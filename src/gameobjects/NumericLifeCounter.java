package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class NumericLifeCounter extends GameObject {
    private final String LIVES_LEFT = "Lives left: %d";
    private final Vector2 COUNTER_POSITION = new Vector2(10, 450);
    private final float
            TEXT_WIDTH = 15,
            TEXT_HEIGHT = 15;
    private Counter counter;
    private TextRenderable textRenderable;
    private GameObjectCollection gameObjectCollection;

    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, null);
        this.gameObjectCollection = gameObjectCollection;
        counter = livesCounter;
        initialize();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        textRenderable.setString(String.format(LIVES_LEFT, this.counter.value()));
    }

    private void initialize() {
        textRenderable = new TextRenderable(String.format(LIVES_LEFT, this.counter.value()));
        GameObject livesDisplay = new GameObject(COUNTER_POSITION, new Vector2(TEXT_WIDTH, TEXT_HEIGHT),
                textRenderable);
        gameObjectCollection.addGameObject(livesDisplay, Layer.FOREGROUND);
    }
}
