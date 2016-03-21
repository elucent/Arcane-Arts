package elucent.aa;

import net.minecraft.item.ItemStack;

public interface tileElementContainer {
	public double getElementValue(String elementName);
	
	public void setElementValue(String elementName, double value);
	
	public double getMaxCapacity(String elementName);
	
	public void setMaxCapacity(String elementName, double value);
}
