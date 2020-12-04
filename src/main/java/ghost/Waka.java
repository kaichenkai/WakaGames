package ghost;

import processing.core.PApplet;
import processing.core.PImage;

import static ghost.Direction.UP;

public class Waka {
    public int speed;  //来自 config
    public int lives;  //来自 config
    public int timer = 0; //用于判断当下画张嘴还是闭嘴(可以不写这里, 使用 app 里的 timer 也可)
    public ImageManager imageManager;
    public PImage currentImage;
    public Grid grid; //用于碰撞判断 (可以不写这里, 每次通过 draw 来访问, 与 ghost 一样)
    public Point position; //当前位置
    public Point originalPosition; //原本位置, 复活用
    public int xOffset = 8;
    public int yOffset = 9;
    public Direction direction; //当前方向
    public int fruitCount;
    public Waka(int speed, int lives) {
        this.speed = speed;
        this.lives = lives;
        this.direction = Direction.RIGHT;
        //…
    }

    //
    public void setLocationInfo(Grid grid){
        this.grid = grid;
        this.position = grid.getWakaStartPosition();
        this.originalPosition = grid.getWakaStartPosition();
        //...
    }

    public void setImageManager(ImageManager imageManager) {
        this.imageManager = imageManager;
        this.currentImage = imageManager.pictures[3]; //默认向右
    }

    public void draw(PApplet app){
        //张嘴,闭嘴
        if (timer % 16 < 8) {
            app.image(currentImage, position.x - xOffset, position.y - yOffset);
        } else {
            app.image(imageManager.playerClosed, position.x - xOffset, position.y - yOffset);
        }
        //移动
        this.move();

        timer += 1;
    }

    //改变方向
    public void changeDirection(Direction direction, PImage pImage) {
        this.direction = direction;
        this.currentImage = pImage;
    }

    public void move(){
        if (canMoveToDirection(this.position, this.speed, this.direction, grid))
            switch(this.direction){
                case RIGHT:  //注意改变的位置与方向相符
                    this.position.x += this.speed;
                    break;
                case LEFT:
                    this.position.x -= this.speed;
                    break;
                case UP:
                    this.position.y -= this.speed;
                    break;
                case DOWN:
                    this.position.y += this.speed;
                    break;

            }
        int fruitFlag = this.eatFruit(this.position, grid);
        fruitCount += fruitFlag;
        if (fruitCount == grid.fruitTotal) {
            System.out.println("游戏结束");
        }
    }

    private boolean canMoveToDirection(Point position, int speed, Direction direction, Grid grid){
        float nextPositionX;
        float nextPositionY;
        switch (direction) {
            case RIGHT:
                nextPositionX = position.x + speed + xOffset;
                nextPositionY = position.y;
                return position.x <= App.WIDTH && !grid.isBlocked((int) (nextPositionY / 16), (int) nextPositionX / 16);
            case LEFT:
                nextPositionX = position.x - speed - xOffset;
                nextPositionY = position.y;
                return position.x >= 0 && !grid.isBlocked((int) (nextPositionY / 16), (int) nextPositionX / 16);
            case UP:
                nextPositionX = position.x;
                nextPositionY = position.y - speed - yOffset;
                return position.x >= 0 && !grid.isBlocked((int) (nextPositionY / 16), (int) nextPositionX / 16);
            case DOWN:
                nextPositionX = position.x;
                nextPositionY = position.y + speed + yOffset;
                return position.x <= App.HEIGHT && !grid.isBlocked((int) (nextPositionY / 16), (int) nextPositionX / 16);
            default:
                return false;
        }
    }

    public int eatFruit(Point position, Grid grid) {
        int i = (int) (position.y / 16);
        int j = (int) (position.x / 16);
        if (grid.gridCharArray[i][j] == '7') {
            grid.gridCharArray[i][j] = '0';
            return 1;
        } else {
            return 0;
        }
    }

    public Point getLocation() {
        return this.position;
    }
}
