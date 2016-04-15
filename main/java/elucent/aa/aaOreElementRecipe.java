package elucent.aa;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class aaOreElementRecipe extends aaElementRecipe {
	public String ore;
	public aaElementValue evFire, evWater, evEarth, evAir, evLight, evVoid;
	public aaElementValue itemClass1, itemClass2;
	public aaOreElementRecipe(String parOre, float fireValue, float earthValue, float waterValue, float airValue, float lightValue, float voidValue){
		super(null, fireValue, earthValue, waterValue, airValue, lightValue, voidValue);
		ore = parOre;
	}
	
	public boolean doesMatch(ItemStack stack){
		return OreDictionary.containsMatch(true, OreDictionary.getOres(ore), stack);
	}
}
