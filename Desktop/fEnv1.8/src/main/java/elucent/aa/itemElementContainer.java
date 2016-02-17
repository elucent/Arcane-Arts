package elucent.aa;

import net.minecraft.item.ItemStack;

public interface itemElementContainer {
	public double getElementValue(ItemStack stack, String elementName);
	
	public void setElementValue(ItemStack stack, String elementName, double value);
	
	public double getMaxCapacity(ItemStack stack, String elementName);
	
	public void setMaxCapacity(ItemStack stack, String elementName, double value);
}
