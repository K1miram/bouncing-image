package kimiram.bouncingimage;

import kimiram.bouncingimage.config.BouncingImageConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static kimiram.bouncingimage.config.BouncingImageConfig.configValues;

public class BouncingImageClient implements ClientModInitializer {
    public static final String MOD_ID = "bouncing-image";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static int k = 0;
    public static int l = 0;
    public static int i = 1;
    public static int j = 1;

    public static int w = 0;
    public static int h = 0;

    public static int iw = 540 / 2;
    public static int ih = 254 / 2;

    @Override
    public void onInitializeClient() {
        BouncingImageConfig.initialize();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (configValues.isEnabled && w != 0 && h != 0) {
                k += i;
                l += j;

                if (k + iw >= w) {
                    i = -1;
                } else if (k <= 0) {
                    i = 1;
                }
                if (l + ih >= h) {
                    j = -1;
                } else if (l <= 0) {
                    j = 1;
                }
            }
        });
    }
}
