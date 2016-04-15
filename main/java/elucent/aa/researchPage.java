package elucent.aa;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class researchPage {
	public ArrayList<String> info = new ArrayList<String>();
	public boolean hasRecipe = false;
	public boolean hasSmelting = false;
	public ItemStack recipeResult = null;
	public ItemStack recipe[][];
	public ItemStack smeltingInput = null;
	
	public researchPage(){
		
	}
	
	public void addText(String text){
		info.add(text);
	}
	
	public ItemStack[][] rotateInputs(ItemStack[][] inputs){
		ItemStack[][] temp = inputs;
		for (int i = 0; i < 3; i ++){
			for (int j = 0; j < 3; j ++){
				temp[i][j] = inputs[j][i];
			}
		}
		return temp;
	}
	
	public void addRecipe(ItemStack result, ItemStack[][] inputs){
		hasRecipe = true;
		recipe = inputs;
		recipeResult = result;
	}
	
	public void addSmelting(ItemStack stack, ItemStack input){
		hasSmelting = true;
		recipeResult = stack;
		smeltingInput = input;
	}
}
