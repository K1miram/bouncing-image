package kimiram.bouncingimage.config;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class AreYouSureScreen extends Screen {
    Screen parent;

    public AreYouSureScreen(Screen parent) {
        super(Text.literal(""));
        this.parent = parent;
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(parent);
    }

    @Override
    protected void init() {

    }
}
