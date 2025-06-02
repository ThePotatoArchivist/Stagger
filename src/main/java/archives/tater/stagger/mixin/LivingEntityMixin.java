package archives.tater.stagger.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @ModifyExpressionValue(
            method = "isBlocking",
            at = @At(value = "CONSTANT", args = "intValue=5")
    )
    private int removeShieldDelay(int original) {
        return 0;
    }
}
