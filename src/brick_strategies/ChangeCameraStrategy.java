package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Puck;

public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator implements CollisionStrategy{
    private WindowController windowController;
    private BrickerGameManager gameManager;
    private BallCollisionCountdownAgent countdownAgent;
    private CollisionStrategy toBeDecorated;

    public ChangeCameraStrategy(CollisionStrategy toBeDecorated, WindowController windowController,
                                BrickerGameManager gameManager) {
        super(toBeDecorated);
        this.toBeDecorated = toBeDecorated;
        this.windowController = windowController;
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if (gameManager.camera() == null && !(otherObj instanceof Puck)){
            countdownAgent = new BallCollisionCountdownAgent((Ball) otherObj, this, 4);
            toBeDecorated.getGameObjectCollection().addGameObject(countdownAgent, Layer.BACKGROUND);
            gameManager.setCamera(new Camera(otherObj,
                                  Vector2.ZERO,
                                  windowController.getWindowDimensions().mult(1.2f),
                                  windowController.getWindowDimensions()));
        }


    }

    public void turnOffCameraChange(){
        gameManager.setCamera(null);
        toBeDecorated.getGameObjectCollection().removeGameObject(countdownAgent, Layer.BACKGROUND);
    }

}
