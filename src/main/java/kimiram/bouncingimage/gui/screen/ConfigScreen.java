package kimiram.bouncingimage.gui.screen;

import kimiram.bouncingimage.BouncingImageClient;
import kimiram.bouncingimage.gui.widget.CustomElementListWidget;
import kimiram.bouncingimage.gui.widget.HeightSliderWidget;
import kimiram.bouncingimage.gui.widget.SpeedSliderWidget;
import kimiram.bouncingimage.gui.widget.WidthSliderWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import static kimiram.bouncingimage.config.BouncingImageConfig.*;

public class ConfigScreen extends Screen {
    Screen parent;
    Text isEnabledButtonText;

    public ConfigScreen(Screen parent) {
        super(Text.of("Bouncing Image Options"));
        this.parent = parent;
    }

    @Override
    public void close() {
        saveFile();
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

        SpeedSliderWidget speedSliderWidget = new SpeedSliderWidget(
                width / 2 + 10, 40, 150, 20,
                Text.literal("Speed: "), configValues.speed / 5d);

        TextFieldWidget imageLinkField = new TextFieldWidget(
                textRenderer, width / 2 - 160, 70, 220, 20, Text.literal("idk"));
        imageLinkField.setMaxLength(256);

        ButtonWidget applyImageButton = ButtonWidget.builder(Text.literal("Apply"), button -> {
                    String link = imageLinkField.getText();
                    applyImage(link);
                })
                .dimensions(width / 2 + 80, 70, 80, 20)
                .build();

        WidthSliderWidget widthSliderWidget = new WidthSliderWidget(
                width / 2 - 160, 100, 150, 20,
                Text.literal("Width: "),
                (double) configValues.imageWidth / ((double) BouncingImageClient.screenWidth / 2d));

        HeightSliderWidget heightSliderWidget = new HeightSliderWidget(
                width / 2 + 10, 100, 150, 20,
                Text.literal("Height: "),
                (double) configValues.imageHeight / ((double) BouncingImageClient.screenHeight / 2d));

        CustomElementListWidget elementList = new CustomElementListWidget(
                client, width, height - 60, 30, 25, this);
        elementList.AddWidgets(isEnabledButton, speedSliderWidget, imageLinkField, applyImageButton, widthSliderWidget, heightSliderWidget);

        addDrawableChild(elementList);

        ButtonWidget changeCollisionBoxes = ButtonWidget.builder(
                Text.literal("Change Collision Boxes"),
                button -> {
                    MinecraftClient.getInstance().setScreen(new CollisionBoxesScreen(this));
                })
                .dimensions(width / 2 - 155, height - 25, 150, 20)
                .build();
        addDrawableChild(changeCollisionBoxes);

        ButtonWidget doneButton = ButtonWidget.builder(
                Text.literal("Done"),
                button -> close())
                .dimensions(width / 2 + 5, height - 25, 150, 20)
                .build();
        addDrawableChild(doneButton);


    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);

        context.drawText(textRenderer, "Bouncing Image Options", (width - (int) textRenderer.getTextHandler().getWidth("Bouncing Image Options")) / 2, 10, 0xFFFFFF, false);
    }
}
