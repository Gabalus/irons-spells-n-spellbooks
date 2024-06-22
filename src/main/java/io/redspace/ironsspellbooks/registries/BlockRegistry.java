package io.redspace.ironsspellbooks.registries;


import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.block.ArmorPileBlock;
import io.redspace.ironsspellbooks.block.BloodCauldronBlock;
import io.redspace.ironsspellbooks.block.FireflyJar;
import io.redspace.ironsspellbooks.block.alchemist_cauldron.AlchemistCauldronBlock;
import io.redspace.ironsspellbooks.block.alchemist_cauldron.AlchemistCauldronTile;
import io.redspace.ironsspellbooks.block.arcane_anvil.ArcaneAnvilBlock;
import io.redspace.ironsspellbooks.block.inscription_table.InscriptionTableBlock;
import io.redspace.ironsspellbooks.block.pedestal.PedestalBlock;
import io.redspace.ironsspellbooks.block.pedestal.PedestalTile;
import io.redspace.ironsspellbooks.block.scroll_forge.ScrollForgeBlock;
import io.redspace.ironsspellbooks.block.scroll_forge.ScrollForgeTile;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;


public class BlockRegistry {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, IronsSpellbooks.MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, IronsSpellbooks.MODID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
    }

    public static final Supplier<Block> INSCRIPTION_TABLE_BLOCK = BLOCKS.register("inscription_table", InscriptionTableBlock::new);
    public static final Supplier<Block> SCROLL_FORGE_BLOCK = BLOCKS.register("scroll_forge", ScrollForgeBlock::new);
    //public static final Supplier<Block> BLOOD_SLASH_BLOCK = BLOCKS.register("blood_slash", BloodSlashBlock::new);
    public static final Supplier<Block> PEDESTAL_BLOCK = BLOCKS.register("pedestal", PedestalBlock::new);
    public static final Supplier<Block> BLOOD_CAULDRON_BLOCK = BLOCKS.register("blood_cauldron", BloodCauldronBlock::new);
    public static final Supplier<Block> ARCANE_ANVIL_BLOCK = BLOCKS.register("arcane_anvil", ArcaneAnvilBlock::new);
    public static final Supplier<Block> ARCANE_DEBRIS = BLOCKS.register("arcane_debris", () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE).requiresCorrectToolForDrops().strength(20.0F, 1200.0F).sound(SoundType.ANCIENT_DEBRIS)));
    public static final Supplier<Block> ARMOR_PILE_BLOCK = BLOCKS.register("armor_pile", ArmorPileBlock::new);
    public static final Supplier<Block> ALCHEMIST_CAULDRON = BLOCKS.register("alchemist_cauldron", AlchemistCauldronBlock::new);
    public static final Supplier<Block> FIREFLY_JAR = BLOCKS.register("firefly_jar", FireflyJar::new);


    //public static final Supplier<BlockEntityType<InscriptionTableTile>> INSCRIPTION_TABLE_TILE = BLOCK_ENTITIES.register("inscription_table", () -> BlockEntityType.Builder.of(InscriptionTableTile::new, INSCRIPTION_TABLE_BLOCK.get()).build(null));
    public static final Supplier<BlockEntityType<ScrollForgeTile>> SCROLL_FORGE_TILE = BLOCK_ENTITIES.register("scroll_forge", () -> BlockEntityType.Builder.of(ScrollForgeTile::new, SCROLL_FORGE_BLOCK.get()).build(null));
    public static final Supplier<BlockEntityType<PedestalTile>> PEDESTAL_TILE = BLOCK_ENTITIES.register("pedestal", () -> BlockEntityType.Builder.of(PedestalTile::new, PEDESTAL_BLOCK.get()).build(null));
    public static final Supplier<BlockEntityType<AlchemistCauldronTile>> ALCHEMIST_CAULDRON_TILE = BLOCK_ENTITIES.register("alchemist_cauldron", () -> BlockEntityType.Builder.of(AlchemistCauldronTile::new, ALCHEMIST_CAULDRON.get()).build(null));
}
