package com.deflatedpickle.heatfromfire.mixin;

import com.deflatedpickle.heatfromfire.HeatFromFire;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@SuppressWarnings({"unused", "UnusedMixin", "deprecation"})
@Mixin(Block.class)
public abstract class MixinAbstractBlock extends AbstractBlock {
    public MixinAbstractBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
        HeatFromFire.INSTANCE.neighborUpdate(world, block, pos);
    }
}
