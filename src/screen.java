import java.awt.*;
import java.awt.color.*;
import java.util.ArrayList;


public class screen {
    public int[][] map;
    public int mapWidth, mapHeight, mapScale;
    //
    public int WIDTH, HEIGHT, FOV, renderDistance ;  // FOV = field of view
    public int[] pixels;
    public int[] colors;
    public int[] tiles;
    public int[] tileColors;

    public ArrayList texture;

    public screen(int[][] map, int mapWidth, int mapHeight, int mapScale, int WIDTH, int HEIGHT, int FOV, int renderDistance, int[] pixels, int[] colors, int[] tiles, int[] tileColors, ArrayList texture){
        this.map = map;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapScale = mapScale;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.FOV = FOV;
        this.renderDistance = renderDistance;
        this.pixels = pixels;
        this.colors = colors;
        this.tiles = tiles;
        this.tileColors = tileColors;
        this.texture = texture;
    }

    public screen(int width, int height, int[] ints, double[] doubles, int width1, int height1) {
    }

    public int[] update(Camera camera, int[] pixels){
        for(int i = 0 ; i < pixels.length/2 ; i++){
            if (pixels[i] != Color.darkGray.getRGB()) pixels[i] = Color.darkGray.getRGB();
        }
        for(int n = pixels.length/2 ; n<pixels.length ; n++){
            if (pixels[n] != Color.black.getRGB()) pixels[n] = Color.black.getRGB();
        }
        for(int x = 0; x <WIDTH; x=x+1) {
            double cameraX = 2 * x / (double) WIDTH - 1;
            double rayDirX = camera.xDir + camera.xPlane * cameraX;
            double rayDirY = camera.yDir + camera.yPlane * cameraX;
            double rayDirZ = camera.zDir + camera.zPlane * cameraX;
            int mapX = (int) camera.xPos;
            int mapY = (int) camera.yPos;
            int mapz = (int) camera.zPos;
            double sideDistX;
            double sideDistY;
            double sideDistZ;
            double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) + (rayDirZ * rayDirZ));
            double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) + (rayDirZ * rayDirZ));
            double deltaDistZ = Math.sqrt(1 + (rayDirX * rayDirX) + (rayDirY * rayDirY));
            double perpWallDist;
            int stepX, stepY, stepZ;
            boolean hit = false;
            int side = 0;
            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (camera.xPos - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - camera.xPos) * deltaDistX;
            }
            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (camera.yPos - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - camera.yPos) * deltaDistY;
            }
            if (rayDirZ < 0) {
                stepZ = -1;
                sideDistZ = (camera.zPos - mapz) * deltaDistZ;
            } else {
                stepZ = 1;
                sideDistZ = (mapz + 1.0 - camera.zPos) * deltaDistZ;
            }
            while (!hit){
                if (sideDistX < sideDistY && sideDistX < sideDistZ) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else if (sideDistY < sideDistX && sideDistY < sideDistZ) {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                } else {
                    sideDistZ += deltaDistZ;
                    mapz += stepZ;
                    side = 2;
                }
                if (map[mapX][mapY] > 0) hit = true;
            }
            if (side == 0) perpWallDist = Math.abs((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX);
            else if (side == 1) perpWallDist = Math.abs((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY);
            else perpWallDist = Math.abs((mapz - camera.zPos + (1 - stepZ) / 2) / rayDirZ);
            int lineHeight;
            if (perpWallDist > 0) lineHeight = Math.abs((int) (HEIGHT / perpWallDist));
            else lineHeight = HEIGHT;
            int drawStart = -lineHeight / 2 + HEIGHT / 2;
            if (drawStart < 0) drawStart = 0;
            int drawEnd = lineHeight / 2 + HEIGHT / 2;
            if (drawEnd >= HEIGHT) drawEnd = HEIGHT - 1;


            int texNum = map[mapX][mapY] - 1;
            double wallX;
            if (side == 1){
                wallX = camera.xPos + ((mapY - camera.yPos + (1 - stepY) / 2) / rayDirY) * rayDirX;
            } else if (side == 0){
                wallX = camera.yPos + ((mapX - camera.xPos + (1 - stepX) / 2) / rayDirX) * rayDirY;
            } else {
                wallX = camera.zPos + ((mapz - camera.zPos + (1 - stepZ) / 2) / rayDirZ) * rayDirX;
            }
            wallX -= Math.floor(wallX);




        }

        return pixels;
    }




}
