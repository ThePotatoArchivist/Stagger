package archives.tater.stagger;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import static java.lang.Math.*;

public class PoiseComponent implements AutoSyncedComponent, CommonTickingComponent {
    private final PlayerEntity player;

    private float damage = 0;
    private float lastDamage = 0;

    public PoiseComponent(PlayerEntity player) {
        this.player = player;
    }

    public float getDamage() {
        return damage;
    }

    public float getClientDamage(float tickDelta) {
        return lastDamage + (damage - lastDamage) * tickDelta;
    }

    public float getClientDamageProgress(float tickDelta) {
        return getClientDamage(tickDelta) / getMaxDamage();
    }

    public void setDamage(float damage) {
        this.damage = clamp(damage, 0, getMaxDamage());
        KEY.sync(player);
    }

    public void changeDamage(float damageChange) {
        damage = clamp(damage + damageChange, 0, getMaxDamage());
        KEY.sync(player);
    }

    public float getMaxDamage() {
        return (float) player.getAttributeValue(StaggerAttributes.MAX_POISE);
    }

    public boolean isMax() {
        return damage >= getMaxDamage();
    }

    public boolean isEmptyClient() {
        return lastDamage <= 0;
    }

    private static final String DAMAGE_NBT = "damage";

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        damage = nbtCompound.getFloat(DAMAGE_NBT);
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putFloat(DAMAGE_NBT, damage);
    }

    @Override
    public void tick() {
        lastDamage = this.damage;
        if (damage > 0)
            damage = max(0, damage - (float) player.getAttributeValue(StaggerAttributes.POISE_RATE));
    }

    public static final ComponentKey<PoiseComponent> KEY = ComponentRegistry.getOrCreate(Stagger.id("poise"), PoiseComponent.class);
}
