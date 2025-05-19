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

    private float poise = 0;
    private float lastPoise = 0;

    public PoiseComponent(PlayerEntity player) {
        this.player = player;
    }

    public float getPoise() {
        return poise;
    }

    public float getClientPoise(float tickDelta) {
        return lastPoise + (poise - lastPoise) * tickDelta;
    }

    public float getClientPoiseProgress(float tickDelta) {
        return getClientPoise(tickDelta) / getMaxPoise();
    }

    public void setPoise(float poise) {
        this.poise = clamp(poise, 0, getMaxPoise());
        KEY.sync(player);
    }

    public void changePoise(float poiseChange) {
        poise = clamp(poise + poiseChange, 0, getMaxPoise());
        KEY.sync(player);
    }

    public float getMaxPoise() {
        return (float) player.getAttributeValue(StaggerAttributes.MAX_POISE);
    }

    public boolean isMax() {
        return poise >= getMaxPoise();
    }

    public boolean isMaxClient() {
        return lastPoise >= getMaxPoise();
    }

    private static final String POISE_NBT = "poise";

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        poise = nbtCompound.getFloat(POISE_NBT);
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putFloat(POISE_NBT, poise);
    }

    @Override
    public void tick() {
        lastPoise = this.poise;
        var maxPoise = getMaxPoise();
        if (poise < maxPoise)
            poise = min(maxPoise, poise + (float) player.getAttributeValue(StaggerAttributes.POISE_RATE));
    }

    public static final ComponentKey<PoiseComponent> KEY = ComponentRegistry.getOrCreate(Stagger.id("poise"), PoiseComponent.class);
}
