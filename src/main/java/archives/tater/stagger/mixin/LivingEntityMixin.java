package archives.tater.stagger.mixin;

import archives.tater.stagger.StaggerAttributes;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @ModifyReturnValue(
            method = "createLivingAttributes",
            at = @At("RETURN")
    )
    private static DefaultAttributeContainer.Builder addStaggerAttributes(DefaultAttributeContainer.Builder original) {
        StaggerAttributes.addLivingEntityAttributes(original);
        return original;
    }

    @ModifyExpressionValue(
            method = "isBlocking",
            at = @At(value = "CONSTANT", args = "intValue=5")
    )
    private int removeShieldDelay(int original) {
        return 0;
    }

    @WrapOperation(
            method = "disablesShield",
            at = @At(value = "CONSTANT", args = "classValue=net/minecraft/item/AxeItem")
    )
    private boolean removeAxeShieldDisable(Object object, Operation<Boolean> original) {
        return false;
    }
}
