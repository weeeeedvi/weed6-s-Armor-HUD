package com.weedvi.armorhud.render;

import com.weedvi.armorhud.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import org.joml.Matrix3x2fStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ArmorHudRenderer {

    private static final int ITEM_SIZE = 16;
    private static final int GAP = 4;

    public static void render(DrawContext drawContext, RenderTickCounter tickCounter) {
        ModConfig config = ModConfig.get();
        if (!config.enabled) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        TextRenderer textRenderer = client.textRenderer;
        if (textRenderer == null) {
            return;
        }

        renderArmorPiece(drawContext, textRenderer,
                player.getEquippedStack(EquipmentSlot.HEAD),
                config.helmetX, config.helmetY, config.helmetScale,
                config.showEmptySlots, config.textOnLeft);

        renderArmorPiece(drawContext, textRenderer,
                player.getEquippedStack(EquipmentSlot.CHEST),
                config.chestX, config.chestY, config.chestScale,
                config.showEmptySlots, config.textOnLeft);

        renderArmorPiece(drawContext, textRenderer,
                player.getEquippedStack(EquipmentSlot.LEGS),
                config.legsX, config.legsY, config.legsScale,
                config.showEmptySlots, config.textOnLeft);

        renderArmorPiece(drawContext, textRenderer,
                player.getEquippedStack(EquipmentSlot.FEET),
                config.bootsX, config.bootsY, config.bootsScale,
                config.showEmptySlots, config.textOnLeft);
    }

    private static void renderArmorPiece(
            DrawContext drawContext,
            TextRenderer textRenderer,
            ItemStack stack,
            int posX,
            int posY,
            float scale,
            boolean showEmpty,
            boolean textOnLeft
    ) {
        if (stack.isEmpty() && !showEmpty) {
            return;
        }

        String durabilityText;
        if (stack.isEmpty()) {
            durabilityText = "-";
        } else if (stack.isDamageable()) {
            durabilityText = Integer.toString(stack.getMaxDamage() - stack.getDamage());
        } else {
            durabilityText = "-";
        }

        int textWidth = textRenderer.getWidth(durabilityText);
        int textHeight = textRenderer.fontHeight;

        int localTextX = textOnLeft ? -(textWidth + GAP) : (ITEM_SIZE + GAP);
        int localTextY = (int) Math.round((ITEM_SIZE - textHeight) / 2.0);

        Matrix3x2fStack matrices = drawContext.getMatrices();
        matrices.pushMatrix();
        matrices.translate(posX, posY);
        matrices.scale(scale, scale);

        if (!stack.isEmpty()) {
            drawContext.drawItem(stack, 0, 0);
        } else {
            drawContext.fill(0, 0, ITEM_SIZE, ITEM_SIZE, 0x44000000);
        }

        drawContext.drawText(textRenderer, durabilityText, localTextX, localTextY, 0xFFFFFFFF, true);

        matrices.popMatrix();
    }
}
