package net.pitan76.solomonsrod;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.item.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.DefaultItemGroups;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.pitan76.mcpitanlib.api.sound.CompatSoundCategory;
import net.pitan76.mcpitanlib.api.util.EntityUtil;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;
import net.pitan76.mcpitanlib.api.util.WorldUtil;

import java.util.Optional;

public class DemonsWand extends SolomonsWand {
    public static DemonsWand DEMONS_WAND = of();

    public DemonsWand(CompatibleItemSettings settings) {
        super(settings);

        EntityEvent.LIVING_HURT.register((entity, damageSource, f) -> {
            Entity attacker = damageSource.getAttacker();
            if (!(attacker instanceof PlayerEntity)) return EventResult.pass();

            Player player = new Player((PlayerEntity) attacker);
            Optional<ItemStack> stackOptional = player.getCurrentHandItem();
            if (!stackOptional.isPresent()) return EventResult.pass();
            ItemStack stack = stackOptional.get();
            
            if (!(stack.getItem() instanceof DemonsWand)) return EventResult.pass();

            if (!Config.infiniteDurability && ItemStackUtil.getDamage(stack) >= ItemStackUtil.getMaxDamage(stack))
                return EventResult.pass();

            if (entity instanceof AnimalEntity || entity instanceof SlimeEntity || entity instanceof VillagerEntity || entity instanceof WaterCreatureEntity) {
                WorldUtil.playSound(player.getWorld(), null, player.getBlockPos(), Sounds.BAM_SOUND, CompatSoundCategory.MASTER, 1f, 1f);
                EntityUtil.kill(entity);

                SolomonsWand.damageStackIfDamageable(stack, player, player.getMainHandStack().getItem() == stack.getItem() ? Hand.MAIN_HAND : Hand.OFF_HAND);

                return EventResult.interruptTrue();
            }

            return EventResult.pass();
        });
    }

    public static DemonsWand of() {
        CompatibleItemSettings settings = CompatibleItemSettings.of().addGroup(DefaultItemGroups.TOOLS, SolomonsRod.INSTANCE.id("demons_wand"));
        if (!Config.infiniteDurability) settings.maxDamage(Config.maxDamage);
        else settings.maxCount(1);

        return new DemonsWand(settings);
    }
}
