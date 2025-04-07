package kimiram.bouncingimage.config;

import com.google.gson.Gson;
import kimiram.bouncingimage.BouncingImageClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.nio.file.Files;

import static kimiram.bouncingimage.config.BouncingImageConfig.configValues;

public class ConfigScreen extends Screen {
    Screen parent;
    Text isEnabledButtonText;

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
        if (configValues.isEnabled) {
            isEnabledButtonText = Text.literal("Enabled: §aYes");
        } else {
            isEnabledButtonText = Text.literal("Enabled: §cNo");
        }

        addDrawableChild(ButtonWidget.builder(isEnabledButtonText, button -> {
            configValues.isEnabled = !configValues.isEnabled;

            if (configValues.isEnabled) {
                isEnabledButtonText = Text.literal("Enabled: §aYes");
            } else {
                isEnabledButtonText = Text.literal("Enabled: §cNo");
            }

            button.setMessage(isEnabledButtonText);
        })
                .dimensions(width / 2 - 160, 40, 150, 20)
                .build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), button -> doneButtonAction())
                .dimensions(width / 2 - 100, height - 25, 200, 20)
                .build());
    }

    public void doneButtonAction() {
        Gson gson = new Gson();
        String json = gson.toJson(configValues);

        if (Files.exists(BouncingImageConfig.file)) {
            try {
                Files.writeString(BouncingImageConfig.file, json);
            } catch (Exception e) {
                BouncingImageClient.LOGGER.error("Failed save bouncing image config because: {}", e.toString());
            }
        } else {
            try {
                Files.createFile(BouncingImageConfig.file);
                Files.writeString(BouncingImageConfig.file, json);
            } catch (Exception e) {
                BouncingImageClient.LOGGER.error("Failed to create bouncing image config file because: {}", e.toString());
            }
        }

        close();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        super.blur();

        context.drawText(textRenderer, "Bouncing Image Options", (width - (int) textRenderer.getTextHandler().getWidth("Bouncing Image Options")) / 2, 10, 0xFFFFFF, false);
    }
}
