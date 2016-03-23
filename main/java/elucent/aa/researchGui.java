package elucent.aa;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3i;
public class researchGui extends GuiScreen {
	public float scrollY = 32;
	public float scrollHeight = 0;
	public boolean scrollable = false;
	public float offsetY = 0;
	
	public ArrayList<researchObject> researches = new ArrayList<researchObject>();
	
	public researchGui(){
		researches.add(new researchObject("Welcome!",new ItemStack(aaItemManager.itemMaterial,1,0)).add("Welcome to the mod!"));
		researches.add(new researchObject("Welcome!",new ItemStack(aaItemManager.itemMaterial,1,0)).add("Welcome to the mod!"));
		researches.add(new researchObject("Welcome!",new ItemStack(aaItemManager.itemMaterial,1,0)).add("Welcome to the mod!"));
		researches.add(new researchObject("Welcome!",new ItemStack(aaItemManager.itemMaterial,1,0)).add("Welcome to the mod!"));
		researches.add(new researchObject("Welcome!",new ItemStack(aaItemManager.itemMaterial,1,0)).add("Welcome to the mod!"));
		researches.add(new researchObject("Welcome!",new ItemStack(aaItemManager.itemMaterial,1,0)).add("Welcome to the mod!"));
		scrollHeight = 40*researches.size();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton){
		if (mouseX >= 137 && mouseX < 161 && mouseY >= scrollY && mouseY < 16+scrollY){
			scrollable = true;
		}
	}
	
	@Override
	public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick){
		if (scrollable){
			scrollY = Math.max(32,Math.min(mouseY,118));
			offsetY = ((scrollY-32.0f)/86.0f)*scrollHeight;
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton){
		scrollable = false;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		this.drawDefaultBackground();
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arcanearts:textures/gui/researchGuiBackground.png"));
		this.drawTexturedModalRect(0, 0, 256, 0, 176, 166);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arcanearts:textures/gui/researchGui.png"));
		this.drawTexturedModalRect(136, scrollY, 200, 0, 24, 16);
		for (int i = 0; i < researches.size(); i ++){
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arcanearts:textures/gui/researchGui.png"));
			if (16+i*40-offsetY < 8){
				this.drawTexturedModalRect(16, 8, 0, 168+Math.abs(8-(16+i*40-(int)offsetY)), 96, Math.max(0, 32-Math.abs(8-(16+i*40-(int)offsetY))));
			}
			/*else if (58+i*40-offsetY > 158){
				this.drawTexturedModalRect(16, 16+i*40, 0, 168, 96, 32+(158-(16+i*40-(int)offsetY)));
			}*/
			else {
				this.drawTexturedModalRect(16, 16+i*40-offsetY, 0, 168, 96, 32);
			}
			if (20+i*40-(int)offsetY > 0){
				this.fontRendererObj.drawString(researches.get(i).name, 20, 20+i*40-(int)offsetY, aaToolKit.intColor(new Vec3i(32,32,32)));
			}
			GL11.glColor4f(1, 1, 1, 1);
		}
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arcanearts:textures/gui/researchGui.png"));
		this.drawTexturedModalRect(0, 0, 0, 0, 176, 166);
	}
}
