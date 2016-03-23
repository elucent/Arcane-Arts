package elucent.aa;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;

public class vesselRenderer extends TileEntitySpecialRenderer {
	public RenderManager manager = Minecraft.getMinecraft().getRenderManager();
	public RenderItem itemRenderer;
	public vesselRenderer(){
	}
	
	public void addBlock(WorldRenderer wr, double x1, double y1, double z1, double x2, double y2, double z2, double tx1, double ty1, double tx2, double ty2){
		wr.pos(x1,y1,z1).tex(tx1, ty1).normal(0, 0, -1.0f).endVertex();
		wr.pos(x2,y1,z1).tex(tx2, ty1).normal(0, 0, -1.0f).endVertex();
		wr.pos(x2,y2,z1).tex(tx2, ty2).normal(0, 0, -1.0f).endVertex();
		wr.pos(x1,y2,z1).tex(tx1, ty2).normal(0, 0, -1.0f).endVertex();

		wr.pos(x1,y1,z1).tex(tx1, ty1).normal(-1.0f, 0, 0).endVertex();
		wr.pos(x1,y1,z2).tex(tx2, ty1).normal(-1.0f, 0, 0).endVertex();
		wr.pos(x1,y2,z2).tex(tx2, ty2).normal(-1.0f, 0, 0).endVertex();
		wr.pos(x1,y2,z1).tex(tx1, ty2).normal(-1.0f, 0, 0).endVertex();

		wr.pos(x1,y1,z1).tex(tx1, ty1).normal(0, -1.0f, 0).endVertex();
		wr.pos(x2,y1,z1).tex(tx2, ty1).normal(0, -1.0f, 0).endVertex();
		wr.pos(x2,y1,z2).tex(tx2, ty2).normal(0, -1.0f, 0).endVertex();
		wr.pos(x1,y1,z2).tex(tx1, ty2).normal(0, -1.0f, 0).endVertex();

		wr.pos(x1,y1,z2).tex(tx2, ty1).normal(0, 0, 1.0f).endVertex();
		wr.pos(x2,y1,z2).tex(tx1, ty1).normal(0, 0, 1.0f).endVertex();
		wr.pos(x2,y2,z2).tex(tx1, ty2).normal(0, 0, 1.0f).endVertex();
		wr.pos(x1,y2,z2).tex(tx2, ty2).normal(0, 0, 1.0f).endVertex();

		wr.pos(x2,y1,z1).tex(tx2, ty1).normal(1.0f, 0, 0).endVertex();
		wr.pos(x2,y1,z2).tex(tx1, ty1).normal(1.0f, 0, 0).endVertex();
		wr.pos(x2,y2,z2).tex(tx1, ty2).normal(1.0f, 0, 0).endVertex();
		wr.pos(x2,y2,z1).tex(tx2, ty2).normal(1.0f, 0, 0).endVertex();

		wr.pos(x1,y2,z1).tex(tx2, ty1).normal(0, 1.0f, 0).endVertex();
		wr.pos(x2,y2,z1).tex(tx1, ty1).normal(0, 1.0f, 0).endVertex();
		wr.pos(x2,y2,z2).tex(tx1, ty2).normal(0, 1.0f, 0).endVertex();
		wr.pos(x1,y2,z2).tex(tx2, ty2).normal(0, 1.0f, 0).endVertex();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
		ResourceLocation tex = new ResourceLocation("arcanearts:textures/blocks/vesselSide.png");
		if (te instanceof vesselEntity){
			GL11.glPushMatrix();
			//GL11.glEnable(GL11.GL_LIGHTING);
			this.bindTexture(tex);
			GL11.glTranslated(x, y, z);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glScaled(1.0/16.0, 1.0/16.0, 1.0/16.0);
			WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
			if (((vesselEntity)te).contents.element != null){
				if (((vesselEntity)te).contents.element == aaElementManager.elementFire){
					GL11.glColor4f(1.0f, 0.25f, 0.0f, 0.75f);
				}
				if (((vesselEntity)te).contents.element == aaElementManager.elementEarth){
					GL11.glColor4f(0.0f, 0.75f, 0.0f, 0.75f);
				}
				if (((vesselEntity)te).contents.element == aaElementManager.elementWater){
					GL11.glColor4f(0.125f, 0.0f, 0.75f, 0.75f);
				}
				if (((vesselEntity)te).contents.element == aaElementManager.elementAir){
					GL11.glColor4f(0.75f, 1.0f, 1.0f, 0.75f);
				}
				if (((vesselEntity)te).contents.element == aaElementManager.elementLight){
					GL11.glColor4f(1.0f, 1.0f, 0.75f, 0.75f);
				}
				if (((vesselEntity)te).contents.element == aaElementManager.elementVoid){
					GL11.glColor4f(0.125f, 0.0f, 0.125f, 0.75f);
				}
				double val = ((vesselEntity)te).contents.value;
				double capacity = ((vesselEntity)te).capacity;
				worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
				if (((vesselEntity)te).contents.value > 0){
					addBlock(worldRenderer, 5.0D,5.0D,5.0D,11.0D,5.0D+6.0D*(val/capacity),11.0D,0.3125D,0.3125D,0.6875,0.3125+0.375*(val/capacity));
				}
				Tessellator.getInstance().draw();
			}
			worldRenderer.reset();
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
			worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
			addBlock(worldRenderer, 4.0D,4.0D,4.0D,12.0D,12.0D,12.0D,0.25D,0.25D,0.75,0.75);
			Tessellator.getInstance().draw();
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}
}
