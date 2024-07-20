package net.pitan76.solomonsrod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.CompatibleMaterial;
import net.pitan76.mcpitanlib.api.block.ExtendBlock;
import net.pitan76.mcpitanlib.api.event.block.AppendPropertiesArgs;
import net.pitan76.mcpitanlib.api.event.block.BlockScheduledTickEvent;
import net.pitan76.mcpitanlib.api.event.block.CollisionShapeEvent;
import net.pitan76.mcpitanlib.api.event.block.OutlineShapeEvent;
import net.pitan76.mcpitanlib.api.util.WorldUtil;
import net.pitan76.mcpitanlib.api.util.math.PosUtil;

public class SolomonsBlock extends ExtendBlock {

    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.1D, 0.1D, 0.1D, 15.5D, 16.0D, 15.5D);
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
    public VoxelShape getOutlineShape(OutlineShapeEvent event) {
        return VoxelShapes.fullCube();
    }

    @Override
    public VoxelShape getCollisionShape(CollisionShapeEvent event) {
        return SHAPE;
    }

    @Override
    public void scheduledTick(BlockScheduledTickEvent e) {
        e.world.setBlockState(e.pos, e.state.with(COOL_DOWN, false));
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient()) {
            //System.out.println("pos: " + pos + "entityPos: " + entity.getBlockPos());
            if (entity.getBlockPos().equals(pos)) {
                world.playSound(null, entity.getBlockPos(), Sounds.NOCRASH_SOUND.getOrNull(), SoundCategory.MASTER, 1f, 1f);
                world.removeBlock(pos, false);
                return;
            }
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                if (PosUtil.flooredBlockPos(player.getCameraPosVec(1F)).getY() >= pos.getY()) return;
            }
            if (!state.get(COOL_DOWN)) {
                if (state.get(BROKEN)) {
                    world.removeBlock(pos, false);
                } else {
                    //world.getBlockTickScheduler().schedule(pos, SOLOMONS_BLOCK, 5);
                    WorldUtil.scheduleBlockTick(world, pos, SOLOMONS_BLOCK, 5);
                    //world.createAndScheduleBlockTick(pos, SOLOMONS_BLOCK, 5);
                    world.setBlockState(pos, state.with(BROKEN, true).with(COOL_DOWN, true));
                }
                world.playSound(null, pos, Sounds.CRASH_SOUND.getOrNull(), SoundCategory.MASTER, 1f, 1f);
            }
        }
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (world.isClient()) return;
        if (player.getMainHandStack() != null)
            if (player.getMainHandStack().getItem() instanceof SolomonsWand || player.getMainHandStack().getItem() instanceof DemonsWand) {
                SolomonsWand wand = (SolomonsWand) player.getMainHandStack().getItem();
                wand.deleteBlock(world, player, pos);
            }
        super.onBlockBreakStart(state, world, pos, player);
    }
}