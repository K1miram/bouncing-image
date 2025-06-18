package kimiram.bouncingimage.gui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.Widget;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomElementListWidget extends ElementListWidget<CustomElementListWidget.WidgetEntry> {
    Screen screen;

    public CustomElementListWidget(MinecraftClient minecraftClient, int i, int j, int k, int l, Screen screen) {
        super(minecraftClient, i, j, k, l);
        this.screen = screen;
    }

    public void AddWidgets(ClickableWidget... widgets) {
        for (int i = 0; i < widgets.length; i += 2) {
            this.addEntry(WidgetEntry.create(widgets[i], i < widgets.length - 1 ? widgets[i + 1] : null, screen));
        }
    }

    @Override
    public int getRowWidth() {
        return 310;
    }

    static class WidgetEntry extends ElementListWidget.Entry<WidgetEntry> {
        List<ClickableWidget> widgets;
        Screen screen;

        WidgetEntry(List<ClickableWidget> widgets, Screen screen) {
            this.widgets = widgets;
            this.screen = screen;
        }

        public static WidgetEntry create(ClickableWidget firstWidget, @Nullable ClickableWidget secondWidget, Screen screen) {
            if (secondWidget != null) {
                return new WidgetEntry(List.of(firstWidget, secondWidget), screen);
            } else {
                return new WidgetEntry(List.of(firstWidget), screen);
            }
        }

        @Override
        public List<? extends Selectable> selectableChildren() {
            return widgets;
        }

        @Override
        public List<? extends Element> children() {
            return widgets;
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickProgress) {
            int i = 0;
            int j = screen.width / 2 - 155;

            for (ClickableWidget widget: widgets) {
                widget.setPosition(i + j, y);
                widget.render(context, mouseX, mouseY, tickProgress);
                i += widget.getWidth() + 10;
            }
        }
    }
}