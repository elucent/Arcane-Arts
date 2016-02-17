package elucent.aa;

import net.minecraft.item.ItemStack;

public class aaSimpleInfusionRecipe {
	public ItemStack input, result = null;
	public aaElement element;
	public aaSimpleInfusionRecipe(ItemStack in, aaElement aaElement, ItemStack out){
		input = in;
		this.element = aaElement;
		result = out;
		System.out.println("Result is itemStack " + result.getUnlocalizedName() + " with meta " + result.getMetadata());
	}
}
