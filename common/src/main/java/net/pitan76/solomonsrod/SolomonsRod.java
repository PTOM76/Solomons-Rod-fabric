package net.pitan76.solomonsrod;

import net.pitan76.mcpitanlib.api.block.CompatibleBlockSettings;
import net.pitan76.mcpitanlib.api.block.CompatibleMaterial;
import net.pitan76.mcpitanlib.api.item.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.DefaultItemGroups;
import net.pitan76.mcpitanlib.api.registry.CompatRegistry;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;
import net.pitan76.mcpitanlib.api.util.BlockUtil;
import net.pitan76.mcpitanlib.api.util.ItemUtil;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
public class SolomonsRod {
    public static final String MOD_ID = "solomons_rod";

    public static CompatRegistry registry = CompatRegistry.createRegistry(MOD_ID);

    public static RegistryResult<Block> SOLOMONS_BLOCK;
    public static RegistryResult<Block> SOLOMONS_BLOCK_2;
    public static RegistryResult<Block> SOLOMONS_BLOCK_3;

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    public static void init() {
        Sounds.init();

        SOLOMONS_BLOCK = registry.registerBlock(id("solomon_block"), () -> SolomonsBlock.SOLOMONS_BLOCK);
        SOLOMONS_BLOCK_2 = registry.registerBlock(id("solomon_block2"), () -> BlockUtil.of(CompatibleBlockSettings.of(CompatibleMaterial.METAL).strength(3f, 3f).requiresTool()));
        SOLOMONS_BLOCK_3 = registry.registerBlock(id("solomon_block3"), () -> BlockUtil.of(CompatibleBlockSettings.of(CompatibleMaterial.METAL).strength(3f, 3f).requiresTool()));

        registry.registerItem(id("solomon_block"), () -> ItemUtil.ofBlock(SOLOMONS_BLOCK.get(), new CompatibleItemSettings().addGroup(DefaultItemGroups.MISC, id("solomon_block"))));
        registry.registerItem(id("solomon_block2"), () -> ItemUtil.ofBlock(SOLOMONS_BLOCK_2.get(), new CompatibleItemSettings().addGroup(DefaultItemGroups.BUILDING_BLOCKS, id("solomon_block2"))));
        registry.registerItem(id("solomon_block3"), () -> ItemUtil.ofBlock(SOLOMONS_BLOCK_3.get(), new CompatibleItemSettings().addGroup(DefaultItemGroups.BUILDING_BLOCKS, id("solomon_block3"))));

        registry.registerItem(id("solomon_wand"), () -> SolomonsWand.SOLOMONS_WAND);
        registry.registerItem(id("demons_wand"), () -> DemonsWand.DEMONS_WAND);

        registry.allRegister();
        //System.out.println(SolomonsRodExpectPlatform.getConfigDirectory().toAbsolutePath().normalize().toString());
    }
}
