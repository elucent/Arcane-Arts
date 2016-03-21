package elucent.aa;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

public class crucibleRenderer extends TileEntitySpecialRenderer {
	public RenderManager manager = Minecraft.getMinecraft().getRenderManager();
	public RenderItem itemRenderer;
	public crucibleRenderer(){
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
		if (te instanceof crucibleEntity){
			crucibleEntity ate = (crucibleEntity)te;
			for (int i = 0; i < ate.renderItems.size(); i ++){
				GL11.glPushMatrix();
				EntityItem item = new EntityItem(Minecraft.getMinecraft().theWorld,x,y,z,ate.renderItems.get(i));
				item.hoverStart = 0;
				GL11.glTranslated(x+0.5, y+0.5+Math.abs(0.25*Math.sin(Math.toRadians(ate.itemRotation*0.5+(360.0*((double)i/(double)ate.renderItems.size()))))), z+0.5);
				GL11.glRotated(ate.itemRotation*0.5+(360.0*((double)i/(double)ate.renderItems.size())), 0, 1, 0);
				GL11.glTranslated(0, 0, 0.2);
				GL11.glRotated(ate.itemRotation, 0, 1, 0);
				GL11.glScaled(0.5, 0.5, 0.5);
				Minecraft.getMinecraft().getRenderManager().doRenderEntity(item, 0, 0, 0, 0, 0, true);
				GL11.glPopMatrix();
			}
		}
	}
}
