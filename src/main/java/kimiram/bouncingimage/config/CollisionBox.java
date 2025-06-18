package kimiram.bouncingimage.config;

import static kimiram.bouncingimage.BouncingImageClient.*;
import static kimiram.bouncingimage.config.BouncingImageConfig.configValues;

public class CollisionBox {
    public int x1;
    public int y1;
    public int x2;
    public int y2;

    public CollisionBox(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void checkOverlaps() {
        double ix1 = imageX, iy1 = imageY;
        double ix2 = ix1 + configValues.imageWidth, iy2 = iy1 + configValues.imageHeight;

        // i don't know how to make it better

        if (isOverlap(ix1, iy1, ix2, iy2)) {
            if (isOverlap(ix1 - dX * configValues.speed, iy1, ix2 - dX * configValues.speed, iy2)) {
                change_dY = true;
            } else if (isOverlap(ix1, iy1 - dY * configValues.speed, ix2, iy2 - dY * configValues.speed)) {
                change_dX = true;
            } else {
                if (Math.max(Math.abs(ix1 - x2), Math.abs(ix2 - x1)) > Math.max(Math.abs(iy1 - y2), Math.abs(iy2 - y1))) {
                    change_dX = true;
                } else if (Math.max(Math.abs(ix1 - x2), Math.abs(ix2 - x1)) < Math.max(Math.abs(iy1 - y2), Math.abs(iy2 - y1))) {
                    change_dY = true;
                } else {
                    change_dX = true;
                    change_dY = true;
                }
            }
        }
    }

    public boolean isOverlap(double x1, double y1, double x2, double y2) {
        if (((x1 <= this.x2 && x1 >= this.x1) || (x2 >= this.x1 && x2 <= this.x2)) && ((y1 <= this.y2 && y1 >= this.y1) || (y2 >= this.y1 && y2 <= this.y2))) {
            return true;
        } else if (((this.x1 <= x2 && this.x1 >= x1) || (this.x2 >= x1 && this.x2 <= x2)) && ((this.y1 <= y2 && this.y1 >= y1) || (this.y2 >= y1 && this.y2 <= y2))) {
            return true;
        }
        return false;
    }

    public void updatePos(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void updatePos(int width, int height) {
        x2 = x1 + width;
        y2 = y1 + height;
    }

    public void updateX(int x, int width) {
        x1 = x;
        x2 = x + width;
    }

    public void updateY(int y, int height) {
        y1 = y;
        y2 = y + height;
    }
}