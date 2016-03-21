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

public class infuserRenderer extends TileEntitySpecialRenderer {
	public RenderManager manager = Minecraft.getMinecraft().getRenderManager();
	public RenderItem itemRenderer;
	public infuserRenderer(){
	}
	
	public void addBlock(WorldRenderer wr, double x1, double y1, double z1, double x2, double y2, double z2, double tx1, double ty1, double tx2, double ty2){
		wr.pos(x1,y1,z1).tex(tx1, ty1).normal(0, 0, -1).endVertex();
		wr.pos(x2,y1,z1).tex(tx2, ty1).normal(0, 0, -1).endVertex();
		wr.pos(x2,y2,z1).tex(tx2, ty2).normal(0, 0, -1).endVertex();
		wr.pos(x1,y2,z1).tex(tx1, ty2).normal(0, 0, -1).endVertex();

		wr.pos(x1,y1,z1).tex(tx1, ty1).normal(-1, 0, 0).endVertex();
		wr.pos(x1,y1,z2).tex(tx2, ty1).normal(-1, 0, 0).endVertex();
		wr.pos(x1,y2,z2).tex(tx2, ty2).normal(-1, 0, 0).endVertex();
		wr.pos(x1,y2,z1).tex(tx1, ty2).normal(-1, 0, 0).endVertex();

		wr.pos(x1,y1,z1).tex(tx1, ty1).normal(0, -1, 0).endVertex();
		wr.pos(x2,y1,z1).tex(tx2, ty1).normal(0, -1, 0).endVertex();
		wr.pos(x2,y1,z2).tex(tx2, ty2).normal(0, -1, 0).endVertex();
		wr.pos(x1,y1,z2).tex(tx1, ty2).normal(0, -1, 0).endVertex();

		wr.pos(x1,y1,z2).tex(tx2, ty1).normal(0, 0, 1).endVertex();
		wr.pos(x2,y1,z2).tex(tx1, ty1).normal(0, 0, 1).endVertex();
		wr.pos(x2,y2,z2).tex(tx1, ty2).normal(0, 0, 1).endVertex();
		wr.pos(x1,y2,z2).tex(tx2, ty2).normal(0, 0, 1).endVertex();

		wr.pos(x2,y1,z1).tex(tx2, ty1).normal(1, 0, 0).endVertex();
		wr.pos(x2,y1,z2).tex(tx1, ty1).normal(1, 0, 0).endVertex();
		wr.pos(x2,y2,z2).tex(tx1, ty2).normal(1, 0, 0).endVertex();
		wr.pos(x2,y2,z1).tex(tx2, ty2).normal(1, 0, 0).endVertex();

		wr.pos(x1,y2,z1).tex(tx2, ty1).normal(0, 1, 0).endVertex();
		wr.pos(x2,y2,z1).tex(tx1, ty1).normal(0, 1, 0).endVertex();
		wr.pos(x2,y2,z2).tex(tx1, ty2).normal(0, 1, 0).endVertex();
		wr.pos(x1,y2,z2).tex(tx2, ty2).normal(0, 1, 0).endVertex();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
		ResourceLocation tex = new ResourceLocation("arcanearts:textures/blocks/mechanism.png");
		if (te instanceof fireInfuserEntity){
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			this.bindTexture(tex);
			GL11.glTranslated(x, y, z);
			GL11.glScaled(1.0/16.0, 1.0/16.0, 1.0/16.0);
			WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
			if (((fireInfuserEntity)te).filled){
				GL11.glColor4f(1.0f, 0.25f, 0.0f, 1.0f);
				worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
				addBlock(worldRenderer, 6.0D,11.0D,6.0D,10.0D,15.0D,10.0D,0.0D,0.0D,0.25,0.25);
				Tessellator.getInstance().draw();
			}
			worldRenderer.reset();
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
			worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
			addBlock(worldRenderer, 5.0D,10.0D,5.0D,11.0D,16.0D,11.0D,0.0D,0.0D,0.375,0.375);
			Tessellator.getInstance().draw();
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
		if (te instanceof earthInfuserEntity){
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			this.bindTexture(tex);
			GL11.glTranslated(x, y, z);
			GL11.glScaled(1.0/16.0, 1.0/16.0, 1.0/16.0);
			WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
			if (((earthInfuserEntity)te).filled){
				GL11.glColor4f(0.0f, 0.75f, 0.0f, 1.0f);
				worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
				addBlock(worldRenderer, 6.0D,1.0D,6.0D,10.0D,5.0D,10.0D,0.0D,0.0D,0.25,0.25);
				Tessellator.getInstance().draw();
			}
			worldRenderer.reset();
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
			worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
			addBlock(worldRenderer, 5.0D,0.0D,5.0D,11.0D,6.0D,11.0D,0.0D,0.0D,0.375,0.375);
			Tessellator.getInstance().draw();
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
		if (te instanceof waterInfuserEntity){
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			this.bindTexture(tex);
			GL11.glTranslated(x, y, z);
			GL11.glScaled(1.0/16.0, 1.0/16.0, 1.0/16.0);
			WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
			if (((waterInfuserEntity)te).filled){
				GL11.glColor4f(0.125f, 0.0f, 0.75f, 1.0f);
				worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
				addBlock(worldRenderer, 6.0D,11.0D,6.0D,10.0D,15.0D,10.0D,0.0D,0.0D,0.25,0.25);
				Tessellator.getInstance().draw();
			}
			worldRenderer.reset();
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
			worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
			addBlock(worldRenderer, 5.0D,10.0D,5.0D,11.0D,16.0D,11.0D,0.0D,0.0D,0.375,0.375);
			Tessellator.getInstance().draw();
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
		if (te instanceof airInfuserEntity){
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			this.bindTexture(tex);
			GL11.glTranslated(x, y, z);
			GL11.glScaled(1.0/16.0, 1.0/16.0, 1.0/16.0);
			WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
			if (((airInfuserEntity)te).filled){
				GL11.glColor4f(0.75f, 1.0f, 1.0f, 1.0f);
				worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
				addBlock(worldRenderer, 6.0D,6.0D,6.0D,10.0D,10.0D,10.0D,0.0D,0.0D,0.25,0.25);
				Tessellator.getInstance().draw();
			}
			worldRenderer.reset();
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
			worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
			addBlock(worldRenderer, 5.0D,5.0D,5.0D,11.0D,11.0D,11.0D,0.0D,0.0D,0.375,0.375);
			Tessellator.getInstance().draw();
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
		if (te instanceof lightInfuserEntity){
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			this.bindTexture(tex);
			GL11.glTranslated(x, y, z);
			GL11.glScaled(1.0/16.0, 1.0/16.0, 1.0/16.0);
			WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
			if (((lightInfuserEntity)te).filled){
				GL11.glColor4f(1.0f, 1.0f, 0.75f, 1.0f);
				worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
				addBlock(worldRenderer, 1.0D,6.0D,6.0D,5.0D,10.0D,10.0D,0.0D,0.0D,0.25,0.25);
				Tessellator.getInstance().draw();
			}
			worldRenderer.reset();
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
			worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
			addBlock(worldRenderer, 0.0D,5.0D,5.0D,6.0D,11.0D,11.0D,0.0D,0.0D,0.375,0.375);
			Tessellator.getInstance().draw();
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
		if (te instanceof voidInfuserEntity){
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			this.bindTexture(tex);
			GL11.glTranslated(x, y, z);
			GL11.glScaled(1.0/16.0, 1.0/16.0, 1.0/16.0);
			WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
			if (((voidInfuserEntity)te).filled){
				GL11.glColor4f(0.125f, 0.0f, 0.125f, 1.0f);
				worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
				addBlock(worldRenderer, 6.0D,11.0D,1.0D,10.0D,15.0D,5.0D,0.0D,0.0D,0.25,0.25);
				Tessellator.getInstance().draw();
			}
			worldRenderer.reset();
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.75f);
			worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
			addBlock(worldRenderer, 5.0D,10.0D,0.0D,11.0D,16.0D,6.0D,0.0D,0.0D,0.375,0.375);
			Tessellator.getInstance().draw();
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}
}
