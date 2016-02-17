package elucent.aa;

import net.minecraft.item.ItemStack;

public class aaElementRecipe {
	public ItemStack item;
	public aaElementValue evFire, evWater, evEarth, evAir, evLight, evVoid;
	public aaElementValue itemClass1, itemClass2;
	public aaElementRecipe(ItemStack stack, float fireValue, float earthValue, float waterValue, float airValue, float lightValue, float voidValue){
		evFire = new aaElementValue(aaElementManager.elementFire,fireValue);
		evWater = new aaElementValue(aaElementManager.elementWater,waterValue);
		evEarth = new aaElementValue(aaElementManager.elementEarth,earthValue);
		evLight = new aaElementValue(aaElementManager.elementLight,lightValue);
		evVoid = new aaElementValue(aaElementManager.elementVoid,voidValue);
		evAir = new aaElementValue(aaElementManager.elementAir,airValue);
		itemClass1 = null;
		itemClass2 = null;
		item = stack;
	}
	
	public void setClasses(aaElementValue class1, aaElementValue class2){
		itemClass1 = class1;
		itemClass2 = class2;
	}
}
