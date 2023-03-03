import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class texture {
    public int[] pixels;
    private String loc;

    private int size;

    public void load(){
        try{
            BufferedImage image = ImageIO.read(new File(loc));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        }catch(IOException e){
            e.printStackTrace();
        }
    }



    public texture(String location, int size) {
        loc = location;
        this.size = size;
        pixels = new int[size * size];
        load();
    }

    public static texture wood = new texture("testTexture/Wood.png", 128);
    public static texture brick = new texture("testTexture/Brick.png", 128);
    public static texture Grass = new texture("testTexture/Grass.png", 128);


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
