package mekanism.generators.common.block.turbine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import mekanism.common.block.basic.BlockBasicMultiblock;
import mekanism.common.block.interfaces.IHasGui;
import mekanism.common.block.interfaces.IHasTileEntity;
import mekanism.common.inventory.container.ContainerProvider;
import mekanism.common.util.SecurityUtils;
import mekanism.generators.common.MekanismGenerators;
import mekanism.generators.common.inventory.container.turbine.TurbineContainer;
import mekanism.generators.common.tile.turbine.TileEntityTurbineCasing;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockTurbineCasing extends BlockBasicMultiblock implements IHasTileEntity<TileEntityTurbineCasing>, IHasGui<TileEntityTurbineCasing> {

    public BlockTurbineCasing() {
        super(MekanismGenerators.MODID, "turbine_casing", Block.Properties.create(Material.IRON).hardnessAndResistance(3.5F, 8F));
    }

    @Override
    @Deprecated
    public float getPlayerRelativeBlockHardness(BlockState state, @Nonnull PlayerEntity player, @Nonnull IBlockReader world, @Nonnull BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        return SecurityUtils.canAccess(player, tile) ? super.getPlayerRelativeBlockHardness(state, player, world, pos) : 0.0F;
    }

    @Override
    public TileEntity createTileEntity(@Nonnull BlockState state, @Nonnull IBlockReader world) {
        return new TileEntityTurbineCasing();
    }

    @Nullable
    @Override
    public Class<? extends TileEntityTurbineCasing> getTileClass() {
        return TileEntityTurbineCasing.class;
    }

    @Override
    public INamedContainerProvider getProvider(TileEntityTurbineCasing tile) {
        return new ContainerProvider("mekanismgenerators.container.industrial_turbine", (i, inv, player) -> new TurbineContainer(i, inv, tile));
    }
}