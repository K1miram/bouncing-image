package kimiram.bouncingimage;

import kimiram.bouncingimage.config.BouncingImageConfig;
import kimiram.bouncingimage.config.CollisionBox;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static kimiram.bouncingimage.config.BouncingImageConfig.*;

public class BouncingImageClient implements ClientModInitializer {
    public static final String MOD_ID = "bouncing-image";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static double imageX = 0;
    public static double imageY = 0;
    public static double dX = 1;
    public static double dY = 1;

    public static boolean change_dX = false;
    public static boolean change_dY = false;

    public static int screenWidth = 0;
    public static int screenHeight = 0;

    public static Identifier image = Identifier.of(MOD_ID, "textures/bouncing_image.png");

    public static List<Integer> colors = List.of(-16777216, -65536, -16711936, -16776961, -256, -65281, -16711681, -1);

    @Override
    public void onInitializeClient() {
        BouncingImageConfig.initialize();

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> applyImage(configValues.imageUrl));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (configValues.isEnabled && screenWidth != 0 && screenHeight != 0) {
                imageX += dX * configValues.speed;
                imageY += dY * configValues.speed;

                if (imageX + configValues.imageWidth >= screenWidth) {
                    dX = -1;
                } else if (imageX <= 0) {
                    dX = 1;
                }
                if (imageY + configValues.imageHeight >= screenHeight) {
                    dY = -1;
                } else if (imageY <= 0) {
                    dY = 1;
                }

                for (CollisionBox box: configValues.collisionBoxes) {
                    box.checkOverlaps();
                }

                if (change_dX) {
                    dX = -dX;
                    change_dX = false;
                }
                if (change_dY) {
                    dY = -dY;
                    change_dY = false;
                }
            }
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            saveFile();
        });
    }
}
