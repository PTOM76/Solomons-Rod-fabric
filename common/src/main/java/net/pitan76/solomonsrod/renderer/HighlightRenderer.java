package net.pitan76.solomonsrod.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.pitan76.mcpitanlib.api.client.event.listener.BeforeBlockOutlineEvent;
import net.pitan76.mcpitanlib.api.client.event.listener.BeforeBlockOutlineListener;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.util.VoxelShapeUtil;
import net.pitan76.solomonsrod.SolomonsWand;

public class HighlightRenderer implements BeforeBlockOutlineListener {

    @Override
    public boolean beforeBlockOutline(BeforeBlockOutlineEvent e) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return true;

        if (e.isBlockType()) return true;

        ItemStack stack = player.getMainHandStack();
        if (stack == null || !(stack.getItem() instanceof SolomonsWand)) return true;

        Camera camera = e.getCamera();

        BlockPos blockPos = SolomonsWand.getPlacingPos(new Player(player));

        double x = blockPos.getX() - camera.getPos().x;
        double y = blockPos.getY() - camera.getPos().y;
        double z = blockPos.getZ() - camera.getPos().z;

        e.push();
        e.translate(x, y, z);

        e.drawBox(VoxelShapeUtil.getBoundingBox(VoxelShapeUtil.fullCube()), 1f, 1f, 1f, 1f);

        e.pop();
        return true;
    }
}
