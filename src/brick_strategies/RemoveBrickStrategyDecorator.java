package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

abstract class RemoveBrickStrategyDecorator implements CollisionStrategy{
    private CollisionStrategy toBeDecorated;

    public RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated){
        this.toBeDecorated = toBeDecorated;
    }
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        toBeDecorated.onCollision(thisObj, otherObj, counter);
    }

    @Override
    public GameObjectCollection getGameObjectCollection() {
        return null;
    }
}
