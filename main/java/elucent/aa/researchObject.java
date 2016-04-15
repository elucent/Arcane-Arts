package elucent.aa;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class researchObject {
	public String name, discipline;
	public ItemStack icon = null;
	public int numPages = 0;
	public int researchValue = 0;
	public ArrayList<researchPage> pages = new ArrayList<researchPage>();
	
	public researchObject(String parDisc, int value, String parName, ItemStack parIcon, int numPages){
		discipline = parDisc;
		name = parName;
		icon = parIcon;
		this.numPages = numPages;
		for (int i = 0; i < numPages; i ++){
			pages.add(new researchPage());
		}
		researchValue = value;
	}
	
	public researchObject add(int page, String infoString){
		pages.get(page).addText(infoString);
		return this;
	}
	
	public researchObject addRecipe(int page, ItemStack parOutput, ItemStack[][] parInputs){
		pages.get(page).addRecipe(parOutput, parInputs);
		return this;
	}
	
	public researchObject addSmelting(int page, ItemStack parOutput, ItemStack parInput){
		pages.get(page).addSmelting(parOutput, parInput);
		return this;
	}
}
