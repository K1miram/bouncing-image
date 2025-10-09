package kimiram.bouncingimage.gui.widget;

import kimiram.bouncingimage.gui.screen.CollisionBoxesScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

import static kimiram.bouncingimage.BouncingImageClient.*;
import static kimiram.bouncingimage.config.BouncingImageConfig.configValues;

public class CollisionBoxWidget extends ClickableWidget {
    double x;
    double y;
    int color;
    int index;
    Screen screen;

    public CollisionBoxWidget(int x, int y, int width, int height, int index, Screen screen) {
        super(x, y, width, height, Text.empty());
        this.x = x;
        this.y = y;
        this.color = colors.get(index % 8);
        this.index = index;
        this.screen = screen;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        if (this.isSelected()) {
            context.drawStrokedRectangle((int) x, (int) y, width, height, color);
            context.fill((int) x, (int) y, (int) x + width, (int) y + height, color - 1761607680);
        } else {
            context.drawStrokedRectangle((int) x, (int) y, width, height, color - 1761607680);
            context.fill((int) x, (int) y, (int) x + width, (int) y + height, color + 855638016);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    protected void onDrag(Click click, double offsetX, double offsetY) {
        if (x + width + offsetX > screen.width) {
            x = screen.width - width;
        } else if (x + offsetX < 0) {
            x = 0;
        } else {
            x += offsetX;
        }
        if (y + height + offsetY > screen.height) {
            y = screen.height - height;
        } else if (y + offsetY < 0) {
            y = 0;
        } else {
            y += offsetY;
        }

        configValues.collisionBoxes.get(index).updatePos((int) x, (int) y, (int) x + width, (int) y + height);
        this.setPosition((int) x, (int) y);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (MinecraftClient.getInstance().isShiftPressed()) {
            if (verticalAmount > 0 && this.getRight() < screen.width) {
                width += (int) verticalAmount;

                configValues.collisionBoxes.get(index).updateX((int) x, width);
                return true;
            } else if (verticalAmount < 0 && width > 5) {
                width += (int) verticalAmount;

                configValues.collisionBoxes.get(index).updateX((int) x, width);
                return true;
            }
        } else {
            if (verticalAmount > 0 && this.getBottom() < screen.height) {
                height += (int) verticalAmount;

                configValues.collisionBoxes.get(index).updateY((int) y, height);
                return true;
            } else if (verticalAmount < 0 && height > 5) {
                height += (int) verticalAmount;

                configValues.collisionBoxes.get(index).updateY((int) y, height);
                return true;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (super.mouseClicked(click, doubled)) {
            return true;
        } else if (active && visible && click.button() == 1) {
            configValues.collisionBoxes.remove(index);

            this.playDownSound(MinecraftClient.getInstance().getSoundManager());

            ((CollisionBoxesScreen) screen).update();
            return true;
        }
        return false;
    }




//
//    OLD CODE I'LL FIX SOMEDAY
//
//    @Override
//    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
//        switch (keyCode) {
//            case 262:
//                if (!shiftPressed) {
//                    if (this.getRight() < screen.width) {
//                        width++;
//
//                        configValues.collisionBoxes.get(index).updateX((int) x, width);
//                    }
//                } else {
//                    if (width > 5) {
//                        x++;
//                        width--;
//                        this.setX((int) x);
//
//                        configValues.collisionBoxes.get(index).updateX((int) x, width);
//                    }
//                }
//                return true;
//            case 263:
//                if (!shiftPressed) {
//                    if (this.getX() > 0) {
//                        x--;
//                        width++;
//                        this.setX((int) x);
//
//                        configValues.collisionBoxes.get(index).updateX((int) x, width);
//                    }
//                } else {
//                    if (width > 5) {
//                        width--;
//
//                        configValues.collisionBoxes.get(index).updateX((int) x, width);
//                    }
//                }
//                return true;
//            case 264:
//                if (!shiftPressed) {
//                    if (this.getBottom() < screen.height) {
//                        height++;
//
//                        configValues.collisionBoxes.get(index).updateY((int) y, height);
//                    }
//                } else {
//                    if (height > 5) {
//                        y++;
//                        height--;
//                        this.setY((int) y);
//
//                        configValues.collisionBoxes.get(index).updateY((int) y, height);
//                    }
//                }
//                return true;
//            case 265:
//                if (!shiftPressed) {
//                    if (this.getY() > 0) {
//                        y--;
//                        height++;
//                        this.setY((int) y);
//
//                        configValues.collisionBoxes.get(index).updateY((int) y, height);
//                    }
//                } else {
//                    if (height > 5) {
//                        height--;
//
//                        configValues.collisionBoxes.get(index).updateY((int) y, height);
//                    }
//                }
//                return true;
//            default:
//                return super.keyPressed(keyCode, scanCode, modifiers);
//        }
//    }
}
