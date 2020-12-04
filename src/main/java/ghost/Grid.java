package ghost;

import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Grid {
    public String gridName;
    public int WIDTH = 28; //基于文件坐标系的 Width, 28
    public int HEIGHT = 36; //基于文件坐标系的 Height, 36
    public int GRIDSIZE = 16; //文件坐标系与 image 坐标系的转化常数.
    public PImage[] walls;
    public PImage[] pictures;
    public PImage fruit;
    public PImage ghost;
    public int fruitTotal;
    public char[][] gridCharArray = new char[HEIGHT][WIDTH]; //文件读好之后 char 数据放这里
    private Point wakaStartPosition; //记录的起始位置
    private List<Point> ghostStartPositions = new ArrayList<>();
    //记录所有 Ghost 的起始位置, 或者不用 List, 单一特殊 Ghost 的起始位置.
    public Grid(String gridName) {
        //readLines(gridName);
        this.gridName = gridName;
        //
        File file = new File(this.gridName);
        try(FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            String line;
            int i = 0;
            while((line = bufferedReader.readLine())!=null){//使用readLine方法，一次读一行
                for (int j = 0; j < line.length(); j++) {
                    switch (line.toCharArray()[j]){
                        case '0':
                            this.gridCharArray[i][j] = '0';
                            break;
                        case '1':
                            this.gridCharArray[i][j] = '1';
                            break;
                        case '2':
                            this.gridCharArray[i][j] = '2';
                            break;
                        case '3':
                            this.gridCharArray[i][j] = '3';
                            break;
                        case '4':
                            this.gridCharArray[i][j] = '4';
                            break;
                        case '5':
                            this.gridCharArray[i][j] = '5';
                            break;
                        case '6':
                            this.gridCharArray[i][j] = '6';
                            break;
                        case '7':
                            this.gridCharArray[i][j] = '7';
                            fruitTotal += 1;
                            break;
                        case 'p':
                            this.gridCharArray[i][j] = 'p';
                            this.wakaStartPosition = new Point(j * GRIDSIZE,i * GRIDSIZE, null);
                            break;
                        case 'g':
                            this.gridCharArray[i][j] = 'g';
                            this.ghostStartPositions.add(new Point(j * GRIDSIZE,i * GRIDSIZE, null));
                            break;
                    }
                }
                i += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImageManager(ImageManager imageManager) {
        this.walls = imageManager.walls;
        this.pictures = imageManager.pictures;
        this.fruit = imageManager.fruit;
        this.ghost = imageManager.ghost;
    }

    //显示 Grid
    public void draw(PApplet app) {
        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
                switch (this.gridCharArray[j][i]){
                    case '0':
                        break;
                    case '1':
                        app.image(walls[0], i * GRIDSIZE, j * GRIDSIZE);
                        break;
                    case '2':
                        app.image(walls[1], i * GRIDSIZE, j * GRIDSIZE);
                        break;
                    case '3':
                        app.image(walls[2], i * GRIDSIZE, j * GRIDSIZE);
                        break;
                    case '4':
                        app.image(walls[3], i * GRIDSIZE, j * GRIDSIZE);
                        break;
                    case '5':
                        app.image(walls[4], i * GRIDSIZE, j * GRIDSIZE);
                        break;
                    case '6':
                        app.image(walls[5], i * GRIDSIZE, j * GRIDSIZE);
                        break;
                    case '7':
                        app.image(fruit, i * GRIDSIZE, j * GRIDSIZE);
                        break;
                    case 'p':
//                            app.image(pictures[3], i * GRIDSIZE, j * GRIDSIZE);
                        break;
                    case 'g':
//                            app.image(ghost, i * GRIDSIZE, j * GRIDSIZE);
                        break;
                }
            }
        }

    }

    public Point getWakaStartPosition() {
        return this.wakaStartPosition;
    }

    public List<Point> getGhostStartPositions(){
        return this.ghostStartPositions;
    }

    //判断某个格子是否可以通过.
    public boolean isBlocked(int i, int j) {
        System.out.println(this.gridCharArray[i][j]);
        if (i < 0 || i >= HEIGHT) {
            System.out.println("true");
            return true;
        }
        if (this.gridCharArray[i][j] == '7' || this.gridCharArray[i][j] == 'p' || this.gridCharArray[i][j] == '0') {
            return false;
        }
        return true;
    }
}
