package kimiram.bouncingimage;

import kimiram.bouncingimage.config.BouncingImageConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import static kimiram.bouncingimage.config.BouncingImageConfig.configValues;

public class BouncingImageClient implements ClientModInitializer {
    public static final String MOD_ID = "bouncing-image";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static double imageX = 0;
    public static double imageY = 0;
    public static double deltaX = 1;
    public static double deltaY = 1;

    public static int screenWidth = 0;
    public static int screenHeight = 0;

    public static Identifier image = Identifier.of(MOD_ID, "textures/bouncing_image.png");

    @Override
    public void onInitializeClient() {
        BouncingImageConfig.initialize();

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            try {
                URI uri = new URI(configValues.imageUrl);
                URL imageURL = uri.toURL();
                InputStream imageStream = imageURL.openStream();
                NativeImage image = NativeImage.read(imageStream);

                TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
                NativeImageBackedTexture texture = new NativeImageBackedTexture(image);
                textureManager.registerTexture(Identifier.of(MOD_ID, "textures/bouncing_image.png"), texture);
            } catch (Exception e) {
                if (configValues.imageUrl != null) {
                    LOGGER.warn("Failed to load bouncing image because: {}", e.toString());
                }
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (configValues.isEnabled && screenWidth != 0 && screenHeight != 0) {
                imageX += deltaX * configValues.speed;
                imageY += deltaY * configValues.speed;

                if (imageX + configValues.imageWidth >= screenWidth) {
                    deltaX = -1;
                } else if (imageX <= 0) {
                    deltaX = 1;
                }
                if (imageY + configValues.imageHeight >= screenHeight) {
                    deltaY = -1;
                } else if (imageY <= 0) {
                    deltaY = 1;
                }
            }
        });
    }
}
