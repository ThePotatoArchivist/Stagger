package archives.tater.stagger;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class StaggerAttributes {
    private StaggerAttributes() {}

    private static RegistryEntry<EntityAttribute> register(String path, EntityAttribute attribute) {
        return Registry.registerReference(Registries.ATTRIBUTE, Stagger.id(path), attribute);
    }

    private static String playerAttr(String name) {
        return Stagger.MOD_ID + ".player." + name;
    }

    private static RegistryEntry<EntityAttribute> registerClamped(String name, double fallback, double min, double max) {
        return register(name, new ClampedEntityAttribute(playerAttr(name), fallback, min, max).setTracked(true));
    }

    // TODO make these values named constants

    public static final RegistryEntry<EntityAttribute> MAX_POISE = registerClamped("max_poise", 20, 0, 100);
    public static final RegistryEntry<EntityAttribute> POISE_RATE = registerClamped("poise_rate", 0.025, 0, 2);
    public static final RegistryEntry<EntityAttribute> STAGGER_LENGTH = registerClamped("stagger_length", 5 * 20, 0, 10 * 20);

    public static void addAttributes(DefaultAttributeContainer.Builder attributeBuilder) {
        attributeBuilder
                .add(MAX_POISE)
                .add(POISE_RATE)
                .add(STAGGER_LENGTH)
        ;
    }

    public static void register() {}
}
