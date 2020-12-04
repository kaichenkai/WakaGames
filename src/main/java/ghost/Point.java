package ghost;

public class Point {
    float x;
    float y;
    Character type; //可以为空.
    public Point(float x, float y, Character type){
        this.x = x;
        this.y = y;
        this.type = type;
    }
//     其他可能的方法: 计算直线距离
    public double getDistance(Point anotherPoint) {
        return Math.sqrt(Math.pow(x - anotherPoint.x, 2) + Math.pow(y - anotherPoint.y, 2));
    }
}
