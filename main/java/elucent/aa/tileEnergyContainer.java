package elucent.aa;

import net.minecraft.util.BlockPos;

public interface tileEnergyContainer {
	public double getElementValue(String elementName);
	
	public void setElementValue(String elementName, double value);
	
	public double getMaxCapacity(String elementName);
	
	public void setMaxCapacity(String elementName, double value);

	public boolean isTargetable();
	
	public void setTarget(BlockPos target);
}
