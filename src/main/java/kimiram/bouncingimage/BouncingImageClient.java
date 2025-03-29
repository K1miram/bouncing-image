package kimiram.bouncingimage;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BouncingImageClient implements ClientModInitializer {
    public static final String MOD_ID = "bouncing-image";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static int k = 0;
    public static int l = 0;
    public static int i = 1;
    public static int j = 1;

    public static int w;
    public static int h;

    public static int iw = 540 / 2;
    public static int ih = 254 / 2;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Bouncing Image is initialized");
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (true) {
                k += i;
                l += j;
            }
        });
    }
}
