package net.pitan76.solomonsrod;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.item.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.DefaultItemGroups;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.pitan76.mcpitanlib.api.util.WorldUtil;

public class DemonsWand extends SolomonsWand {
    public static DemonsWand DEMONS_WAND = new DemonsWand(new CompatibleItemSettings().addGroup(() -> DefaultItemGroups.TOOLS, SolomonsRod.id("demons_wand")).maxCount(1));

    public DemonsWand(CompatibleItemSettings settings) {
        super(settings);
        EntityEvent.LIVING_HURT.register((entity, damageSource, f) -> {
            Entity attacker = damageSource.getAttacker();
            if (attacker instanceof PlayerEntity) {
                Player player = new Player((PlayerEntity) attacker);
                if (player.getMainHandStack().getItem() instanceof DemonsWand) {
                    if(entity instanceof AnimalEntity || entity instanceof SlimeEntity || entity instanceof VillagerEntity || entity instanceof WaterCreatureEntity) {
                        WorldUtil.playSound(player.getWorld(), null, player.getPlayerEntity().getBlockPos(), Sounds.BAM_SOUND.getOrNull(), SoundCategory.MASTER, 1f, 1f);
                        entity.kill();
                        return EventResult.interruptTrue();
                    }
                }
            }

            return EventResult.pass();
        });
    }
}
