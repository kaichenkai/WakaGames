package ghost;


import org.json.simple.JSONArray;
import processing.core.PApplet;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.List;

public class Ghost {
    public ImageManager imageManager;
    public int offset = 6;
    public int speed; //���� config
    public Direction direction;
    public Grid grid;
    public int xOffset = 8;
    public int yOffset = 9;
    public Point position; //��ǰλ��
    public Point target;
    public JSONArray modeLengths;

    public List<Mode> modes = new ArrayList<>();  //���е� mode
    public int modeIndex; //���Բ�ʹ�� index

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
            //��ż����Ϊ SCATTER, ������ CHASE;
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
        currentMode.remainingFrames -= 1; //����ʣ��֡��.
        if (currentMode.remainingFrames <= 0) { //���ʣ��֡��<=0
            currentMode.remainingFrames = currentMode.totalFrames;
            this.modes.add(currentMode);
        }
        //����ʣ��ʱ�䲢����Ԫ�ؼӵ� modes ĩβ, �Դ�ѭ��. ���ظ��º�� modes.
        return this.modes;
    }

    private boolean isAtIntersection(Direction direction, boolean canTurnLeft, boolean
            canTurnRight, boolean canTurnUp, boolean canTurnDown) {
            //�����Ҫת��, ���ڹ���Ҫ��ֱ��, Ҫ���� Position x, y λ�ÿ��Ըպ����� 16.
            //(������̫�����ת���λ����)
            //    �ڴ˻�����,
            //   �����ǰ����������: ���[����ת��]����[����ת��]������һ��Ϊ True, ����һ�������
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
            // ��ǰ����������ͬ��.
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

//    private Point getTargetPosition(Waka waka, Mode mode, ���ܻ��õ� List<Ghost> ghosts) {
    private Point getTargetPosition(Waka waka, Mode mode) {
        // ����Ǳ�׼�� chaser ��(����Ҫ��):
        switch (mode.type) {
            case SCATTER:
                // �����ĸ������ Point ������.. topLeft, topRight, bottomLeft, bottomRight
                Point topLeft = new Point(0,0, null);
                Point topRight = new Point(0, App.WIDTH, null);
                Point bottomLeft = new Point(App.HEIGHT, 0, null);
                Point bottomRight = new Point(App.HEIGHT, App.WIDTH, null);
                Point [] candidates = {topLeft, topRight, bottomLeft, bottomRight};
                //...
                double maxDistance = Double.MAX_VALUE;
                Point nearestPoint = topLeft; //����ĵ�
                for (Point p : candidates) {
                    double distance = p.getDistance(this.position);// �ô�ͳ�ıȴ�С����, �ҵ���������Ǹ������, ��������õ� Point �ﶨ��� getDistance ����.
                    if (distance <= maxDistance) {
                        maxDistance = distance;
                        nearestPoint = p;
                    }
                }
                return nearestPoint;
            case CHASE:
                return waka.getLocation();
        }
        //�����ĳ�� additional Ҫ��Ĺ�:
        //��������߼�, �迼�������� ghost λ��, ����Ҫ���ǵ����������.getTargetPosition ����.
        return new Point(1, 2, null);
    }

//    private Direction turn(Point ghost, Point target, Direction direction) {
//        double distanceIfTurnRight =
//                (�����һ��������ת ��Ŀǰ�� direction ��Ϊ LEFT)
//        ���� <ghost.x + speed, ghost.y> �� targetPosition ֮��ľ���
//        �����������ת, ����Ϊ Double.MAX_VALUE (�Ͳ��ᱻ������);
//...�ظ��ĸ�����
//        ���� 4 �ַ����Ͽ��ܵ�"��һ��"��Ŀ��ľ���
//        double[] arr = {4 �������Ŀ�����};
//        �ҵ���С���Ǹ������������ķ���.
//    }
}
