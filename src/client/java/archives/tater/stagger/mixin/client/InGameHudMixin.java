package archives.tater.stagger.mixin.client;

import archives.tater.stagger.PoiseMeterRenderer;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	@Shadow @Final private MinecraftClient client;

	@Shadow public abstract void tick(boolean paused);

	@WrapWithCondition(method = "renderMainHud", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderExperienceBar(Lnet/minecraft/client/gui/DrawContext;I)V"))
	private boolean init(InGameHud instance, DrawContext context, int x, @Local(argsOnly = true)RenderTickCounter tickCounter) {
		if (!PoiseMeterRenderer.shouldRender(client.player)) return true;

		PoiseMeterRenderer.render(context, x, client.player, tickCounter.getTickDelta(false));
		return false;
	}
}