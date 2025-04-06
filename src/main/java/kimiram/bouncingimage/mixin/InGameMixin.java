package kimiram.bouncingimage.mixin;

import kimiram.bouncingimage.BouncingImageClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
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
            context.drawTexture(RenderLayer::getGuiTexturedOverlay, Identifier.of(BouncingImageClient.MOD_ID, "textures/bouncing_image.png"), k, l, 0, 0, iw, ih, iw, ih, iw, ih);
        }
    }
}
