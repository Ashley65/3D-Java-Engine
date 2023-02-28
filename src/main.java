import java.awt.color.*;
import java.awt.Graphics.*;
import java.awt.image.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.*;


public class Game extends JFrame implements Runnable{
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public int mapWidth = 30;
    public int mapHeight = 30;
    public int mapScale = 20;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;
    public static int [][] map = new int[30][30];
    public int[] tiles = new int[mapWidth * mapHeight];
    public int[] colors = new int[mapWidth * mapHeight];
    public int[] tileColors = new int[mapWidth * mapHeight];
    public int[] tileColors2 = new int[mapWidth * mapHeight];

    public Game(){
        thread = new Thread(this);
    }



}


public class main {


}
