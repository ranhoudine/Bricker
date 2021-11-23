package src.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;

public class BallCollisionCountdownAgent extends GameObject {
    private ChangeCameraStrategy owner;
    private int countDownValue;
    private Ball ballToFollow;
    private int numOfBallCollisionsAtCreation;

    public BallCollisionCountdownAgent(Ball ball, ChangeCameraStrategy owner, int countDownValue) {
        super(ball.getTopLeftCorner(), ball.getDimensions(), null);
        this.owner = owner;
        this.countDownValue = countDownValue;
        ballToFollow = ball;
        numOfBallCollisionsAtCreation = ball.getCollisionCount();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (ballToFollow.getCollisionCount() > numOfBallCollisionsAtCreation + countDownValue){
            owner.turnOffCameraChange();
        }
    }
}
