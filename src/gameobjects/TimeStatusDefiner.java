package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.brick_strategies.ChangeTimeScaleStrategy;


public class TimeStatusDefiner extends GameObject {

    private WindowController windowController;
    private GameObjectCollection gameObjectCollection;
    private boolean isGreen;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public TimeStatusDefiner(Vector2 topLeftCorner,
                             Vector2 dimensions,
                             WindowController windowController,
                             Renderable renderable,
                             GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, renderable);
        this.windowController = windowController;
        this.gameObjectCollection = gameObjectCollection;
        this.isGreen = ChangeTimeScaleStrategy.timeScaleIsGreen;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if(!(other instanceof Paddle))
            super.onCollisionEnter(other, collision);
            changeTimeScale();
            gameObjectCollection.removeGameObject(this);
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other instanceof Paddle;
    }

    private void changeTimeScale() {
        if (isGreen){
            windowController.setTimeScale(0.9f);
            ChangeTimeScaleStrategy.timeScaleIsGreen = false;
        }
        else{
            windowController.setTimeScale(1.1f);
            ChangeTimeScaleStrategy.timeScaleIsGreen = true;
        }
    }

}