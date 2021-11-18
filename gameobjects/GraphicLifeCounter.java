package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {
    private final float
            HEART_HEIGHT = 20,
            HEART_WIDTH = 20,
            HEARTS_SEPARATING_DISTANCE = 3;
    private final Vector2 LEFTMOST_HEART_POSITION = new Vector2(10, 478);

    private Counter counter;
    private GameObject lives[];
    private int currentLives;
    private GameObjectCollection gameObjectsCollection;

    public GraphicLifeCounter(Vector2 widgetTopLeftCorner,
                              Vector2 widgetDimensions,
                              Counter livesCounter,
                              Renderable widgetRenderable,
                              GameObjectCollection gameObjectsCollection,
                              int numOfLives) {
        super(widgetTopLeftCorner, widgetDimensions, widgetRenderable);
        counter = livesCounter;
        currentLives = numOfLives;
        this.gameObjectsCollection = gameObjectsCollection;
        initializeLives(numOfLives, widgetRenderable);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (counter.value() < currentLives) {
            if (currentLives > 0) {
                gameObjectsCollection.removeGameObject(lives[currentLives - 1], Layer.FOREGROUND);
            }
            currentLives = counter.value();
        }
    }

    private void initializeLives(int numOfLives, Renderable widgetRenderable) {
        lives = new GameObject[numOfLives];
        for (int i = 0; i < numOfLives; i++) {
            lives[i] = new GameObject(LEFTMOST_HEART_POSITION.add(new Vector2(i * (HEART_WIDTH + HEARTS_SEPARATING_DISTANCE), 0)),
                    new Vector2(HEART_WIDTH, HEART_HEIGHT),
                    widgetRenderable);
            gameObjectsCollection.addGameObject(lives[i], Layer.FOREGROUND);
        }
    }
}
