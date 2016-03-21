package elucent.aa;

import net.minecraft.item.ItemStack;

public class aaResearchItem {
	public static double medicine, artifice, alchemy, potentia, anima, demonology, theurgy;
	ItemStack stack = null;
	boolean isBlock = false;
	public aaResearchItem(ItemStack stack, boolean block, double med, double art, double pot, double alc, double ani, double dem, double the){
		this.stack = stack;
		isBlock = block;
		medicine = med;
		artifice = art;
		alchemy = alc;
		potentia = pot;
		anima = ani;
		demonology = dem;
		theurgy = the;
	}
}
