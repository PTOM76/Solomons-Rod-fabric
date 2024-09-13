package net.pitan76.solomonsrod;

import net.pitan76.mcpitanlib.api.CommonModInitializer;
import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.CompatibleMaterial;
import net.pitan76.mcpitanlib.api.item.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.DefaultItemGroups;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;
import net.pitan76.mcpitanlib.api.util.BlockUtil;
import net.pitan76.mcpitanlib.api.util.ItemUtil;
import net.minecraft.block.Block;

public class SolomonsRod extends CommonModInitializer {
    public static final String MOD_ID = "solomons_rod";
    public static final String MOD_NAME = "Solomons Rod";

    public static SolomonsRod INSTANCE;

    public static RegistryResult<Block> SOLOMONS_BLOCK;
    public static RegistryResult<Block> SOLOMONS_BLOCK_2;
    public static RegistryResult<Block> SOLOMONS_BLOCK_3;

    public void init() {
        INSTANCE = this;

        Config.init();
        Sounds.init();

        SOLOMONS_BLOCK = registry.registerBlock(compatId("solomon_block"), () -> SolomonsBlock.SOLOMONS_BLOCK);
        SOLOMONS_BLOCK_2 = registry.registerBlock(compatId("solomon_block2"), () -> BlockUtil.of(CompatibleBlockSettings.of(CompatibleMaterial.METAL).strength(3f, 3f).requiresTool()));
        SOLOMONS_BLOCK_3 = registry.registerBlock(compatId("solomon_block3"), () -> BlockUtil.of(CompatibleBlockSettings.of(CompatibleMaterial.METAL).strength(3f, 3f).requiresTool()));

        registry.registerItem(compatId("solomon_block"), () -> ItemUtil.ofBlock(SOLOMONS_BLOCK.get(), CompatibleItemSettings.of().addGroup(DefaultItemGroups.MISC, id("solomon_block"))));
        registry.registerItem(compatId("solomon_block2"), () -> ItemUtil.ofBlock(SOLOMONS_BLOCK_2.get(), CompatibleItemSettings.of().addGroup(DefaultItemGroups.BUILDING_BLOCKS, id("solomon_block2"))));
        registry.registerItem(compatId("solomon_block3"), () -> ItemUtil.ofBlock(SOLOMONS_BLOCK_3.get(), CompatibleItemSettings.of().addGroup(DefaultItemGroups.BUILDING_BLOCKS, id("solomon_block3"))));

        registry.registerItem(compatId("solomon_wand"), () -> SolomonsWand.SOLOMONS_WAND);
        registry.registerItem(compatId("demons_wand"), () -> DemonsWand.DEMONS_WAND);
    }

    @Override
    public String getId() {
        return MOD_ID;
    }

    @Override
    public String getName() {
        return MOD_NAME;
    }
}
