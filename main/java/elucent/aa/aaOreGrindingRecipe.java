
package elucent.aa;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class aaOreGrindingRecipe {
	public ItemStack output;
	public String input;
	public aaOreGrindingRecipe(String input, ItemStack output){
		this.input = input;
		this.output = output;
	}
	
	public boolean doesMatch(ItemStack input){
		return OreDictionary.containsMatch(true, OreDictionary.getOres(this.input), input);
	}
}
