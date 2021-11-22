package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.util.Counter;
import src.BrickerGameManager;

public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator implements CollisionStrategy{
    public ChangeCameraStrategy(CollisionStrategy toBeDecorated, WindowController windowController,
                                BrickerGameManager gameManager) {
        super(toBeDecorated);
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
    }

    public void turnOffCameraChange(){

    }

}
