package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class RemoveBrickStrategy implements CollisionStrategy{
    private GameObjectCollection gameObjectCollection;

    public RemoveBrickStrategy(GameObjectCollection gameObjectCollection){

        this.gameObjectCollection = gameObjectCollection;
    }
    public GameObjectCollection getGameObjectCollection() {
        return gameObjectCollection;
    }

    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {

    }
}
