package ghost;

public class Point {
    float x;
    float y;
    Character type; //����Ϊ��.
    public Point(float x, float y, Character type){
        this.x = x;
        this.y = y;
        this.type = type;
    }
//     �������ܵķ���: ����ֱ�߾���
    public double getDistance(Point anotherPoint) {
        return Math.sqrt(Math.pow(x - anotherPoint.x, 2) + Math.pow(y - anotherPoint.y, 2));
    }
}
