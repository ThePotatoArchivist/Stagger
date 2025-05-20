package archives.tater.stagger.mixin;

import archives.tater.stagger.StaggerAttributes;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@ModifyReturnValue(
			method = "createPlayerAttributes",
			at = @At("RETURN")
	)
	private static DefaultAttributeContainer.Builder addStaggerAttributes(DefaultAttributeContainer.Builder original) {
		StaggerAttributes.addAttributes(original);
		return original;
	}
}