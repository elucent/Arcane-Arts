package elucent.aa;

import net.minecraft.item.ItemStack;

public interface itemVariableResult {
	public double getCorrectness(ItemStack stack);
	
	public ItemStack initFromCorrectness(double correctness);
}
