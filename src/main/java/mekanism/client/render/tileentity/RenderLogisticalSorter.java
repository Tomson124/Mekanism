package mekanism.client.render.tileentity;

import mekanism.client.model.ModelLogisticalSorter;
import mekanism.common.tile.TileEntityLogisticalSorter;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderLogisticalSorter extends TileEntitySpecialRenderer<TileEntityLogisticalSorter>
{
	private ModelLogisticalSorter model = new ModelLogisticalSorter();

	@Override
	public void renderTileEntityAt(TileEntityLogisticalSorter tileEntity, double x, double y, double z, float partialTick, int destroyStage)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 1.5F);

		bindTexture(MekanismUtils.getResource(ResourceType.RENDER, "LogisticalSorter" + (tileEntity.isActive ? "On" : "") + ".png"));

		switch(tileEntity.facing.ordinal()) /*TODO: switch the enum*/
		{
			case 0:
			{
				GlStateManager.rotate(90F, -1.0F, 0.0F, 0.0F);
				GlStateManager.translate(0.0F, 1.0F, -1.0F);
				break;
			}
			case 1:
			{
				GlStateManager.rotate(90F, 1.0F, 0.0F, 0.0F);
				GlStateManager.translate(0.0F, 1.0F, 1.0F);
				break;
			}
			case 2: GlStateManager.rotate(0, 0.0F, 1.0F, 0.0F); break;
			case 3: GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F); break;
			case 4: GlStateManager.rotate(90, 0.0F, 1.0F, 0.0F); break;
			case 5: GlStateManager.rotate(270, 0.0F, 1.0F, 0.0F); break;
		}

		GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
		model.render(0.0625F, tileEntity.isActive);
		GlStateManager.popMatrix();
	}
}