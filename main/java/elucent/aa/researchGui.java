package elucent.aa;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3i;
public class researchGui extends GuiScreen {
	public float scrollY = 32;
	public float scrollHeight = 0;
	public boolean scrollable = false;
	public float offsetY = 0;
	int shiftX = 0;
	RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
	int shiftY = 0;
	int pageIndex = 0;
	boolean inPage = false;
	public String discipline = "alchemy";
	public ArrayList<researchObject> researches = new ArrayList<researchObject>();
	researchObject currentResearch = null;
	
	public researchGui(){
		researches.add(new researchObject("alchemy", 0, "Weak Catalyst",new ItemStack(aaItemManager.itemMaterial,1,0),3)
			.add(0,"The weak catalyst is key to beginning alchemy.")
			.add(0, "A simple mixture of gunpowder and charcoal,")
			.add(0,"this catalyst allows you to create more")
			.add(0, "magically capable materials and devices.")
			.addRecipe(0, new ItemStack(aaItemManager.itemMaterial,4,0), new ItemStack[][]{{new ItemStack(Items.gunpowder),new ItemStack(Items.coal,1,1),null},{null,null,null},{null,null,null}})
			.add(1, "While the catalyst is useful on its own, it can")
			.add(1, "be crystallized for use in more refined")
			.add(1, "constructs. You will need to create a Weak")
			.add(1, "Crystal Mixture.")
			.addRecipe(1, new ItemStack(aaItemManager.itemMaterial,1,1), new ItemStack[][]{{null,new ItemStack(aaItemManager.itemMaterial,1,0),null},{new ItemStack(aaItemManager.itemMaterial,1,0),new ItemStack(Blocks.glass),new ItemStack(aaItemManager.itemMaterial,1,0)},{null,new ItemStack(aaItemManager.itemMaterial,1,0),null}})
			.add(2, "Then, smelt the crystal mixture to yield one")
			.add(2, "brittle crystal. This will be the base of many")
			.add(2, "of your early devices for manipulating elements.")
			.addSmelting(2, new ItemStack(aaItemManager.itemMaterial,1,2), new ItemStack(aaItemManager.itemMaterial,1,1)));
		researches.add(new researchObject("alchemy", 1, "Breaking Things Down",new ItemStack(aaItemManager.reducerBlock,1,0),3)
			.add(0,"Using the weak catalyst, you have created a")
			.add(0, "device to break down objects into their")
			.add(0,"component parts. It must be placed above lava,")
			.add(0, "then items can be right-clicked into it.")
			.addRecipe(0, new ItemStack(aaItemManager.reducerBlock,1,0), new ItemStack[][]{{new ItemStack(Blocks.stonebrick),null,new ItemStack(Blocks.stonebrick)},{new ItemStack(Blocks.stonebrick),new ItemStack(aaItemManager.itemMaterial,1,0),new ItemStack(Blocks.stonebrick)},{new ItemStack(Blocks.cobblestone),new ItemStack(Items.coal),new ItemStack(Blocks.cobblestone)}})
			.add(1, "Items will be broken down into raw matter.")
			.add(1, "This matter is released from the top of the")
			.add(1, "Reducer, and can be used in potion mixtures")
			.add(1, "and replicating items and blocks.")
			.add(2, "Items are also broken down into elemental")
			.add(2, "energy. There are six kinds of elemental")
			.add(2, "energy: fire, earth, water, air, light, and void.")
			.add(2, "These will slowly evaporate if left in the")
			.add(2, "Reducer, but can be taken out and safely")
			.add(2, "stored in Alchemy Flasks.")
			.addRecipe(2, new ItemStack(aaItemManager.itemAlchemyFlask,1,0), new ItemStack[][]{{null,null,new ItemStack(Blocks.log)},{new ItemStack(Blocks.glass),new ItemStack(Blocks.glass),null},{new ItemStack(Blocks.glass),new ItemStack(Blocks.glass),null}}));
		researches.add(new researchObject("alchemy", 1, "Long-term Storage",new ItemStack(aaItemManager.vesselBlock,1,0),2)
			.add(0,"Brittle crystals, though weak, have absorbing")
			.add(0, "properties, capable of holding elemental energy.")
			.add(0,"Using them, you have created a vessel to hold")
			.add(0, "elemental energy for later use.")
			.addRecipe(0, new ItemStack(aaItemManager.vesselBlock,1,0), new ItemStack[][]{{new ItemStack(Blocks.stonebrick),new ItemStack(Blocks.glass),new ItemStack(Blocks.stonebrick)},{new ItemStack(Blocks.glass),new ItemStack(aaItemManager.itemMaterial,1,2),new ItemStack(Blocks.glass)},{new ItemStack(Blocks.stonebrick),new ItemStack(Blocks.glass),new ItemStack(Blocks.stonebrick)}})
			.add(1, "The Repository can hold up to 640 units of a")
			.add(1, "element, and will not lose elements to")
			.add(1, "evaporation like an Alchemical Reducer would.")
			.add(1, "Keep in mind that these Repositories will not")
			.add(1, "hold their contents when broken."));
		researches.add(new researchObject("alchemy", 1, "Mixtures",new ItemStack(aaItemManager.crucibleBlock,1,0),2)
			.add(0,"The power within brittle crystals can also")
			.add(0, "be used in combining items. The Alchemical")
			.add(0,"Crucible is capable of mixing items into new")
			.add(0, "ones, and also mixing potions.")
			.addRecipe(0, new ItemStack(aaItemManager.crucibleBlock,1,0), new ItemStack[][]{{new ItemStack(Items.iron_ingot),null,new ItemStack(Items.iron_ingot)},{new ItemStack(Items.iron_ingot),null,new ItemStack(Items.iron_ingot)},{new ItemStack(Blocks.cobblestone),new ItemStack(aaItemManager.itemMaterial,1,2),new ItemStack(Blocks.cobblestone)}})
			.add(1, "Items can be placed into the crucible by right")
			.add(1, "clicking, as well as taken out. Once items")
			.add(1, "are present in the right comibination, the")
			.add(1, "recipe can be completed by adding a certain")
			.add(1, "item. Either a certain type of catalyst must")
			.add(1, "be added, or a potion vial."));
		researches.add(new researchObject("alchemy", 1, "Potion Vials",new ItemStack(aaItemManager.vial,1,0),7)
			.add(0,"Potion vials are a means of creating potions")
			.add(0, "using a Crucible. Place ingredients in the")
			.add(0,"Crucible, then right click with a vial to")
			.add(0, "create your potion.")
			.addRecipe(0, new ItemStack(aaItemManager.vial,2,0), new ItemStack[][]{{null,new ItemStack(Blocks.planks),null},{null,new ItemStack(Blocks.glass),null},{null,new ItemStack(Blocks.glass),null}})
			.add(1, "There are two types of potions:")
			.add(1, " - Effect potions grant an effect")
			.add(1, " - Cure potions remove an effect")
			.add(1, "All potions require a type-determining")
			.add(1, "item, an effect-determining item, and some")
			.add(1, "raw matter.")
			.add(2, "There are three tiers of potions,")
			.add(2, "determined by their effect-determining")
			.add(2, "item.")
			.add(2, "Tier 1:")
			.add(2, " - Weak catalyst (effect), rose red (cure)")
			.add(2, "Tier 2:")
			.add(2, " - Energized catalyst (effect), redstone (cure)")
			.add(2, "Tier 3:")
			.add(2, " - Volatile catalyst (effect), nether wart (cure)")
			.add(3, "Potions will require one effect-determining item,")
			.add(3, "and some amount of an associated type of raw")
			.add(3, "matter. Tier 1 potions require one")
			.add(3, "item of matter, tier 2 potions require two,")
			.add(3, "and tier 3 potions require four.")
			.add(4, "You have discovered the following effect")
			.add(4, "items and associated matter types:")
			.add(4, " - Speed: sugar and vegetable matter.")
			.add(4, " - Slowness: string and watery matter.")
			.add(4, " - Strength: blaze powder and fleshy matter.")
			.add(4, " - Fire resist: magma cream and fleshy matter.")
			.add(4, " - Regeneration: ghast tear and vegetable")
			.add(4, "   matter.")
			.add(4, " - Leaping: rabbit foot and dry organic matter.")
			.add(4, " - Weakness: feather and mineral matter.")
			.add(4, " - Poison: spider eyes and vegetable matter.")
			.add(4, " - Haste: cocoa beans and reactive matter.")
			.add(5, " - Mining fatigue: bones and dry organic matter.")
			.add(5, " - Resistance: prismarine shards and mineral")
			.add(5, "   matter.")
			.add(5, " - Water breathing: pufferfish and fleshy")
			.add(5, "   matter.")
			.add(5, " - Night vision: gold carrots and reactive")
			.add(5, "   matter.")
			.add(5, " - Blindness: ink sacs and dry organic matter.")
			.add(5, " - Nausea: red mushrooms and vegetable matter.")
			.add(5, " - Invisibility: glowstone dust and fleshy")
			.add(5, "   matter.")
			.add(5, " - Absorption: gold apples and watery matter.")
			.add(6, " - Health boost: apples and watery matter.")
			.add(6, " - Hunger: rotten flesh and fleshy matter.")
			.add(6, " - Wither: coal and dry organic matter.")
			.add(6, " - Harming: fermented spider eyes and dry")
			.add(6, "   organic matter.")
			.add(6, " - Healing: glistering melon and vegetable")
			.add(6, "   matter."));
		researches.add(new researchObject("alchemy", 1, "Releasing Elements",new ItemStack(aaItemManager.diffuserBlock,1,0),2)
			.add(0,"It seems that you lack the ability to")
			.add(0, "properly manipulate elemental energy. The")
			.add(0,"Atmospheric Diffuser is your first attempt")
			.add(0, "at using them by releasing them into the")
			.add(0, "air.")
			.addRecipe(0, new ItemStack(aaItemManager.diffuserBlock,1,0), new ItemStack[][]{{new ItemStack(Blocks.stonebrick),new ItemStack(Items.gold_ingot),new ItemStack(Blocks.stonebrick)},{new ItemStack(Blocks.stonebrick),new ItemStack(aaItemManager.itemMaterial,1,2),new ItemStack(Blocks.stonebrick)},{new ItemStack(Blocks.cobblestone),new ItemStack(Items.gold_ingot),new ItemStack(Blocks.cobblestone)}})
			.add(1, "The device can be filled with an ordinary")
			.add(1, "alchemy flask, and will release its contained")
			.add(1, "elements into the air for a certain effect.")
			.add(1, "It must be powered by redstone to function.")
			.add(1, "")
			.add(1, "Effects:")
			.add(1, " - Fire: ignite nearby mobs.")
			.add(1, " - Earth: solidify nearby blocks.")
			.add(1, " - Water: extinguish and slow nearby mobs.")
			.add(1, " - Air: push away nearby mobs.")
			.add(1, " - Light: apply regeneration and grow crops.")
			.add(1, " - Void: hurt and wither nearby mobs."));
		researches.add(new researchObject("alchemy", 0, "Energized Catalyst",new ItemStack(aaItemManager.itemMaterial,1,3),3)
			.add(0,"Using the minerals you found underground,")
			.add(0, "you have synthesized a new, powerful catalyst.")
			.add(0,"This catalyst seems capable of harnessing")
			.add(0, "elemental energy to a higher degree.")
			.addRecipe(0, new ItemStack(aaItemManager.itemMaterial,4,3), new ItemStack[][]{{new ItemStack(Items.redstone),new ItemStack(Items.dye,1,4),null},{null,null,null},{null,null,null}})
			.add(1, "As with the weak catalyst, it can be alloyed")
			.add(1, "with glass to form crystals.")
			.addRecipe(1, new ItemStack(aaItemManager.itemMaterial,1,4), new ItemStack[][]{{null,new ItemStack(aaItemManager.itemMaterial,1,3),null},{new ItemStack(aaItemManager.itemMaterial,1,3),new ItemStack(Blocks.glass),new ItemStack(aaItemManager.itemMaterial,1,3)},{null,new ItemStack(aaItemManager.itemMaterial,1,3),null}})
			.add(2, "Smelting the mixture is sufficient to create")
			.add(2, "the crystal, which seems to hold some degree")
			.add(2, "of internal energy. Perhaps you can use this")
			.add(2, "for some more powerful devices.")
			.addSmelting(2, new ItemStack(aaItemManager.itemMaterial,1,5), new ItemStack(aaItemManager.itemMaterial,1,4)));
	/*researches.add(new researchObject("alchemy", "Storing Elements",new ItemStack(aaItemManager.vesselBlock,1,0)).add("Welcome to the mod!"));
		researches.add(new researchObject("alchemy", "Alchemical Crucible",new ItemStack(aaItemManager.crucibleBlock,1,0)).add("Welcome to the mod!"));
		researches.add(new researchObject("alchemy", "Atmospheric Diffuser",new ItemStack(aaItemManager.diffuserBlock,1,0)).add("Welcome to the mod!"));*/
		scrollHeight = 40*(researches.size());
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton){
		if (!inPage){
			for (int i = 0; i < researches.size(); i ++){
				if (mouseX >= shiftX+16 && mouseX < shiftX+192 && mouseY >= 16+i*40-offsetY+shiftY && mouseY < 48+i*40-offsetY+shiftY){
					inPage = true;
					currentResearch = researches.get(i);
					pageIndex = 0;
				}
			}
			if (mouseX >= 217+shiftX && mouseX < 241+shiftX && mouseY >= scrollY+shiftY && mouseY < 16+scrollY+shiftY){
				scrollable = true;
			}
		}
		else {
			if (mouseX >= shiftX+8 && mouseX < shiftX+40 && mouseY >= shiftY+142 && mouseY < shiftY+158){
				if (pageIndex > 0){
					pageIndex -= 1;
				}
				else {
					inPage = false;
					currentResearch = null;
				}
			}
			if (mouseX >= shiftX+216 && mouseX < shiftX+248 && mouseY >= shiftY+142 && mouseY < shiftY+158){
				if (pageIndex < currentResearch.numPages-1){
					pageIndex += 1;
				}
			}
		}
	}
	
	@Override
	public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick){
		if (!inPage){
			if (scrollable){
				scrollY = Math.max(32,Math.min(mouseY-shiftY,118));
				offsetY = ((scrollY-32.0f)/86.0f)*scrollHeight;
			}
		}
	}
	
	public void drawResearchPages(){
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arcanearts:textures/gui/researchPage.png"));
		this.drawTexturedModalRect(0, 0, 256, 0, 256, 166);
		this.drawTexturedModalRect(8, 142, 0, 232, 32, 16);
		this.drawTexturedModalRect(216, 142, 32, 232, 32, 16);
		for (int i = 0; i < currentResearch.pages.size(); i ++){
			if (i == pageIndex){
				if (currentResearch.pages.get(i).hasSmelting){
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
					this.drawTexturedModalRect(80, 8, 96, 168, 96, 64);
					for (int j = 0; j < currentResearch.pages.get(i).info.size(); j ++){
						GL11.glColor4f(0.125f, 0.125f, 0.125f, 1.0f);
						this.fontRendererObj.drawString(currentResearch.pages.get(i).info.get(j), 8, 80+j*11, aaToolKit.intColor(new Vec3i(32,32,32)));
					}
					renderItem.renderItemIntoGUI(currentResearch.pages.get(i).smeltingInput, 96, 12);
					renderItem.renderItemIntoGUI(new ItemStack(Items.coal,1,0), 96, 52);
					renderItem.renderItemIntoGUI(currentResearch.pages.get(i).recipeResult, 144, 32);
					renderItem.renderItemOverlayIntoGUI(this.fontRendererObj,currentResearch.pages.get(i).recipeResult, 144, 32, Integer.toString(currentResearch.pages.get(i).recipeResult.stackSize));
				}
				else if (currentResearch.pages.get(i).hasRecipe){
					GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
					this.drawTexturedModalRect(80, 8, 0, 168, 96, 64);
					for (int j = 0; j < currentResearch.pages.get(i).info.size(); j ++){
						GL11.glColor4f(0.125f, 0.125f, 0.125f, 1.0f);
						this.fontRendererObj.drawString(currentResearch.pages.get(i).info.get(j), 8, 80+j*11, aaToolKit.intColor(new Vec3i(32,32,32)));
					}
					if (currentResearch.pages.get(i).recipe[0][0] != null){
						renderItem.renderItemIntoGUI(currentResearch.pages.get(i).recipe[0][0], 84, 12);
					}
					if (currentResearch.pages.get(i).recipe[0][1] != null){
						renderItem.renderItemIntoGUI(currentResearch.pages.get(i).recipe[0][1], 104, 12);
					}
					if (currentResearch.pages.get(i).recipe[0][2] != null){
						renderItem.renderItemIntoGUI(currentResearch.pages.get(i).recipe[0][2], 124, 12);
					}
					if (currentResearch.pages.get(i).recipe[1][0] != null){
						renderItem.renderItemIntoGUI(currentResearch.pages.get(i).recipe[1][0], 84, 32);
					}
					if (currentResearch.pages.get(i).recipe[1][1] != null){
						renderItem.renderItemIntoGUI(currentResearch.pages.get(i).recipe[1][1], 104, 32);
					}
					if (currentResearch.pages.get(i).recipe[1][2] != null){
						renderItem.renderItemIntoGUI(currentResearch.pages.get(i).recipe[1][2], 124, 32);
					}
					if (currentResearch.pages.get(i).recipe[2][0] != null){
						renderItem.renderItemIntoGUI(currentResearch.pages.get(i).recipe[2][0], 84, 52);
					}
					if (currentResearch.pages.get(i).recipe[2][1] != null){
						renderItem.renderItemIntoGUI(currentResearch.pages.get(i).recipe[2][1], 104, 52);
					}
					if (currentResearch.pages.get(i).recipe[2][2] != null){
						renderItem.renderItemIntoGUI(currentResearch.pages.get(i).recipe[2][2], 124, 52);
					}
					renderItem.renderItemIntoGUI(currentResearch.pages.get(i).recipeResult, 152, 32);
					renderItem.renderItemOverlayIntoGUI(this.fontRendererObj,currentResearch.pages.get(i).recipeResult, 152, 32, Integer.toString(currentResearch.pages.get(i).recipeResult.stackSize));
				}
				else {
					for (int j = 0; j < currentResearch.pages.get(i).info.size(); j ++){
						GL11.glColor4f(0.125f, 0.125f, 0.125f, 1.0f);
						this.fontRendererObj.drawString(currentResearch.pages.get(i).info.get(j), 8, 8+j*11, aaToolKit.intColor(new Vec3i(32,32,32)));
					}
				}
			}
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton){
		scrollable = false;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		ScaledResolution dimensions = new ScaledResolution(Minecraft.getMinecraft());
		shiftX = (int) (dimensions.getScaledWidth()/2.0-128);
		shiftY = (int) (dimensions.getScaledHeight()/2.0-83);
		this.drawDefaultBackground();
		GL11.glTranslated(dimensions.getScaledWidth()/2.0-128, dimensions.getScaledHeight()/2.0-83, 0);
		if (!inPage){
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arcanearts:textures/gui/researchGuiBackground.png"));
			this.drawTexturedModalRect(0, 0, 256, 0, 256, 166);
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arcanearts:textures/gui/researchGui.png"));
			this.drawTexturedModalRect(216, scrollY, 24, 200, 24, 16);
			for (int i = 0; i < researches.size(); i ++){
				Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arcanearts:textures/gui/researchGui.png"));
				if (16+i*40-offsetY < -25 || 16+i*40-offsetY > 166){
					//
				}
				else if (16+i*40-offsetY < 8){
					this.drawTexturedModalRect(16, 8, 0, 168+Math.abs(8-(16+i*40-(int)offsetY)), 176, Math.max(0, 32-Math.abs(8-(16+i*40-(int)offsetY))));
				}
				else if (16+i*40-offsetY > 126){
					this.drawTexturedModalRect(16, 16+i*40-offsetY, 0, 168, 176, 32 - Math.abs(126-(16+i*40-(int)offsetY)));
				}
				else {
					this.drawTexturedModalRect(16, 16+i*40-offsetY, 0, 168, 176, 32);
				}
				/*if (20+i*40-(int)offsetY > 0){
					this.fontRendererObj.drawString(researches.get(i).name, 20, 20+i*40-(int)offsetY, aaToolKit.intColor(new Vec3i(32,32,32)));
				}*/
				GL11.glColor4f(1, 1, 1, 1);
			}
			for (int i = 0; i < researches.size(); i ++){
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				if (24+i*40-(int)offsetY > 0 && 32+i*40-(int)offsetY < 158){
					renderItem.renderItemIntoGUI(researches.get(i).icon, 24, 24+i*40-(int)offsetY);
				}
				GL11.glColor4f(0.125f, 0.125f, 0.125f, 1.0f);
				if (36+i*40-(int)offsetY > 0 && 28+i*40-(int)offsetY < 166){
					this.fontRendererObj.drawString(researches.get(i).name, 48, 28+i*40-(int)offsetY, aaToolKit.intColor(new Vec3i(32,32,32)));
				}
			}
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("arcanearts:textures/gui/researchGui.png"));
			this.drawTexturedModalRect(0, 0, 0, 0, 256, 166);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		}
		else {
			drawResearchPages();
		}
		GL11.glTranslated(dimensions.getScaledWidth()/-2.0-128, dimensions.getScaledHeight()/-2.0-83, 0);
	}
}
