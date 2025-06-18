package kimiram.bouncingimage.config;

import java.util.LinkedList;
import java.util.List;

public class ConfigValues {
    public boolean isEnabled = true;
    public String imageUrl;
    public int imageWidth = 540 / 2;
    public int imageHeight = 254 / 2;
    public double speed = 1.0;
    public List<CollisionBox> collisionBoxes = new LinkedList<>(List.of());
}
