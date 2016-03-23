package elucent.aa;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class researchObject {
	public String name;
	public ArrayList<String> info = new ArrayList<String>();
	public ItemStack icon = null;
	
	public researchObject(String parName, ItemStack parIcon){
		name = parName;
		icon = parIcon;
	}
	
	public researchObject add(String infoString){
		info.add(infoString);
		return this;
	}
}
