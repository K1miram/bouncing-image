package kimiram.bouncingimage.config;

import com.google.gson.Gson;
import kimiram.bouncingimage.BouncingImageClient;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BouncingImageConfig {
    public static Path file = Paths.get(FabricLoader.getInstance().getGameDir().toString() + "\\config\\bouncingimage.json");
    public static ConfigValues configValues = new ConfigValues();
    public static Gson gson = new Gson();

    public static void createFile() {
        try {
            Files.createFile(file);

            String json = gson.toJson(configValues);
            Files.writeString(file, json);
        } catch (Exception e) {
            BouncingImageClient.LOGGER.error("Failed to create bouncing image config file because: {}", e.toString());
        }
    }

    public static void readFile() {
        try {
            String json = Files.readString(file);
            configValues = gson.fromJson(json, ConfigValues.class);
        } catch (Exception e) {
            BouncingImageClient.LOGGER.error("Failed to read bouncing image config file because: {}", String.valueOf(e));
        }
    }

    public static void saveFile() {
        String json = gson.toJson(configValues);

        if (Files.exists(file)) {
            try {
                Files.writeString(BouncingImageConfig.file, json);
            } catch (Exception e) {
                BouncingImageClient.LOGGER.error("Failed to save bouncing image config because: {}", e.toString());
            }
        } else {
            createFile();
        }
    }

    public static void initialize() {
        if (Files.exists(file)) {
            readFile();
        } else {
            createFile();
        }
    }

    public static void applyImage(String link) {
        if (link != null && !link.isEmpty()) {
            try {
                URI uri = new URI(link);
                URL imageURL = uri.toURL();
                InputStream imageStream = imageURL.openStream();
                NativeImage image = NativeImage.read(imageStream);

                TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
                NativeImageBackedTexture texture = new NativeImageBackedTexture(image::toString, image);
                textureManager.registerTexture(Identifier.of(BouncingImageClient.MOD_ID, "textures/bouncing_image.png"), texture);

                configValues.imageUrl = link;
            } catch (Exception e) {
                BouncingImageClient.LOGGER.warn("Failed to load bouncing image because: {}", e.toString());
            }
        }
    }
}
