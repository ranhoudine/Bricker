package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.TimeStatusDefiner;
import java.util.Random;


public class ChangeTimeScaleStrategy extends RemoveBrickStrategyDecorator implements CollisionStrategy {
    public static boolean timeScaleIsGreen;
    private Random random;
    private CollisionStrategy toBeDecorated;
    private ImageReader imageReader;
    private WindowController windowController;


    public ChangeTimeScaleStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader, WindowController windowController) {
        super(toBeDecorated);
        this.toBeDecorated = toBeDecorated;
        this.imageReader = imageReader;
        this.windowController = windowController;
        random = new Random();
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        dropTimeStatus(thisObj);
    }

    private void dropTimeStatus(GameObject brickCollidedWith) {
        TimeStatusDefiner timeStatus = new TimeStatusDefiner(brickCollidedWith.getTopLeftCorner(),
                                                new Vector2(20,20),
                                                  windowController,
                                                  getTimeStatusImage(),
                                                toBeDecorated.getGameObjectCollection());
        timeStatus.setCenter(brickCollidedWith.getCenter());
        timeStatus.setVelocity(new Vector2(0, 100));
        toBeDecorated.getGameObjectCollection().addGameObject(timeStatus);
    }

    private Renderable getTimeStatusImage(){
        if (timeScaleIsGreen){
            return imageReader.readImage(".idea/assets/slow.png", false);
        }
        else{
            return imageReader.readImage(".idea/assets/quicken.png", false);
        }
    }

}
