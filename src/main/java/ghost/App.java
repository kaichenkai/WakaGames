package ghost;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.List;

public class App extends PApplet {

    public static final int WIDTH = 448;
    public static final int HEIGHT = 576;
    public Grid grid;
    public Waka waka;
    public List<Ghost> ghosts;
    private final ConfigManager configManager;
    private final ImageManager imageManager;
    public int timer = 0;  //ÃëÊý*60
    //
    public PImage horizontal;
    public PImage vertical;
    public PImage upLeft;
    public PImage upRight;
    public PImage downLeft;
    public PImage downRight;
    //
    public PImage fruit;
    public PImage ghost;
    //
    public PImage playerUp;
    public PImage playerDown;
    public PImage playerLeft;
    public PImage playerRight;
    public PImage playerClosed;
    public App() {
        //Set up your objects
        this.configManager = new ConfigManager();
        this.imageManager = new ImageManager();
        this.configManager.readConfig("config.json");
        this.grid = configManager.initializeGrid();
        this.waka = configManager.initializeWaka();
        this.ghosts = configManager.initializeGhost();
        this.waka.setLocationInfo(this.grid);
        for (int i = 0; i < this.ghosts.size(); i++) {
            this.ghosts.get(i).setLocationInfo(this.grid, this.grid.getGhostStartPositions().get(i));
        }
    }
    public void setup() {
        frameRate(60);
        size(WIDTH, HEIGHT);
        // Load images
        horizontal = loadImage("horizontal.png");
        vertical = loadImage("vertical.png");
        upLeft = loadImage("upLeft.png");
        upRight = loadImage("upRight.png");
        downLeft = loadImage("downLeft.png");
        downRight = loadImage("downRight.png");

        fruit = loadImage("fruit.png");
        ghost = loadImage("ghost.png");
        playerUp = loadImage("playerUp.png");
        playerDown = loadImage("playerDown.png");
        playerLeft = loadImage("playerLeft.png");
        playerRight = loadImage("playerRight.png");
        playerClosed = loadImage("playerClosed.png");

        //imageManager ¸³Öµ
        imageManager.fruit = fruit;
        imageManager.ghost = ghost;
        imageManager.walls = new PImage[] {horizontal, vertical, upLeft, upRight, downLeft, downRight};
        imageManager.pictures = new PImage[] {playerUp, playerDown, playerLeft, playerRight};
        imageManager.playerClosed = playerClosed;
        //
        grid.setImageManager(imageManager);
        waka.setImageManager(imageManager);
        for (Ghost ghost : ghosts) {
            ghost.setImageManager(imageManager);
        }
    }
    public void settings() {
        size(WIDTH, HEIGHT);
    }
    public void draw() {
        background(0, 0, 0);
        this.grid.draw(this);
        this.waka.draw(this);
        for (Ghost ghost : ghosts) {
            ghost.draw(this, waka);
        }
        timer += 1;
    }
    public void keyPressed(){
        Direction direction = this.waka.direction;
        PImage currentImage = this.waka.currentImage;
        switch (keyCode) {
            case RIGHT:
                direction = Direction.RIGHT;
                currentImage = imageManager.pictures[3];
                break;
            case LEFT:
                direction = Direction.LEFT;
                currentImage = imageManager.pictures[2];
                break;
            case UP:
                direction = Direction.UP;
                currentImage = imageManager.pictures[0];
                break;
            case DOWN:
                direction = Direction.DOWN;
                currentImage = imageManager.pictures[1];
                break;
        }
        waka.changeDirection(direction, currentImage);
    }

    public static void main(String[] args) {
        PApplet.main("ghost.App");
    }

}
