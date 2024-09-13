package net.pitan76.solomonsrod;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.event.item.EnchantableArgs;
import net.pitan76.mcpitanlib.api.event.item.ItemUseEvent;
import net.pitan76.mcpitanlib.api.event.item.ItemUseOnBlockEvent;
import net.pitan76.mcpitanlib.api.item.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.DefaultItemGroups;
import net.pitan76.mcpitanlib.api.item.ExtendItem;
import net.pitan76.mcpitanlib.api.util.BlockStateUtil;
import net.pitan76.mcpitanlib.api.util.HandUtil;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;
import net.pitan76.mcpitanlib.api.util.WorldUtil;
import net.pitan76.mcpitanlib.api.util.math.PosUtil;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Optional;

public class SolomonsWand extends ExtendItem {
    public static SolomonsWand SOLOMONS_WAND = of();

    public SolomonsWand(CompatibleItemSettings settings) {
        super(settings);
    }

    public static SolomonsWand of() {
        CompatibleItemSettings settings = CompatibleItemSettings.of().addGroup(DefaultItemGroups.TOOLS, SolomonsRod.INSTANCE.id("solomon_wand"));
        if (!Config.infiniteDurability) settings.maxDamage(Config.maxDamage);
        else settings.maxCount(1);

        return new SolomonsWand(settings);
    }

    public void deleteBlock(World world, Player user, BlockPos pos) {
        WorldUtil.removeBlock(world, pos, false);
        WorldUtil.playSound(world, null, user.getBlockPos(), Sounds.ERASE_SOUND.getOrNull(), SoundCategory.MASTER, 1f, 1f);
    }

    @Override
    public ActionResult onRightClickOnBlock(ItemUseOnBlockEvent e) {
        World world = e.world;
        BlockPos blockPos = PosUtil.flooredBlockPos(e.getPos());

        // 耐久値が0の場合はそのまま終了
        if (!Config.infiniteDurability && ItemStackUtil.getDamage(e.stack) >= ItemStackUtil.getMaxDamage(e.stack))
            return super.onRightClickOnBlock(e);

        if (e.isClient()) {
            if (WorldUtil.canSetBlock(world, blockPos) && canPlace(WorldUtil.getBlockState(world, blockPos).getBlock()))
                return ActionResult.SUCCESS;

            return super.onRightClickOnBlock(e);
        }

        // ブロックを設置できない場合はそのまま終了
        if (!WorldUtil.canSetBlock(world, blockPos) || !canPlace(WorldUtil.getBlockState(world, blockPos).getBlock()))
            return super.onRightClickOnBlock(e);

        // ブロックエンティティが存在する場合はそのまま音を鳴らして終了
        if (WorldUtil.getBlockEntity(world, blockPos) != null) {
            WorldUtil.playSound(world, null, e.player.getBlockPos(), Sounds.NOCRASH_SOUND.getOrNull(), SoundCategory.MASTER, 1f, 1f);
            return ActionResult.SUCCESS;
        }

        WorldUtil.setBlockState(world, blockPos, BlockStateUtil.getDefaultState(SolomonsBlock.SOLOMONS_BLOCK));
        WorldUtil.playSound(world, null, blockPos, Sounds.CREATE_SOUND.getOrNull(), SoundCategory.MASTER, 1f, 1f);

        damageStackIfDamageable(e.player.getStackInHand(e.hand), e.player, e.hand);

        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> onRightClick(ItemUseEvent e) {

        // 耐久値が0の場合はそのまま終了
        if (!Config.infiniteDurability && ItemStackUtil.getDamage(e.stack) >= ItemStackUtil.getMaxDamage(e.stack))
            return super.onRightClick(e);

        if (e.isClient()) super.onRightClick(e);

        World world = e.world;
        Player user = e.user;
        BlockPos blockPos = getPlacingPos(user);

        if (WorldUtil.canSetBlock(world, blockPos) && canPlace(WorldUtil.getBlockState(world, blockPos).getBlock()) && WorldUtil.getBlockEntity(world, blockPos) == null) {
            WorldUtil.setBlockState(world, blockPos, BlockStateUtil.getDefaultState(SolomonsBlock.SOLOMONS_BLOCK));
            WorldUtil.playSound(world, null, user.getBlockPos(), Sounds.CREATE_SOUND.getOrNull(), SoundCategory.MASTER, 1f, 1f);

            damageStackIfDamageable(user.getStackInHand(e.hand), user, e.hand);

            return TypedActionResult.success(user.getStackInHand(e.hand));
        }
        return super.onRightClick(e);
    }

    public static void damageStackIfDamageable(ItemStack stack, Player player, Hand hand) {
        if (!Config.infiniteDurability) {
            Optional<ServerPlayerEntity> optionalServerPlayer = player.getServerPlayer();
            if (!optionalServerPlayer.isPresent()) return;
            ServerPlayerEntity serverPlayer = optionalServerPlayer.get();

            ItemStackUtil.damage(stack, 1, serverPlayer, HandUtil.getEquipmentSlot(hand));
        }
    }

    public static BlockPos getPlacingPos(Player user) {
        Vec3d pos = user.getPos();

        double posX = pos.getX();
        double posY = pos.getY();
        double posZ = pos.getZ();

        boolean notChange = false;
        if (user.getPitch() <= -25) {
            posY += 2;
            if (user.getPitch() <= -60 && user.getPitch() >= -90) {
                notChange = true;
            }
        }

        if (user.getPitch() <= 25 && user.getPitch() >= -25) {
            posY += 1;
        }

        if (user.getPitch() >= 50) {
            posY -= 1;
            if (user.getPitch() <= 90 && user.getPitch() >= 75) {
                notChange = true;
            }
        }

        if (!notChange) {
            if (user.getHorizontalFacing() == Direction.EAST)
                posX += 1;
            if (user.getHorizontalFacing() == Direction.WEST)
                posX -= 1;
            if (user.getHorizontalFacing() == Direction.NORTH)
                posZ -= 1;
            if (user.getHorizontalFacing() == Direction.SOUTH)
                posZ += 1;
        }

        return PosUtil.flooredBlockPos(posX, posY, posZ);
    }

    public static boolean canPlace(Block block) {
        if (block == null) return true;
        if (block instanceof AirBlock) return true;
        if (block instanceof FluidBlock) return true;
        if (block instanceof FernBlock) return true;
        if (block instanceof DeadBushBlock) return true;
        return false;
    }

    @Override
    public boolean isEnchantable(EnchantableArgs args) {
        return true;
    }
}