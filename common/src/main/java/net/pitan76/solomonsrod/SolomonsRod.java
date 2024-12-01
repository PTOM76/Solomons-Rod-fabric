package net.pitan76.solomonsrod;

import net.pitan76.mcpitanlib.api.CommonModInitializer;
import net.pitan76.mcpitanlib.api.block.v2.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.CompatibleMaterial;
import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;
import net.pitan76.mcpitanlib.api.util.block.BlockUtil;
import net.pitan76.mcpitanlib.api.util.CompatIdentifier;
import net.pitan76.mcpitanlib.api.util.item.ItemUtil;
import net.minecraft.block.Block;
import net.pitan76.mcpitanlib.midohra.item.ItemGroups;

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

        SOLOMONS_BLOCK = registry.registerBlock(_id("solomon_block"), () -> SolomonsBlock.SOLOMONS_BLOCK);
        SOLOMONS_BLOCK_2 = registry.registerBlock(_id("solomon_block2"), () -> BlockUtil.create(CompatibleBlockSettings.of(_id("solomon_block2"), CompatibleMaterial.METAL).strength(3f, 3f).requiresTool()));
        SOLOMONS_BLOCK_3 = registry.registerBlock(_id("solomon_block3"), () -> BlockUtil.create(CompatibleBlockSettings.of(_id("solomon_block3"), CompatibleMaterial.METAL).strength(3f, 3f).requiresTool()));

        registry.registerItem(_id("solomon_block"), () -> ItemUtil.create(SOLOMONS_BLOCK.get(), CompatibleItemSettings.of(_id("solomon_block")).addGroup(ItemGroups.MISC)));
        registry.registerItem(_id("solomon_block2"), () -> ItemUtil.create(SOLOMONS_BLOCK_2.get(), CompatibleItemSettings.of(_id("solomon_block2")).addGroup(ItemGroups.BUILDING_BLOCKS)));
        registry.registerItem(_id("solomon_block3"), () -> ItemUtil.create(SOLOMONS_BLOCK_3.get(), CompatibleItemSettings.of(_id("solomon_block3")).addGroup(ItemGroups.BUILDING_BLOCKS)));

        registry.registerItem(_id("solomon_wand"), () -> SolomonsWand.SOLOMONS_WAND);
        registry.registerItem(_id("demons_wand"), () -> DemonsWand.DEMONS_WAND);
    }
    
    public static CompatIdentifier _id(String path) {
        return new CompatIdentifier(MOD_ID, path);
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
