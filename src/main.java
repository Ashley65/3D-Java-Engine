import java.awt.*;
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
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("3D engine");
        setBackground(color.white);
        start();
    }
    public void start(){
        running = true;
        thread.start();
    }
    public synchronized void stop(){
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
        }
        for(int i = 0; i < mapWidth; i++){
            for(int j = 0; j < mapHeight; j++){
                if(map[i][j] == 1){
                    for(int k = 0; k < mapScale; k++){
                        for(int l = 0; l < mapScale; l++){
                            pixels[(i * mapScale + k) + (j * mapScale + l) * WIDTH] = 0x00ff00;
                        }
                    }
                }
            }
        }



    }
    public void run(){
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int updates = 0;
        long timer = System.currentTimeMillis();
        requestFocus();
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                update();
                updates++;
                delta--;
            }
            render();
            frames++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
    }



}


public class main {


}
