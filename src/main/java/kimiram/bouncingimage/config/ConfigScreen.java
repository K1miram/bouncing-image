package kimiram.bouncingimage.config;

import com.google.gson.Gson;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import static kimiram.bouncingimage.config.BouncingImageConfig.configValues;

public class ConfigScreen extends Screen {
    Screen parent;
    boolean isEnabled = configValues.isEnabled;

    public ConfigScreen(Screen parent) {
        super(Text.of(""));
        this.parent = parent;
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(parent);
    }

    @Override
    protected void init() {
        super.init();

        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), button -> doneButtonAction())
                .dimensions(width / 2 - 80, height - 40, 160, 20)
                .build());
    }

    public void doneButtonAction() {
        configValues.isEnabled = isEnabled;

        Gson gson = new Gson();
        String json = gson.toJson(configValues);

        close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);


    }
}
