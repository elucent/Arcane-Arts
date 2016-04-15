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
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class rayLensRenderer extends TileEntitySpecialRenderer {
	public RenderManager manager = Minecraft.getMinecraft().getRenderManager();
	public RenderItem itemRenderer;
	public rayLensRenderer(){
	}
	
	public void drawLens(WorldRenderer wr){
		for (int i = 0; i < 8; i ++){
			wr.pos(0, 0.125, 0).tex(0.25,0.25).endVertex();
			wr.pos(0.25*Math.sin(Math.toRadians(i*45.0)), 0, 0.25*Math.cos(Math.toRadians(i*45.0))).tex(0.25+0.25*Math.cos(Math.toRadians(i*45.0)),0.25+0.25*Math.sin(Math.toRadians(i*45.0))).endVertex();
			wr.pos(0.25*Math.sin(Math.toRadians((i+1)*45.0)), 0, 0.25*Math.cos(Math.toRadians((i+1)*45.0))).tex(0,0).tex(0.25+0.25*Math.cos(Math.toRadians((i+1)*45.0)),0.25+0.25*Math.sin(Math.toRadians((i+1)*45.0))).endVertex();	
			wr.pos(0, -0.125, 0).tex(0.25,0.25).endVertex();
			wr.pos(0.25*Math.sin(Math.toRadians(i*45.0)), 0, 0.25*Math.cos(Math.toRadians(i*45.0))).tex(0.25+0.25*Math.cos(Math.toRadians(i*45.0)),0.25+0.25*Math.sin(Math.toRadians(i*45.0))).endVertex();
			wr.pos(0.25*Math.sin(Math.toRadians((i+1)*45.0)), 0, 0.25*Math.cos(Math.toRadians((i+1)*45.0))).tex(0,0).tex(0.25+0.25*Math.cos(Math.toRadians((i+1)*45.0)),0.25+0.25*Math.sin(Math.toRadians((i+1)*45.0))).endVertex();
		}
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
		if (te instanceof rayLensEntity){
			rayLensEntity ate = (rayLensEntity)te;
			GL11.glPushMatrix();
			GL11.glPushClientAttrib(0);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_CULL_FACE);
			this.bindTexture(new ResourceLocation("arcanearts:textures/blocks/blank.png"));
			GL11.glTranslated(x, y, z);
			GL11.glTranslated(0.5, 0.5, 0.5);
			WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
			
			GL11.glScaled(2.0, 2.0, 2.0);
			double flatDistance = Math.sqrt(Math.pow((ate.target.getX()+0.5)-(ate.getPos().getX()+0.5),2)+Math.pow((ate.target.getZ()+0.5)-(ate.getPos().getZ()+0.5), 2));
			double dirX = 90-Math.toDegrees(Math.atan2((ate.target.getY()+0.5)-(ate.getPos().getY()+0.5),flatDistance));
			double dirY = Math.toDegrees(Math.atan2((ate.target.getX()+0.5)-(ate.getPos().getX()+0.5),(ate.target.getZ()+0.5)-(ate.getPos().getZ()+0.5)));
			GL11.glRotated(dirY, 0, 1, 0);
			GL11.glRotated(dirX, 1, 0, 0);
			if (ate.target != ate.getPos()){
				GL11.glRotated(ate.angle*14.4, 0, 1, 0);
			}
			worldRenderer.begin(4, DefaultVertexFormats.POSITION_TEX);
			drawLens(worldRenderer);
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
