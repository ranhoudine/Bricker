package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.*;

public interface CollisionStrategy {

    GameObjectCollection getGameObjectCollection();

    void onCollision(GameObject thisObj, GameObject otherObj, Counter counter);
}

