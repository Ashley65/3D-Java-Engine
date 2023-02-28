import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class Camera implements KeyListener{
    public double xPos, yPos, zPos, xDir, yDir, zDir, xPlane, yPlane, zPlane;
    public boolean left, right, forward, back, up, down;
    public final double moveSpeed = 0.1;
    public final double rotSpeed = 0.1;

    public Camera(double x, double y, double z, double xd, double yd, double zd, double xp, double yp, double zp){
        xPos = x;
        yPos = y;
        zPos = z;
        xDir = xd;
        yDir = yd;
        zDir = zd;
        xPlane = xp;
        yPlane = yp;
        zPlane = zp;
    }

    public void update(int[][]map){
        if (left){
            double oldDirX = xDir;
            xDir = xDir * Math.cos(rotSpeed) - yDir * Math.sin(rotSpeed);
            yDir = oldDirX * Math.sin(rotSpeed) + yDir * Math.cos(rotSpeed);
            double oldPlaneX = xPlane;
            xPlane = xPlane * Math.cos(rotSpeed) - yPlane * Math.sin(rotSpeed);
            yPlane = oldPlaneX * Math.sin(rotSpeed) + yPlane * Math.cos(rotSpeed);
        }
        if (right){
            double oldDirX = xDir;
            xDir = xDir * Math.cos(-rotSpeed) - yDir * Math.sin(-rotSpeed);
            yDir = oldDirX * Math.sin(-rotSpeed) + yDir * Math.cos(-rotSpeed);
            double oldPlaneX = xPlane;
            xPlane = xPlane * Math.cos(-rotSpeed) - yPlane * Math.sin(-rotSpeed);
            yPlane = oldPlaneX * Math.sin(-rotSpeed) + yPlane * Math.cos(-rotSpeed);
        }
        if (forward){
            if (map[(int)(xPos + xDir * moveSpeed)][(int)yPos] == 0)
                xPos += xDir * moveSpeed;
            if (map[(int)xPos][(int)(yPos + yDir * moveSpeed)] == 0)
                yPos += yDir * moveSpeed;

        }
        if (back){
            if (map[(int)(xPos - xDir * moveSpeed)][(int)yPos] == 0)
                xPos -= xDir * moveSpeed;
            if (map[(int)xPos][(int)(yPos - yDir * moveSpeed)] == 0)
                yPos -= yDir * moveSpeed;
        }
        if (up){
            if (map[(int)(xPos + xPlane * moveSpeed)][(int)yPos] == 0)
                xPos += xPlane * moveSpeed;
            if (map[(int)xPos][(int)(yPos + yPlane * moveSpeed)] == 0)
                yPos += yPlane * moveSpeed;
        }
        if (down){
            if (map[(int)(xPos - xPlane * moveSpeed)][(int)yPos] == 0)
                xPos -= xPlane * moveSpeed;
            if (map[(int)xPos][(int)(yPos - yPlane * moveSpeed)] == 0)
                yPos -= yPlane * moveSpeed;
        }



    }

}
