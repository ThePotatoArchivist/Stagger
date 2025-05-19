package archives.tater.stagger;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;

public class PoiseMeterRenderer {
    private PoiseMeterRenderer() {}

    private static final Identifier POISE_BAR_BACKGROUND_TEXTURE = Stagger.id("textures/gui/sprites/poise_bar_background.png");
    private static final Identifier POISE_BAR_PROGRESS_TEXTURE = Stagger.id("textures/gui/sprites/poise_bar_progress.png");

    public static boolean shouldRender(ClientPlayerEntity player) {
        return !PoiseComponent.KEY.get(player).isMaxClient();
    }

    public static void render(DrawContext context, int x, ClientPlayerEntity player, float tickDelta) {
        int width = 182;
        int progress = (int)((1 - PoiseComponent.KEY.get(player).getClientPoiseProgress(tickDelta)) * width / 2);
        int y = context.getScaledWindowHeight() - 32 + 3;
        RenderSystem.enableBlend();
        context.drawTexture(POISE_BAR_BACKGROUND_TEXTURE, x, y, 0, 0, width, 5, width, 5);
        if (progress > 0) {
            context.drawTexture(POISE_BAR_PROGRESS_TEXTURE, x + width / 2 - progress, y, progress * 2, 5, -progress, 0, progress * 2, 5, width, 5);
        }

        RenderSystem.disableBlend();
    }
}
