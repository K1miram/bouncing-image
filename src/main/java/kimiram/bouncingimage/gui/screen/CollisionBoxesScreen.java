package kimiram.bouncingimage.gui.screen;

import kimiram.bouncingimage.config.CollisionBox;
import kimiram.bouncingimage.gui.widget.CollisionBoxWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;

import static kimiram.bouncingimage.config.BouncingImageConfig.configValues;

public class CollisionBoxesScreen extends Screen {
    Screen parent;

    public CollisionBoxesScreen(Screen parent) {
        super(Text.literal("idk"));
        this.parent = parent;
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(parent);
    }

    CheckboxWidget renderInGameHudCheckbox;

    @Override
    protected void init() {
        for (int i = 0; i < configValues.collisionBoxes.size(); i++) {
            CollisionBox box = configValues.collisionBoxes.get(i);
            addDrawableChild(new CollisionBoxWidget(box.x1, box.y1, box.x2 - box.x1, box.y2 - box.y1, i, this));
        }

        ButtonWidget addBoxButton = ButtonWidget.builder(
                Text.literal("Add Box"),
                button -> {
                    configValues.collisionBoxes.add(new CollisionBox(width / 2 - 25, height / 2 - 25, width / 2 + 25, height / 2 + 25));
                    addDrawableChild(new CollisionBoxWidget(width / 2 - 25, height / 2 - 25, 50, 50, configValues.collisionBoxes.size() - 1, this));
                })
                .dimensions(width / 2 - 155, height - 25, 150, 20)
                .tooltip(Tooltip.of(Text.literal("Controls:\nArrows: increase scale of box, with shift - decrease.\nMouse wheel: change height, with shift - width.\nRight mouse button: remove box.")))
                .build();
        addDrawableChild(addBoxButton);

        ButtonWidget doneButton = ButtonWidget.builder(
                Text.literal("Done"),
                button -> close())
                .dimensions(width / 2 + 5, height - 25, 150, 20)
                .build();
        addDrawableChild(doneButton);
    }

    public void update() {
        MinecraftClient.getInstance().setScreen(new CollisionBoxesScreen(parent));
    }
}