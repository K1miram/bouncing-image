package kimiram.bouncingimage.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static kimiram.bouncingimage.BouncingImageClient.*;
import static kimiram.bouncingimage.config.BouncingImageConfig.configValues;

@Mixin(value = InGameHud.class)
public class InGameMixin {
    @Inject(at = @At("RETURN"), method = "render")
    public void addImage(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (configValues.isEnabled) {
            context.drawTexture(RenderLayer::getGuiTexturedOverlay, image, MathHelper.floor(imageX), MathHelper.floor(imageY), 0, 0, configValues.imageWidth, configValues.imageHeight, configValues.imageWidth, configValues.imageHeight, configValues.imageWidth, configValues.imageHeight);
        }
    }
}
