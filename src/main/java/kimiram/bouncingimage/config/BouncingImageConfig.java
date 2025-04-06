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
    public static ConfigValues defaultConfigValues = new ConfigValues(true);

    public static void initialize() {
        if (Files.notExists(file)) {
            try {
                Files.createFile(file);

                Gson gson = new Gson();
                String json = gson.toJson(defaultConfigValues);
                Files.writeString(file, json);
                configValues = defaultConfigValues;

                BouncingImageClient.LOGGER.info("created bouncing image config file at: {}", file.toString());
            } catch (Exception e) {
                BouncingImageClient.LOGGER.error("failed to create bouncing image config file because: {}", String.valueOf(e));
            }
        } else {
            try {
                String json = Files.readString(file);
                Gson gson = new Gson();
                configValues = gson.fromJson(json, ConfigValues.class);
            } catch (Exception e) {
                BouncingImageClient.LOGGER.error("failed to read bouncing image config file because: {}", String.valueOf(e));
                configValues = defaultConfigValues;
            }
        }
    }
}
