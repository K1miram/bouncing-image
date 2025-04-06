package kimiram.bouncingimage.mixin;

import kimiram.bouncingimage.BouncingImageClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static kimiram.bouncingimage.BouncingImageClient.*;
import static kimiram.bouncingimage.config.BouncingImageConfig.configValues;

@Mixin(Screen.class)
public class ScreensMixin {

    @Inject(at = @At("RETURN"), method = "init(Lnet/minecraft/client/MinecraftClient;II)V")
    public final void onInit(MinecraftClient client, int width, int height, CallbackInfo ci) {
        w = width;
        h = height;
    }

    @Inject(at = @At("RETURN"), method = "resize")
    public void onResize(MinecraftClient client, int width, int height, CallbackInfo ci) {
        w = width;
        h = height;
    }

    @Inject(at = @At("RETURN"), method = "render")
    public void addImage(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (configValues.isEnabled) {
            context.drawTexture(RenderLayer::getGuiTexturedOverlay, Identifier.of(BouncingImageClient.MOD_ID, "textures/bouncing_image.png"), k, l, 0, 0, iw, ih, iw, ih, iw, ih);
        }
    }
}
