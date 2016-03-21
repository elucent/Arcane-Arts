package elucent.aa;

import org.lwjgl.opengl.GL11;

import scala.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

public class arcaneForgeRenderer extends TileEntitySpecialRenderer {
	public RenderManager manager = Minecraft.getMinecraft().getRenderManager();
	public RenderItem itemRenderer;
	public arcaneForgeRenderer(){
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
		if (te instanceof arcaneForgeEntity){
			arcaneForgeEntity ate = (arcaneForgeEntity)te;
			for (int i = 0; i < ate.renderItems.size(); i ++){
				GL11.glPushMatrix();
				EntityItem item = new EntityItem(Minecraft.getMinecraft().theWorld,x,y,z,ate.renderItems.get(i));
				item.hoverStart = 0;
				Random random = new Random();
				random.setSeed(item.getEntityItem().hashCode());
				GL11.glTranslated(x, y+0.875+0.0625+((double)i*0.0625), z);
				GL11.glTranslated(0.5, 0, 0.5);
				GL11.glRotated(random.nextFloat()*360.0, 0, 1.0, 0);
				GL11.glTranslated(-0.5, 0, -0.5);
				GL11.glRotated(90, 1, 0, 0);
				GL11.glTranslated(0.5, 0, 0);
				GL11.glScaled(1.5, 1.5, 1.5);
				Minecraft.getMinecraft().getRenderManager().doRenderEntity(item, 0, 0, 0, 0, 0, true);
				GL11.glPopMatrix();
			}
		}
	}
}
