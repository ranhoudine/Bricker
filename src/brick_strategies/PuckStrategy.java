package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;
import src.gameobjects.Puck;

import java.util.Random;

public class PuckStrategy extends RemoveBrickStrategyDecorator implements CollisionStrategy{

    private CollisionStrategy toBeDecorated;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private Random random;
    private boolean collidedAlready;

    public PuckStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader, SoundReader soundReader) {
        super(toBeDecorated);
        this.toBeDecorated = toBeDecorated;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        random = new Random();
        collidedAlready = false;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        if (!collidedAlready){
            super.onCollision(thisObj, otherObj, counter);
            addThreePucks(toBeDecorated, thisObj.getTopLeftCorner(), otherObj.getVelocity());
            collidedAlready = true;
        }

    }

    private void addThreePucks(CollisionStrategy toBeDecorated, Vector2 newBallsStartingPosition, Vector2 ballSpeed) {
            Renderable ballImage = imageReader.readImage(".idea/assets/mockBall.png", true);
            Sound impactSound = soundReader.readSound(".idea/assets/blop_cut_silenced.wav");
            Puck puck;
            for (int i = 0; i < 3; i++) {
                puck = new Puck(newBallsStartingPosition.add(Vector2.RIGHT.mult(20*i)),
                        new Vector2(20, 20),
                        ballImage,
                        impactSound);
                puck.setVelocity(ballSpeed.rotated(i*30));
                toBeDecorated.getGameObjectCollection().addGameObject(puck);
            }

    }
}
