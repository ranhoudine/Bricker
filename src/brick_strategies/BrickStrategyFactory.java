package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Brick;

import java.util.Random;

public class BrickStrategyFactory {

    private GameObjectCollection gameObjectCollection;
    private BrickerGameManager gameManager;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private Vector2 windowDimensions;
    private Random random;

    public BrickStrategyFactory(GameObjectCollection gameObjectCollection, BrickerGameManager gameManager,
                                ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener,
                                WindowController windowController, Vector2 windowDimensions) {

        this.gameObjectCollection = gameObjectCollection;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
        random = new Random();
    }

    public CollisionStrategy getStrategy() {
        int randomInteger = random.nextInt(5);
        CollisionStrategy strategy = null;
        switch (randomInteger) {
            case 0:
                strategy = new RemoveBrickStrategy(gameObjectCollection);
                break;
            case 1:
                strategy = new AddPaddleStrategy(new RemoveBrickStrategy(gameObjectCollection),
                        imageReader,
                        inputListener,
                        windowDimensions);
                break;
            case 2:
                strategy = new PuckStrategy(new RemoveBrickStrategy(gameObjectCollection),
                        imageReader,
                        soundReader);
                break;
            case 3:
                strategy = new ChangeCameraStrategy(new RemoveBrickStrategy(gameObjectCollection),
                        windowController,
                        gameManager);
                break;
            case 4:
                strategy = new ChangeTimeScaleStrategy(new RemoveBrickStrategy(gameObjectCollection),
                        imageReader,
                        windowController);
                break;
        }
        return  strategy;
    }
}
