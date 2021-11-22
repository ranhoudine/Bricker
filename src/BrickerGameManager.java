package src;

import src.brick_strategies.CollisionStrategy;
import src.gameobjects.Ball;
import src.gameobjects.Brick;
import src.gameobjects.GraphicLifeCounter;
import src.gameobjects.NumericLifeCounter;
import src.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.util.Random;

public class BrickerGameManager extends GameManager {

    //region constants
    private final float
            BRICK_HEIGHT = 15,
            BRICKS_SEPARATING_DISTANCE = 1,
            BALL_SPEED = 300,
            PADDLE_HEIGHT = 15,
            PADDLE_WIDTH = 100;

    private final int
            NUMBER_OF_BRICKS = 40,
            NUMBER_OF_BRICK_ROWS = 5,
            INITIAL_LIVES = 3,
            MINIMAL_BRICK_DISTANCE_FROM_WALLS = 5,
            MIN_PADDLE_DISTANCE_FROM_EDGE = 5;

    //endregion
    //region private attributes
    private Ball ball;
    private Brick[][] brickArray;
    private WindowController windowController;
    private Random random;
    private Counter brickCounter;
    private Counter livesCounter;
    //endregion
    //region public attributes
    public static final int BORDER_WIDTH = 5;
    //endregion

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        random = new Random();
        brickCounter = new Counter(NUMBER_OF_BRICKS);
        livesCounter = new Counter(INITIAL_LIVES);
        this.windowController = windowController;
        Vector2 windowDimensions = windowController.getWindowDimensions();
        createBackground(windowDimensions);
        createWalls(windowDimensions);
        brickArray = createBricks(imageReader, windowDimensions);
        ball = createBall(imageReader, windowDimensions, soundReader);
        createPaddle(imageReader, windowDimensions, inputListener);
        createGraphicLifeCounter(imageReader);
        createNumericLifeCounter();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkBallFell();
        checkGameOver();
    }

    public void repositionBall(GameObject ball){

    }

    private void checkBallFell() {
        float ballHeight = ball.getCenter().y();
        if (ballHeight > windowController.getWindowDimensions().y()) {
            livesCounter.decrement();
            restartBallPosition();
        }
    }

    public static void main(String[] args) {
        new BrickerGameManager("Arkanoid",
                new Vector2(700, 500)).run();
    }

    private void createWalls(Vector2 windowDimensions) {
        //creating three walls
        GameObject topWall = new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(), BORDER_WIDTH),
                null);
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x() - BORDER_WIDTH, 0),
                new Vector2(BORDER_WIDTH, windowDimensions.y()),
                null);
        GameObject leftWall = new GameObject(Vector2.ZERO,
                new Vector2(BORDER_WIDTH, windowDimensions.y()),
                null);
        gameObjects().addGameObject(topWall);
        gameObjects().addGameObject(rightWall);
        gameObjects().addGameObject(leftWall);
    }

    private Ball createBall(ImageReader imageReader, Vector2 windowDimensions, SoundReader soundReader) {
        Renderable ballImage = imageReader.readImage(".idea/assets/ball.png", true);
        Sound impactSound = soundReader.readSound(".idea/assets/blop_cut_silenced.wav");
        Ball ball = new Ball(Vector2.ZERO, new Vector2(20, 20), ballImage, impactSound);
        ball.setCenter(windowDimensions.mult(0.5f));
        int[] movementFactors = new int[]{1, -1};
        ball.setVelocity(new Vector2(movementFactors[random.nextInt(2)],
                movementFactors[random.nextInt(2)]).mult(BALL_SPEED));
        gameObjects().addGameObject(ball);
        return ball;
    }

    private void restartBallPosition() {
        ball.setCenter(windowController.getWindowDimensions().mult(0.5f));
        int[] movementFactors = new int[]{1, -1};
        ball.setVelocity(new Vector2(movementFactors[random.nextInt(2)],
                movementFactors[random.nextInt(2)]).mult(BALL_SPEED));
    }

    private void createPaddle(ImageReader imageReader, Vector2 windowDimensions, UserInputListener inputListener) {
        Renderable paddleImage = imageReader.readImage(".idea/assets/paddle.png", true);
        Paddle paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage, inputListener, windowDimensions, MIN_PADDLE_DISTANCE_FROM_EDGE);
        paddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30));
        gameObjects().addGameObject(paddle);
    }

    private void createBackground(Vector2 windowDimensions) {
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, new RectangleRenderable(Color.DARK_GRAY));
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private Brick[][] createBricks(ImageReader imageReader, Vector2 windowDimensions) {
        Renderable brickImage = imageReader.readImage(".idea/assets/brick.png", false);
        int bricksPerRow = NUMBER_OF_BRICKS / NUMBER_OF_BRICK_ROWS;
        int brickWidth = getRequiredBrickWidth(windowDimensions.x());
        Brick[][] bricks = new Brick[NUMBER_OF_BRICK_ROWS][];
        Vector2 brickPosition = getFirstBrickPosition(windowDimensions, bricksPerRow, brickWidth);
        for (int i = 0; i < NUMBER_OF_BRICK_ROWS; i++) {
            bricks[i] = new Brick[bricksPerRow];
            for (int j = 0; j < bricksPerRow; j++) {
                bricks[i][j] = new Brick(brickPosition.add(new Vector2(j * (brickWidth + 1), i * (BRICK_HEIGHT + 1))),
                        new Vector2(brickWidth, BRICK_HEIGHT),
                        brickImage, new CollisionStrategy(gameObjects()), brickCounter);
                gameObjects().addGameObject(bricks[i][j], Layer.STATIC_OBJECTS);
                brickCounter.increment();
            }
        }
        return bricks;
    }

    private int getRequiredBrickWidth(float windowWidth) {
        int bricksPerRow = NUMBER_OF_BRICKS / NUMBER_OF_BRICK_ROWS;
        return (int) (windowWidth - 2 * MINIMAL_BRICK_DISTANCE_FROM_WALLS - (bricksPerRow - 1) * BRICKS_SEPARATING_DISTANCE) /
                bricksPerRow;
    }

    private void checkGameOver() {
        String prompt = "";
        if (livesCounter.value() < 0) {
            prompt += "You lose! ";
        } else {
            if (noBricksLeft()) {
                prompt += "You win! ";
            }
        }
        if (!prompt.isEmpty()) {
            prompt += "play again?";
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }

    private void createGraphicLifeCounter(ImageReader imageReader) {
        Renderable heartImage = imageReader.readImage(".idea/assets/heart.png", true);
        GraphicLifeCounter graphicLifeCounter = new GraphicLifeCounter(Vector2.ZERO, Vector2.ZERO,
                livesCounter, heartImage, gameObjects(), INITIAL_LIVES);
        gameObjects().addGameObject(graphicLifeCounter);
    }

    private void createNumericLifeCounter() {
        NumericLifeCounter counter = new NumericLifeCounter(livesCounter, Vector2.ZERO, Vector2.ZERO, gameObjects());
        gameObjects().addGameObject(counter);
    }

    private Vector2 getFirstBrickPosition(Vector2 windowDimensions, int bricksPerRow, int brickWidth) {
        float additionalMarginFromWalls = (windowDimensions.x() - (bricksPerRow * (brickWidth + 1) - 1) -
                2 * MINIMAL_BRICK_DISTANCE_FROM_WALLS) / 2;
        return new Vector2(MINIMAL_BRICK_DISTANCE_FROM_WALLS + additionalMarginFromWalls,
                MINIMAL_BRICK_DISTANCE_FROM_WALLS);

    }

    private boolean noBricksLeft() {
        if (brickCounter.value() <= 0) {
            return true;
        }
        return false;
    }
}


