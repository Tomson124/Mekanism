package mekanism.tools.item;

import java.util.List;

import mekanism.api.util.StackUtils;
import mekanism.common.item.ItemMekanism;
import mekanism.common.util.LangUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMekanismHoe extends ItemMekanism
{
	protected ToolMaterial toolMaterial;

	public ItemMekanismHoe(ToolMaterial enumtoolmaterial)
	{
		super();
		toolMaterial = enumtoolmaterial;
		maxStackSize = 1;
		setMaxDamage(enumtoolmaterial.getMaxUses());
		setCreativeTab(CreativeTabs.tabTools);
	}
	
	@Override
    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2)
    {
        return StackUtils.equalsWildcard(ItemMekanismTool.getRepairStack(toolMaterial), stack2) ? true : super.getIsRepairable(stack1, stack2);
    }

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos pos, EnumFacing side, float entityX, float entityY, float entityZ)
	{
		if(!entityplayer.canPlayerEdit(pos, side, itemstack))
		{
			return false;
		}
		else {
			UseHoeEvent event = new UseHoeEvent(entityplayer, itemstack, world, pos);

			if(MinecraftForge.EVENT_BUS.post(event))
			{
				return false;
			}

			if(event.getResult() == Result.ALLOW)
			{
				itemstack.damageItem(1, entityplayer);
				return true;
			}

			Block blockID = world.getBlockState(pos).getBlock();
			Block aboveBlock = world.getBlockState(pos.add(0, 1, 0)).getBlock();

			if((side == EnumFacing.DOWN || !aboveBlock.isAir(world, pos.add(0, 1, 0)) || blockID != Blocks.grass) && blockID != Blocks.dirt)
			{
				return false;
			}
			else {
				IBlockState block = Blocks.farmland.getDefaultState();
				world.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, block.getBlock().stepSound.getStepSound(), (block.getBlock().stepSound.getVolume() + 1.0F) / 2.0F, block.getBlock().stepSound.getFrequency() * 0.8F);

				if(world.isRemote)
				{
					return true;
				}
				else {
					world.setBlockState(pos, block);
					itemstack.damageItem(1, entityplayer);
					return true;
				}
			}
		}
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer entityplayer, List list, boolean flag)
	{
		list.add(LangUtils.localize("tooltip.hp") + ": " + (itemstack.getMaxDamage() - itemstack.getItemDamage()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
}