package net.pitan76.solomonsrod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.CompatibleMaterial;
import net.pitan76.mcpitanlib.api.block.ExtendBlock;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.event.block.*;
import net.pitan76.mcpitanlib.api.util.VoxelShapeUtil;
import net.pitan76.mcpitanlib.api.util.WorldUtil;
import net.pitan76.mcpitanlib.api.util.math.PosUtil;
import net.pitan76.mcpitanlib.core.serialization.CompatMapCodec;

public class SolomonsBlock extends ExtendBlock {

    protected static final VoxelShape SHAPE = VoxelShapeUtil.blockCuboid(0.1D, 0.1D, 0.1D, 15.5D, 16.0D, 15.5D);
    public static final BooleanProperty BROKEN = BooleanProperty.of("broken");
    public static final BooleanProperty COOL_DOWN = BooleanProperty.of("cooldown");

    public static SolomonsBlock SOLOMONS_BLOCK = new SolomonsBlock(CompatibleBlockSettings
            .of(CompatibleMaterial.METAL)
            .strength(-1F, 0F)
            .dropsNothing()
    );

    public SolomonsBlock(CompatibleBlockSettings settings) {
        super(settings);
        setNewDefaultState(getNewDefaultState().with(BROKEN, false).with(COOL_DOWN, false));
    }

    @Override
    public void appendProperties(AppendPropertiesArgs args) {
        super.appendProperties(args);
        args.addProperty(BROKEN);
        args.addProperty(COOL_DOWN);
    }

    @Override
    public VoxelShape getOutlineShape(OutlineShapeEvent e) {
        return VoxelShapeUtil.fullCube();
    }

    @Override
    public VoxelShape getCollisionShape(CollisionShapeEvent e) {
        return SHAPE;
    }

    @Override
    public void scheduledTick(BlockScheduledTickEvent e) {
        WorldUtil.setBlockState(e.world, e.pos, e.state.with(COOL_DOWN, false));
    }

    @Override
    public void onEntityCollision(EntityCollisionEvent e) {
        if (e.isClient()) return;

        World world = e.getWorld();
        BlockPos pos = e.getBlockPos();
        BlockState state = e.getState();

        //System.out.println("pos: " + pos + "entityPos: " + entity.getBlockPos());
        if (e.getEntityPos().equals(pos)) {
            WorldUtil.playSound(e.getWorld(), null, e.getEntityPos(), Sounds.NOCRASH_SOUND.getOrNull(), SoundCategory.MASTER, 1f, 1f);
            WorldUtil.removeBlock(e.getWorld(), pos, false);
            return;
        }

        if (e.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) e.getEntity();
            if (PosUtil.flooredBlockPos(player.getCameraPosVec(1F)).getY() >= pos.getY()) return;
        }

        if (!state.get(COOL_DOWN)) {
            if (state.get(BROKEN)) {
                WorldUtil.removeBlock(world, pos, false);
            } else {
                //world.getBlockTickScheduler().schedule(pos, SOLOMONS_BLOCK, 5);
                WorldUtil.scheduleBlockTick(world, pos, SOLOMONS_BLOCK, 5);
                //world.createAndScheduleBlockTick(pos, SOLOMONS_BLOCK, 5);
                WorldUtil.setBlockState(world, pos, state.with(BROKEN, true).with(COOL_DOWN, true));
            }
            WorldUtil.playSound(world, null, pos, Sounds.CRASH_SOUND.getOrNull(), SoundCategory.MASTER, 1f, 1f);
        }
    }

    @Override
    public void onBlockBreakStart(BlockBreakStartEvent e) {
        if (e.isClient()) {
            super.onBlockBreakStart(e);
            return;
        }

        Player player = e.player;
        if (player.getMainHandStack() == null) {
            super.onBlockBreakStart(e);
            return;
        }

        if (player.getMainHandStack().getItem() instanceof SolomonsWand || player.getMainHandStack().getItem() instanceof DemonsWand) {
            SolomonsWand wand = (SolomonsWand) player.getMainHandStack().getItem();
            wand.deleteBlock(e.getWorld(), player, e.getPos());
        }

        super.onBlockBreakStart(e);
    }

    @Override
    public CompatMapCodec<? extends Block> getCompatCodec() {
        return CompatMapCodec.createCodecOfExtendBlock(SolomonsBlock::new);
    }
}