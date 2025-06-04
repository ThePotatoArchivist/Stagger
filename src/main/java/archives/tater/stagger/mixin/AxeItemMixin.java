package archives.tater.stagger.mixin;

import archives.tater.stagger.StaggerAttributes;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Items.class)
public class AxeItemMixin {
    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/AxeItem;createAttributeModifiers(Lnet/minecraft/item/ToolMaterial;FF)Lnet/minecraft/component/type/AttributeModifiersComponent;")
    )
    private static AttributeModifiersComponent addPoiseMultiplier(AttributeModifiersComponent original) {
        return original.with(
                StaggerAttributes.POISE_DAMAGE,
                new EntityAttributeModifier(StaggerAttributes.WEAPON_POISE_DAMAGE, 0.5, Operation.ADD_MULTIPLIED_BASE),
                AttributeModifierSlot.MAINHAND
        );
    }
}
