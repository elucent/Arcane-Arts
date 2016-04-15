package elucent.aa;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class energyCellRenderer extends TileEntitySpecialRenderer {
	public RenderManager manager = Minecraft.getMinecraft().getRenderManager();
	public RenderItem itemRenderer;
	public energyCellRenderer(){
	}
	
	public void drawCrystal(WorldRenderer wr){
		wr.pos(0, 0.25f, 0).tex(0.1875,0.375).endVertex();
		wr.pos(0, 0, -0.125f).tex(0,0.0).endVertex();
		wr.pos(-0.125f, 0, 0).tex(0.375,0.0).endVertex();
		wr.pos(0, 0.25f, 0).tex(0.1875,0.375).endVertex();
		wr.pos(0.125f, 0, 0).tex(0,0.0).endVertex();
		wr.pos(0, 0, -0.125f).tex(0.375,0.0).endVertex();
		wr.pos(0, 0.25f, 0).tex(0.1875,0.375).endVertex();
		wr.pos(0, 0, 0.125f).tex(0,0.0).endVertex();
		wr.pos(0.125f, 0, 0).tex(0.375,0.0).endVertex();
		wr.pos(0, 0.25f, 0).tex(0.1875,0.375).endVertex();
		wr.pos(-0.125f, 0, 0).tex(0,0.0).endVertex();
		wr.pos(0, 0, 0.125f).tex(0.375,0.0).endVertex();

		wr.pos(-0.125f, 0, 0).tex(0,0.375).endVertex();
		wr.pos(0, 0, -0.125f).tex(0.375,0.375).endVertex();
		wr.pos(0, -0.25f, 0).tex(0.1875,0.0).endVertex();
		wr.pos(0, 0, -0.125f).tex(0,0.375).endVertex();
		wr.pos(0.125f, 0, 0).tex(0.375,0.375).endVertex();
		wr.pos(0, -0.25f, 0).tex(0.1875,0.0).endVertex();
		wr.pos(0.125f, 0, 0).tex(0,0.375).endVertex();
		wr.pos(0, 0, 0.125f).tex(0.375,0.375).endVertex();
		wr.pos(0, -0.25f, 0).tex(0.1875,0.0).endVertex();
		wr.pos(0, 0, 0.125f).tex(0,0.375).endVertex();
		wr.pos(-0.125f, 0, 0).tex(0.375,0.375).endVertex();
		wr.pos(0, -0.25f, 0).tex(0.1875,0.0).endVertex();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
		if (te instanceof energyCellEntity){
			energyCellEntity ate = (energyCellEntity)te;
			GL11.glPushMatrix();
			GL11.glPushClientAttrib(0);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			this.bindTexture(new ResourceLocation("arcanearts:textures/blocks/blank.png"));
			GL11.glTranslated(x, y, z);
			GL11.glTranslated(0.5, 0.5, 0.5);
			WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
			
			GL11.glRotated(ate.angle*2.4,0,1,0);
			GL11.glTranslated(-0.3, 0, 0);
			GL11.glRotated(ate.angle*4.8, 0, 1, 0);
			GL11.glTranslated(0,0.0625*Math.sin(Math.toRadians(ate.angle*9.6)),0);
			GL11.glRotated(15*Math.sin(Math.toRadians(ate.angle*9.6)), 1, 0, 0);
			GL11.glColor4d(1.0f*((ate.fireCapacity-ate.fireValue)/ate.fireCapacity)+1.0f*((ate.fireValue)/ate.fireCapacity)
					, 1.0f*((ate.fireCapacity-ate.fireValue)/ate.fireCapacity)+0.25f*((ate.fireValue)/ate.fireCapacity)
					, 1.0f*((ate.fireCapacity-ate.fireValue)/ate.fireCapacity)+0.0f*((ate.fireValue)/ate.fireCapacity), 1.0);
			worldRenderer.begin(4, DefaultVertexFormats.POSITION_TEX);
			drawCrystal(worldRenderer);
			Tessellator.getInstance().draw();
			worldRenderer.reset();

			GL11.glRotated(-15*Math.sin(Math.toRadians(ate.angle*9.6)), 1, 0, 0);
			GL11.glTranslated(0,-0.0625*Math.sin(Math.toRadians(ate.angle*9.6)),0);
			GL11.glRotated(-ate.angle*4.8, 0, 1, 0);
			GL11.glTranslated(0.3, 0, 0);
			GL11.glRotated(60, 0, 1, 0);
			GL11.glTranslated(-0.3, 0, 0);
			GL11.glRotated(ate.angle*4.8, 0, 1, 0);
			GL11.glTranslated(0,0.0625*Math.sin(Math.toRadians(ate.angle*9.6+60)),0);
			GL11.glRotated(15*Math.sin(Math.toRadians(ate.angle*9.6+60)), 1, 0, 0);
			GL11.glColor4d(1.0*((ate.earthCapacity-ate.earthValue)/ate.earthCapacity)+0.0*((ate.earthValue)/ate.earthCapacity), 1.0*((ate.earthCapacity-ate.earthValue)/ate.earthCapacity)+0.75*((ate.earthValue)/ate.earthCapacity), 1.0*((ate.earthCapacity-ate.earthValue)/ate.earthCapacity)+0.0*((ate.earthValue)/ate.earthCapacity), 1.0);
			worldRenderer.begin(4, DefaultVertexFormats.POSITION_TEX);
			drawCrystal(worldRenderer);
			Tessellator.getInstance().draw();
			worldRenderer.reset();

			GL11.glRotated(-15*Math.sin(Math.toRadians(ate.angle*9.6+60)), 1, 0, 0);
			GL11.glTranslated(0,-0.0625*Math.sin(Math.toRadians(ate.angle*9.6+60)),0);
			GL11.glRotated(-ate.angle*4.8, 0, 1, 0);
			GL11.glTranslated(0.3, 0, 0);
			GL11.glRotated(60, 0, 1, 0);
			GL11.glTranslated(-0.3, 0, 0);
			GL11.glRotated(ate.angle*4.8, 0, 1, 0);
			GL11.glTranslated(0,0.0625*Math.sin(Math.toRadians(ate.angle*9.6+120)),0);
			GL11.glRotated(15*Math.sin(Math.toRadians(ate.angle*9.6+120)), 1, 0, 0);
			GL11.glColor4d(1.0*((ate.waterCapacity-ate.waterValue)/ate.waterCapacity)+0.125*((ate.waterValue)/ate.waterCapacity), 1.0*((ate.waterCapacity-ate.waterValue)/ate.waterCapacity)+0.0*((ate.waterValue)/ate.waterCapacity), 1.0*((ate.waterCapacity-ate.waterValue)/ate.waterCapacity)+0.75*((ate.waterValue)/ate.waterCapacity), 1.0);
			worldRenderer.begin(4, DefaultVertexFormats.POSITION_TEX);
			drawCrystal(worldRenderer);
			Tessellator.getInstance().draw();
			worldRenderer.reset();

			GL11.glRotated(-15*Math.sin(Math.toRadians(ate.angle*9.6+120)), 1, 0, 0);
			GL11.glTranslated(0,-0.0625*Math.sin(Math.toRadians(ate.angle*9.6+120)),0);
			GL11.glRotated(-ate.angle*4.8, 0, 1, 0);
			GL11.glTranslated(0.3, 0, 0);
			GL11.glRotated(60, 0, 1, 0);
			GL11.glTranslated(-0.3, 0, 0);
			GL11.glRotated(ate.angle*4.8, 0, 1, 0);
			GL11.glTranslated(0,0.0625*Math.sin(Math.toRadians(ate.angle*9.6+180)),0);
			GL11.glRotated(15*Math.sin(Math.toRadians(ate.angle*9.6+180)), 1, 0, 0);
			GL11.glColor4d(1.0*((ate.airCapacity-ate.airValue)/ate.airCapacity)+0.75*((ate.airValue)/ate.airCapacity), 1.0*((ate.airCapacity-ate.airValue)/ate.airCapacity)+1.0*((ate.airValue)/ate.airCapacity), 1.0*((ate.airCapacity-ate.airValue)/ate.airCapacity)+1.0*((ate.airValue)/ate.airCapacity), 1.0);
			worldRenderer.begin(4, DefaultVertexFormats.POSITION_TEX);
			drawCrystal(worldRenderer);
			Tessellator.getInstance().draw();
			worldRenderer.reset();

			GL11.glRotated(-15*Math.sin(Math.toRadians(ate.angle*9.6+180)), 1, 0, 0);
			GL11.glTranslated(0,-0.0625*Math.sin(Math.toRadians(ate.angle*9.6+180)),0);
			GL11.glRotated(-ate.angle*4.8, 0, 1, 0);
			GL11.glTranslated(0.3, 0, 0);
			GL11.glRotated(60, 0, 1, 0);
			GL11.glTranslated(-0.3, 0, 0);
			GL11.glRotated(ate.angle*4.8, 0, 1, 0);
			GL11.glTranslated(0,0.0625*Math.sin(Math.toRadians(ate.angle*9.6+240)),0);
			GL11.glRotated(15*Math.sin(Math.toRadians(ate.angle*9.6+240)), 1, 0, 0);
			GL11.glColor4d(1.0*((ate.lightCapacity-ate.lightValue)/ate.lightCapacity)+1.0*((ate.lightValue)/ate.lightCapacity), 1.0*((ate.lightCapacity-ate.lightValue)/ate.lightCapacity)+1.0*((ate.lightValue)/ate.lightCapacity), 1.0*((ate.lightCapacity-ate.lightValue)/ate.lightCapacity)+0.75*((ate.lightValue)/ate.lightCapacity), 1.0);
			worldRenderer.begin(4, DefaultVertexFormats.POSITION_TEX);
			drawCrystal(worldRenderer);
			Tessellator.getInstance().draw();
			worldRenderer.reset();

			GL11.glRotated(-15*Math.sin(Math.toRadians(ate.angle*9.6+240)), 1, 0, 0);
			GL11.glTranslated(0,-0.0625*Math.sin(Math.toRadians(ate.angle*9.6+240)),0);
			GL11.glRotated(-ate.angle*4.8, 0, 1, 0);
			GL11.glTranslated(0.3, 0, 0);
			GL11.glRotated(60, 0, 1, 0);
			GL11.glTranslated(-0.3, 0, 0);
			GL11.glRotated(ate.angle*4.8, 0, 1, 0);
			GL11.glTranslated(0,0.0625*Math.sin(Math.toRadians(ate.angle*9.6+300)),0);
			GL11.glRotated(15*Math.sin(Math.toRadians(ate.angle*9.6+300)), 1, 0, 0);
			GL11.glColor4d(1.0*((ate.voidCapacity-ate.voidValue)/ate.voidCapacity)+0.125*((ate.voidValue)/ate.voidCapacity), 1.0*((ate.voidCapacity-ate.voidValue)/ate.voidCapacity)+0.0*((ate.voidValue)/ate.voidCapacity), 1.0*((ate.voidCapacity-ate.voidValue)/ate.voidCapacity)+0.125*((ate.voidValue)/ate.voidCapacity), 1.0);
			worldRenderer.begin(4, DefaultVertexFormats.POSITION_TEX);
			drawCrystal(worldRenderer);
			Tessellator.getInstance().draw();
			worldRenderer.reset();
			GL11.glColor4d(1.0,1.0,1.0,1.0);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
			GL11.glPopClientAttrib();
		}
	}
}
