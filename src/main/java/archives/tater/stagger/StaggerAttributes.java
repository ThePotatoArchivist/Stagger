package archives.tater.stagger;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute.Category;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class StaggerAttributes {
    private StaggerAttributes() {}

    private static RegistryEntry<EntityAttribute> register(String path, EntityAttribute attribute) {
        return Registry.registerReference(Registries.ATTRIBUTE, Stagger.id(path), attribute);
    }

    private static String playerAttr(String name) {
        return "attribute.name." + Stagger.MOD_ID + '.' + name;
    }

    private static RegistryEntry<EntityAttribute> registerClamped(String name, double fallback, double min, double max, Category category) {
        return register(name, new ClampedEntityAttribute(playerAttr(name), fallback, min, max).setTracked(true).setCategory(category));
    }

    // TODO make these values named constants

    public static final RegistryEntry<EntityAttribute> MAX_POISE = registerClamped("max_poise", Stagger.CONFIG.baseMaxPoise, 0, 100, Category.POSITIVE);
    public static final RegistryEntry<EntityAttribute> POISE_RATE = registerClamped("poise_rate", Stagger.CONFIG.basePoiseRate, 0, 2, Category.POSITIVE);
    public static final RegistryEntry<EntityAttribute> STAGGER_LENGTH = registerClamped("stagger_length", Stagger.CONFIG.baseStaggerLength, 0, 10, Category.NEGATIVE);
    public static final RegistryEntry<EntityAttribute> POISE_DAMAGE = registerClamped("poise_damage", 1, 0, 4, Category.POSITIVE);

    public static final Identifier WEAPON_POISE_DAMAGE = Stagger.id("weapon_poise_damage");

    public static void addLivingEntityAttributes(DefaultAttributeContainer.Builder attributeBuilder) {
        attributeBuilder.add(POISE_DAMAGE);
    }

    public static void addPlayerAttributes(DefaultAttributeContainer.Builder attributeBuilder) {
        attributeBuilder
                .add(MAX_POISE)
                .add(POISE_RATE)
                .add(STAGGER_LENGTH);
    }

    public static void register() {}
}
