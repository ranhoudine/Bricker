package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class Ball extends GameObject {
    private Sound collisionSound;
    private int collisionCount;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        collisionCount = 0;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (!(other instanceof Ball)){
            collisionCount++;
            super.onCollisionEnter(other, collision);
            setVelocity(getVelocity().flipped(collision.getNormal()));
            collisionSound.play();
        }
    }
    public int getCollisionCount(){
        return collisionCount;
    }

}
