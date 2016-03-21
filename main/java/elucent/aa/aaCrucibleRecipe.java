package elucent.aa;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class aaCrucibleRecipe {
	ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
	ItemStack activator;
	int requiredCatalyst = 0;
	ItemStack result;
	
	public aaCrucibleRecipe(int requiredCatalyst, ItemStack activator, ItemStack result){
		this.requiredCatalyst = requiredCatalyst;
		this.activator = activator;
		this.result = result;
	}
	
	public aaCrucibleRecipe addIngredient(ItemStack ingredient){
		inputs.add(ingredient);
		return this;
	}
	
	public void doResult(World world, BlockPos pos, ItemStack parActivator, crucibleEntity entity){
		if (activator.getItem() == parActivator.getItem() && activator.getMetadata() == parActivator.getMetadata()){
			if (parActivator.getItem() instanceof itemMaterial){
				if (itemMaterial.getCatalystLevel(parActivator) >= requiredCatalyst){
					if (world.isRemote == false){
						world.spawnEntityInWorld(new EntityItem(world,pos.getX()+0.5,pos.getY()+1,pos.getZ()+0.5,new ItemStack(result.getItem(),result.stackSize,result.getItemDamage())));
					}
					entity.renderItems = new ArrayList<ItemStack>();
				}
			}
			if (parActivator.getItem() == aaItemManager.vial && parActivator.getMetadata() == 0){
				ItemStack toSpawn = new ItemStack(result.getItem(),result.stackSize,result.getItemDamage());
				toSpawn.setTagCompound(result.getTagCompound());
				if (world.isRemote == false){
					world.spawnEntityInWorld(new EntityItem(world,pos.getX()+0.5,pos.getY()+1,pos.getZ()+0.5,toSpawn));
				}
				entity.renderItems = new ArrayList<ItemStack>();
			}
		}
	}
	
	public boolean willWork(World world, BlockPos pos, ItemStack parActivator){
		if (activator.getItem() == parActivator.getItem() && activator.getMetadata() == parActivator.getMetadata()){
			if (parActivator.getItem() instanceof itemMaterial){
				if (itemMaterial.getCatalystLevel(parActivator) >= requiredCatalyst){
					return true;
				}
			}
			if (parActivator.getItem() == aaItemManager.vial && parActivator.getMetadata() == 0){
				return true;
			}
		}
		return false;
	}
	
	public boolean doesMatch(ArrayList<ItemStack> parInputs){
		ArrayList<ItemStack> tempInputs = (ArrayList<ItemStack>)parInputs.clone();
		if (tempInputs.size() == inputs.size() && inputs.size() > 0){
			for (int i = 0; i < inputs.size(); i ++){
				boolean endLoop = false;
				for (int j = 0; j < tempInputs.size() && !endLoop; j ++){
					if (tempInputs.get(j).getItem() == inputs.get(i).getItem() && tempInputs.get(j).getMetadata() == inputs.get(i).getMetadata()){
						tempInputs.remove(j);
						endLoop = true;
					}
				}
			}
			if (tempInputs.size() == 0){
				return true;
			}
		}
		return false;
	}
}
