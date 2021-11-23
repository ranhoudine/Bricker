package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class RemoveBrickStrategy implements CollisionStrategy{
    private GameObjectCollection gameObjectCollection;
    private boolean strategyPerformedAlready;

    public RemoveBrickStrategy(GameObjectCollection gameObjectCollection){
        this.gameObjectCollection = gameObjectCollection;
        strategyPerformedAlready = false;
    }
    public GameObjectCollection getGameObjectCollection() {
        return gameObjectCollection;
    }

    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        if (!strategyPerformedAlready){
            gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
            counter.decrement();
            strategyPerformedAlready = true;
        }

    }
}
