package kimiram.bouncingimage.config;

import com.google.gson.Gson;
import kimiram.bouncingimage.BouncingImageClient;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BouncingImageConfig {
    public static Path file = Paths.get(FabricLoader.getInstance().getGameDir().toString() + "\\config\\bouncingimage.json");
    public static ConfigValues configValues;

    public static void initialize() {
        if (Files.notExists(file)) {
            try {
                Files.createFile(file);
                configValues = new ConfigValues();

                Gson gson = new Gson();
                String json = gson.toJson(configValues);
                Files.writeString(file, json);
            } catch (Exception e) {
                BouncingImageClient.LOGGER.error("Failed to create bouncing image config file because: {}", String.valueOf(e));
            }
        } else {
            try {
                String json = Files.readString(file);
                Gson gson = new Gson();
                configValues = gson.fromJson(json, ConfigValues.class);
            } catch (Exception e) {
                BouncingImageClient.LOGGER.error("Failed to read bouncing image config file because: {}", String.valueOf(e));
                configValues = new ConfigValues();
            }
        }
    }
}
