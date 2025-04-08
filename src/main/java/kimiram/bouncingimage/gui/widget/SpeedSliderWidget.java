package kimiram.bouncingimage.gui.widget;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import static kimiram.bouncingimage.config.BouncingImageConfig.configValues;

public class SpeedSliderWidget extends SliderWidget {
    Text text;

    public SpeedSliderWidget(int x, int y, int width, int height, Text text, double value) {
        super(x, y, width, height, text, value);
        this.text = Text.of(text.getString());
        this.setMessage(Text.of(text.getString() + value * 5d));
        this.value = value;
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Text.of(text.getString() + (double) Math.round(this.value * 5 * 10d) / 10d));
    }

    @Override
    protected void applyValue() {
        configValues.speed = (double) Math.round(this.value * 5 * 10d) / 10d;
    }
}
