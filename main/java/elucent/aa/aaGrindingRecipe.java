
package elucent.aa;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class aaGrindingRecipe {
	public ItemStack input, output;
	public boolean oreDict = false;
	public aaGrindingRecipe(ItemStack input, ItemStack output){
		this.input = input;
		this.output = output;
	}
	
	public boolean doesMatch(ItemStack input){
		if (this.input.hasTagCompound() && input.hasTagCompound()){
			return input.getItem() == this.input.getItem() && input.getTagCompound() == this.input.getTagCompound() && input.getItemDamage() == this.input.getItemDamage();
		}
		else if (this.input.hasTagCompound() && !input.hasTagCompound() || input.hasTagCompound() && !this.input.hasTagCompound()){
			return false;
		}
		else {
			return input.getItem() == this.input.getItem() && input.getItemDamage() == this.input.getItemDamage();
		}
	}
}
