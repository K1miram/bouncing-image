package kimiram.bouncingimage.mixin;

import kimiram.bouncingimage.BouncingImageClient;
import kimiram.bouncingimage.ModMenuInteraction;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static kimiram.bouncingimage.BouncingImageClient.*;

@Mixin(Screen.class)
public class ScreensMixin {
    @Shadow
    public int width;
    @Shadow
    public int height;


    @Inject(at = @At("RETURN"), method = "render")
    public void addImage(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (true) {
            w = width;
            h = height;

            context.drawTexture(RenderLayer::getGuiTexturedOverlay, Identifier.of(BouncingImageClient.MOD_ID, "textures/bouncing_image.png"), k, l, 0, 0, iw, ih, iw, ih, iw, ih);

            if (k + iw >= w) {
                i = -1;
            } else if (k <= 0) {
                i = 1;
            }
            if (l + ih >= h) {
                j = -1;
            } else if (l <= 0) {
                j = 1;
            }
        }
    }
}
