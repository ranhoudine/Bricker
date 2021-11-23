package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;

public class AddPaddleStrategy extends RemoveBrickStrategyDecorator implements CollisionStrategy{
    private CollisionStrategy toBeDecorated;
    private ImageReader imageReader;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;

    public AddPaddleStrategy(CollisionStrategy toBeDecorated,
                             ImageReader imageReader,
                             UserInputListener inputListener,
                             Vector2 windowDimensions){
        super(toBeDecorated);
        this.toBeDecorated = toBeDecorated;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if (!MockPaddle.isInstantiated){
            addPaddle();
        }
    }

    private void addPaddle() {
        Renderable paddleImage = imageReader.readImage(".idea/assets/paddle.png", true);
        MockPaddle paddle = new MockPaddle(Vector2.ZERO,
                                           new Vector2(300, 15),
                                           paddleImage,
                                           inputListener,
                                           windowDimensions,
                                           toBeDecorated.getGameObjectCollection(),
                                           10,
                                           3);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y()/2));
        toBeDecorated.getGameObjectCollection().addGameObject(paddle);
    }
}
