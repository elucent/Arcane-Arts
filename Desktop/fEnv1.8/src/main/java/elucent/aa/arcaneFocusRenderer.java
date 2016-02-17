package elucent.aa;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

public class arcaneFocusRenderer extends TileEntitySpecialRenderer {
	public RenderManager manager = Minecraft.getMinecraft().getRenderManager();
	public RenderItem itemRenderer;
	public arcaneFocusRenderer(){
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
		if (te instanceof arcaneFocusEntity){
			arcaneFocusEntity ate = (arcaneFocusEntity)te;
			if (ate.heldItem != null){
				GL11.glPushMatrix();
				EntityItem item = new EntityItem(Minecraft.getMinecraft().theWorld,x,y,z,ate.heldItem);
				item.hoverStart = 0;
				GL11.glTranslated(x+0.5, y+0.25, z+0.5);
				GL11.glRotated(ate.itemRotation, 0, 1, 0);
				GL11.glTranslated(-(x+0.5), -(y+0.25), -(z+0.5));
				Minecraft.getMinecraft().getRenderManager().doRenderEntity(item, x+0.5, y+0.25, z+0.5, 0, 0, true);
				GL11.glPopMatrix();
			}
		}
	}
}
