package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Brick;

public class BrickStrategyFactory {

    private GameObjectCollection gameObjectCollection;
    private BrickerGameManager gameManager;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private Vector2 windowDimensions;

    public BrickStrategyFactory(GameObjectCollection gameObjectCollection, BrickerGameManager gameManager,
                                ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener,
                                WindowController windowController, Vector2 windowDimensions){

        this.gameObjectCollection = gameObjectCollection;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
    }
    public CollisionStrategy getStrategy(){
        return null;
    }
}
