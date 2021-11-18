package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.*;

public class CollisionStrategy {
    private GameObjectCollection objectCollection;

    public CollisionStrategy(GameObjectCollection objectCollection) {
        this.objectCollection = objectCollection;
    }

    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        objectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        counter.decrement();
    }
}

