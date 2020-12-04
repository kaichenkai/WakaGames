package ghost;


import org.json.simple.JSONArray;
import processing.core.PApplet;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.List;

public class Ghost {
    public ImageManager imageManager;
    public int offset = 6;
    public int speed; //来自 config
    public Direction direction;
    public Grid grid;
    public int xOffset = 8;
    public int yOffset = 9;
    public Point position; //当前位置
    public Point target;
    public JSONArray modeLengths;

    public List<Mode> modes = new ArrayList<>();  //所有的 mode
    public int modeIndex; //可以不使用 index

    public Ghost(JSONArray modeLengths, int speed){
        this.speed = speed;
        this.modeLengths = modeLengths;
        this.readModes();
    }

    public void setLocationInfo(Grid grid, Point position){
        this.grid = grid;
        this.position = position;
    }

    public void setImageManager(ImageManager imageManager) {
        this.imageManager = imageManager;
    }

    public void tick() {

    }

    public void draw(PApplet app, Waka waka) {
        app.image(imageManager.ghost, position.x - offset, position.y - offset);
        updateMode();
        //        move(waka);
    }

    public void readModes() {
        for (Object index : this.modeLengths) {
            Mode mode;
            int seconds =  Math.toIntExact((Long) index);
            //是偶数则为 SCATTER, 否则是 CHASE;
            if (seconds % 2 == 0) {
                mode = new Mode(ModeType.SCATTER, seconds);
            } else {
                mode = new Mode(ModeType.CHASE, seconds);
            }
            this.modes.add(mode);
        }
    }

    public List<Mode> updateMode() {
        Mode currentMode = this.modes.get(modeIndex);
        currentMode.remainingFrames -= 1; //减少剩余帧数.
        if (currentMode.remainingFrames <= 0) { //如果剩余帧数<=0
            currentMode.remainingFrames = currentMode.totalFrames;
            this.modes.add(currentMode);
        }
        //重置剩余时间并将此元素加到 modes 末尾, 以此循环. 返回更新后的 modes.
        return this.modes;
    }

    private boolean isAtIntersection(Direction direction, boolean canTurnLeft, boolean
            canTurnRight, boolean canTurnUp, boolean canTurnDown) {
            //如果想要转弯, 由于鬼需要走直线, 要求其 Position x, y 位置可以刚好整除 16.
            //(否则有太多可能转弯的位置了)
            //    在此基础上,
            //   如果当前方向是左右: 如果[向上转弯]或者[向下转弯]至少有一个为 True, 则是一个交叉口
        float nextPositionX;
        float nextPositionY;
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            nextPositionX = position.x;
            nextPositionY = position.y - speed - yOffset;
            return position.y >= 0 && !grid.isBlocked((int) (nextPositionY / 16), (int) nextPositionX / 16);
        }
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            nextPositionX = position.x;
            nextPositionY = position.y + speed + yOffset;
            return position.y <= App.HEIGHT && !grid.isBlocked((int) (nextPositionY / 16), (int) nextPositionX / 16);
        }
            // 当前方向是上下同理.
        if (direction == Direction.UP || direction == Direction.DOWN) {
            nextPositionX = position.x - speed - yOffset;
            nextPositionY = position.y;
            return position.x >= 0 && !grid.isBlocked((int) (nextPositionY / 16), (int) nextPositionX / 16);
        }
        if (direction == Direction.UP || direction == Direction.DOWN) {
            nextPositionX = position.x + speed + yOffset;
            nextPositionY = position.y;
            return position.x <= App.WIDTH && !grid.isBlocked((int) (nextPositionY / 16), (int) nextPositionX / 16);
        }
        return false;
    }

//    private Point getTargetPosition(Waka waka, Mode mode, 可能会用到 List<Ghost> ghosts) {
    private Point getTargetPosition(Waka waka, Mode mode) {
        // 如果是标准的 chaser 鬼(基本要求):
        switch (mode.type) {
            case SCATTER:
                // 创建四个角落点 Point 的坐标.. topLeft, topRight, bottomLeft, bottomRight
                Point topLeft = new Point(0,0, null);
                Point topRight = new Point(0, App.WIDTH, null);
                Point bottomLeft = new Point(App.HEIGHT, 0, null);
                Point bottomRight = new Point(App.HEIGHT, App.WIDTH, null);
                Point [] candidates = {topLeft, topRight, bottomLeft, bottomRight};
                //...
                double maxDistance = Double.MAX_VALUE;
                Point nearestPoint = topLeft; //最近的点
                for (Point p : candidates) {
                    double distance = p.getDistance(this.position);// 用传统的比大小方法, 找到距离最近那个角落点, 这里可以用到 Point 里定义的 getDistance 方法.
                    if (distance <= maxDistance) {
                        maxDistance = distance;
                        nearestPoint = p;
                    }
                }
                return nearestPoint;
            case CHASE:
                return waka.getLocation();
        }
        //如果是某个 additional 要求的鬼:
        //基于这个逻辑, 需考虑其他的 ghost 位置, 或需要考虑调用其他鬼的.getTargetPosition 方法.
        return new Point(1, 2, null);
    }

//    private Direction turn(Point ghost, Point target, Direction direction) {
//        double distanceIfTurnRight =
//                (如果下一步可以右转 且目前的 direction 不为 LEFT)
//        计算 <ghost.x + speed, ghost.y> 与 targetPosition 之间的距离
//        如果不可以右转, 距离为 Double.MAX_VALUE (就不会被考虑了);
//...重复四个方向…
//        计算 4 种方向上可能的"下一步"到目标的距离
//        double[] arr = {4 个方向的目标距离};
//        找到最小的那个距离与其代表的方向.
//    }
}
