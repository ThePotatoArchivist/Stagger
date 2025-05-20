package archives.tater.stagger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Math.max;

public class Stagger implements ModInitializer, EntityComponentInitializer {
	public static final String MOD_ID = "stagger";

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
		entityComponentFactoryRegistry.registerFor(PlayerEntity.class, PoiseComponent.KEY, PoiseComponent::new);
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		StaggerAttributes.register();
		ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamageTaken, damageTaken, blocked) -> {
            if (!blocked || !(entity instanceof PlayerEntity player)) return;
			var poiseComponent = PoiseComponent.KEY.get(player);
			poiseComponent.changeDamage(max(baseDamageTaken - damageTaken, 0));
			if (poiseComponent.isMax()) {
				var blockingItem = player.getActiveItem();
				if (blockingItem.isEmpty()) return;
				player.getItemCooldownManager().set(blockingItem.getItem(), (int) (20 * player.getAttributeValue(StaggerAttributes.STAGGER_LENGTH)));
				player.stopUsingItem();
				player.getWorld().playSoundFromEntity(null, player, SoundEvents.ITEM_SHIELD_BREAK, player.getSoundCategory(), 1f, 1f);
			}
        });
	}
}