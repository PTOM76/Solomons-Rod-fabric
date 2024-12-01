package net.pitan76.solomonsrod;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.event.result.EventResult;
import net.pitan76.mcpitanlib.api.event.v1.LivingHurtEventRegistry;
import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.pitan76.mcpitanlib.api.sound.CompatSoundCategory;
import net.pitan76.mcpitanlib.api.util.CompatIdentifier;
import net.pitan76.mcpitanlib.api.util.EntityUtil;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;
import net.pitan76.mcpitanlib.api.util.WorldUtil;
import net.pitan76.mcpitanlib.midohra.item.ItemGroups;

import static net.pitan76.solomonsrod.SolomonsRod._id;

public class DemonsWand extends SolomonsWand {
    public static DemonsWand DEMONS_WAND = of(_id("demons_wand"));

    public DemonsWand(CompatibleItemSettings settings) {
        super(settings);

        LivingHurtEventRegistry.register((e) -> {
            if (!e.isPlayerAttacker()) return EventResult.pass();

            Entity entity = e.getEntity();
            Player player = e.getPlayerAttacker();
            ItemStack stack = player.getMainHandStack();

            if (stack == null || !(stack.getItem() instanceof DemonsWand)) return EventResult.pass();
            if (!Config.infiniteDurability && ItemStackUtil.isBreak(stack))
                return EventResult.pass();

            if (entity instanceof AnimalEntity || entity instanceof SlimeEntity || entity instanceof VillagerEntity || entity instanceof WaterCreatureEntity) {
                WorldUtil.playSound(player.getWorld(), null, player.getBlockPos(), Sounds.BAM_SOUND, CompatSoundCategory.MASTER, 1f, 1f);
                EntityUtil.kill(entity);

                SolomonsWand.damageStackIfDamageable(stack, player, Hand.MAIN_HAND);

                return EventResult.success();
            }

            return EventResult.pass();
        });
    }

    public static DemonsWand of(CompatIdentifier id) {
        CompatibleItemSettings settings = CompatibleItemSettings.of(id).addGroup(ItemGroups.TOOLS).enchantable(15);
        if (!Config.infiniteDurability) settings.maxDamage(Config.maxDamage);
        else settings.maxCount(1);

        return new DemonsWand(settings);
    }
}
