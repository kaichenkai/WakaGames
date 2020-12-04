package ghost;

import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Grid {
    public String gridName;
    public int WIDTH = 28; //�����ļ�����ϵ�� Width, 28
    public int HEIGHT = 36; //�����ļ�����ϵ�� Height, 36
    public int GRIDSIZE = 16; //�ļ�����ϵ�� image ����ϵ��ת������.
    public PImage[] walls;
    public PImage[] pictures;
    public PImage fruit;
    public PImage ghost;
    public int fruitTotal;
    public char[][] gridCharArray = new char[HEIGHT][WIDTH]; //�ļ�����֮�� char ���ݷ�����
    private Point wakaStartPosition; //��¼����ʼλ��
    private List<Point> ghostStartPositions = new ArrayList<>();
    //��¼���� Ghost ����ʼλ��, ���߲��� List, ��һ���� Ghost ����ʼλ��.
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
            while((line = bufferedReader.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
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

    //��ʾ Grid
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

    //�ж�ĳ�������Ƿ����ͨ��.
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
