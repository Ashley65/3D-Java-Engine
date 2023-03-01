import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.Serial;
import java.util.ArrayList;

import static java.lang.Math.*;


public class Game extends JFrame implements Runnable {
    @Serial
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
    public static int[][] map = new int[30][30];
    public int[] tiles = new int[mapWidth * mapHeight];
    public int[] colors = new int[mapWidth * mapHeight];
    public int[] tileColors = new int[mapWidth * mapHeight];
    public int[] tileColors2 = new int[mapWidth * mapHeight];

    public Game() {
        thread = new Thread(this);
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("3D engine");
        start();
    }

    public void start() {
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                if (map[i][j] == 1) {
                    for (int k = 0; k < mapScale; k++) {
                        for (int l = 0; l < mapScale; l++) {
                            pixels[(i * mapScale + k) + (j * mapScale + l) * WIDTH] = 0x00ff00;
                        }
                    }
                }
            }
        }


    }

    public void update() {
        render();
    }

    public void run() {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int updates = 0;
        long timer = System.currentTimeMillis();
        requestFocus();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                // Handles the updates
                camera.update(map);
                update();
                updates++;
                delta--;
            }
            render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                // Handles the frames
                timer += 1000;
                System.out.println(updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }


        }

    }

    public ArrayList<texture> textures;
    public void loadTextures(){
        textures = new ArrayList<texture>();
        textures.add(new texture("testTexture/Wood.png", 128));
        textures.add(new texture("testTexture/Brick.png", 128));
        textures.add(new texture("testTexture/Grass.png", 128));
    }
    public Camera camera;{
        camera = new Camera(22, 12, 5, -1, 0, 0, 0, 0.66, 0) {
            @Override
            public void keyTyped(KeyEvent e) {
                addKeyListener(camera);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_LEFT)) {
                    left = true;


                }
                if ((e.getKeyCode() == KeyEvent.VK_RIGHT)) {
                    right = true;
                }
                if ((e.getKeyCode() == KeyEvent.VK_UP)) {
                    forward = true;
                }
                if ((e.getKeyCode() == KeyEvent.VK_DOWN)) {
                    back = true;
                }
                if ((e.getKeyCode() == KeyEvent.VK_W)) {
                    up = true;
                }
                if ((e.getKeyCode() == KeyEvent.VK_S)) {
                    down = true;
                }


            }

            @Override
            public void keyReleased(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_LEFT)) {
                    left = false;
                }
                if ((e.getKeyCode() == KeyEvent.VK_RIGHT)) {
                    right = false;
                }
                if ((e.getKeyCode() == KeyEvent.VK_UP)) {
                    forward = false;
                }
                if ((e.getKeyCode() == KeyEvent.VK_DOWN)) {
                    back = false;
                }
                if ((e.getKeyCode() == KeyEvent.VK_W)) {
                    up = false;
                }
                if ((e.getKeyCode() == KeyEvent.VK_S)) {
                    down = false;
                }



            }


        };

    }
    public screen Screen;{
        Screen = new screen(WIDTH, HEIGHT, new int[WIDTH * HEIGHT], new double[WIDTH * HEIGHT], 640, 480);

    }



}
