package kimiram.bouncingimage.gui.screen;

import com.google.gson.Gson;
import kimiram.bouncingimage.BouncingImageClient;
import kimiram.bouncingimage.config.BouncingImageConfig;
import kimiram.bouncingimage.gui.widget.HeightSliderWidget;
import kimiram.bouncingimage.gui.widget.SpeedSliderWidget;
import kimiram.bouncingimage.gui.widget.WidthSliderWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;

import static kimiram.bouncingimage.config.BouncingImageConfig.configValues;

public class ConfigScreen extends Screen {
    Screen parent;
    Text isEnabledButtonText;

    public ConfigScreen(Screen parent) {
        super(Text.of("Bouncing Image Options"));
        this.parent = parent;
    }

    @Override
    public void close() {
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

        MinecraftClient.getInstance().setScreen(parent);
    }

    @Override
    protected void init() {
        if (configValues.isEnabled) {
            isEnabledButtonText = Text.literal("Enabled: §aYes");
        } else {
            isEnabledButtonText = Text.literal("Enabled: §cNo");
        }

        ButtonWidget isEnabledButton = ButtonWidget.builder(isEnabledButtonText, button -> {
                    configValues.isEnabled = !configValues.isEnabled;

                    if (configValues.isEnabled) {
                        isEnabledButtonText = Text.literal("Enabled: §aYes");
                    } else {
                        isEnabledButtonText = Text.literal("Enabled: §cNo");
                    }

                    button.setMessage(isEnabledButtonText);
                })
                .dimensions(width / 2 - 160, 40, 150, 20)
                .build();
        addDrawableChild(isEnabledButton);

        SpeedSliderWidget speedSliderWidget = new SpeedSliderWidget(width / 2 + 10, 40, 150, 20, Text.literal("Speed: "), (double) Math.round(configValues.speed * 2d) / 10d);
        addDrawableChild(speedSliderWidget);

        TextFieldWidget imageLinkField = new TextFieldWidget(textRenderer, width / 2 - 160, 70, 230, 20, Text.literal("idk"));
        imageLinkField.setMaxLength(256);
        addDrawableChild(imageLinkField);

        ButtonWidget applyImageButton = ButtonWidget.builder(Text.literal("Apply"), button -> {
                    configValues.imageUrl = imageLinkField.getText();

                    try {
                        URI uri = new URI(configValues.imageUrl);
                        URL imageURL = uri.toURL();
                        InputStream imageStream = imageURL.openStream();
                        NativeImage image = NativeImage.read(imageStream);

                        TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
                        NativeImageBackedTexture texture = new NativeImageBackedTexture(image::toString, image);
                        textureManager.registerTexture(Identifier.of(BouncingImageClient.MOD_ID, "textures/bouncing_image.png"), texture);
                    } catch (Exception e) {
                        if (configValues.imageUrl != null) {
                            BouncingImageClient.LOGGER.warn("Failed to load bouncing image because: {}", e.toString());
                        }
                    }
                })
                .dimensions(width / 2 + 80, 70, 80, 20)
                .build();
        addDrawableChild(applyImageButton);

        WidthSliderWidget widthSliderWidget = new WidthSliderWidget(width / 2 - 160, 100, 150, 20, Text.literal("Width: "), (double) configValues.imageWidth / ((double) BouncingImageClient.screenWidth / 2d));
        addDrawableChild(widthSliderWidget);

        HeightSliderWidget heightSliderWidget = new HeightSliderWidget(width / 2 + 10, 100, 150, 20, Text.literal("Height: "), (double) configValues.imageHeight / ((double) BouncingImageClient.screenHeight / 2d));
        addDrawableChild(heightSliderWidget);

        ButtonWidget doneButton = ButtonWidget.builder(Text.literal("Done"), button -> close())
                .dimensions(width / 2 - 100, height - 25, 200, 20)
                .build();
        addDrawableChild(doneButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);

        context.drawText(textRenderer, "Bouncing Image Options", (width - (int) textRenderer.getTextHandler().getWidth("Bouncing Image Options")) / 2, 10, 0xFFFFFF, false);
    }
}
