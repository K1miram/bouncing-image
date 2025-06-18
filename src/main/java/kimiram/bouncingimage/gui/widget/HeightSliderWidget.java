package kimiram.bouncingimage.gui.widget;

import kimiram.bouncingimage.BouncingImageClient;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import static kimiram.bouncingimage.config.BouncingImageConfig.configValues;

public class HeightSliderWidget extends SliderWidget {
    Text text;

    public HeightSliderWidget(int x, int y, int width, int height, Text text, double value) {
        super(x, y, width, height, text, value);
        this.text = text;
        this.setMessage(Text.of(text.getString() + (int) Math.round(value * BouncingImageClient.screenHeight / 2d)));
        this.value = value;
    }

    @Override
    protected void updateMessage() {
        this.setMessage(Text.of(text.getString() + (int) Math.round(value * BouncingImageClient.screenHeight / 2d)));
    }

    @Override
    protected void applyValue() {
        configValues.imageHeight = (int) Math.round(value * BouncingImageClient.screenHeight / 2d);
    }
}
