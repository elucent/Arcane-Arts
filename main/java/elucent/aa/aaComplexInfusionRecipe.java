package elucent.aa;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class aaComplexInfusionRecipe {
	public String name = "";
	public double fireReq, earthReq, waterReq, airReq, lightReq, voidReq, variability = 0;
	public ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
	public ItemStack output;
	public aaComplexInfusionRecipe(String name, int parFire, int parEarth, int parWater, int parAir, int parLight, int parVoid, float variability, ItemStack output){
		Random random = new Random();
		this.name = name;
		fireReq = parFire;
		earthReq = parEarth;
		waterReq = parWater;
		airReq = parAir;
		lightReq = parLight;
		voidReq = parVoid;
		this.variability = variability;
		this.output = output;
	}
	
	public aaComplexInfusionRecipe addIngredient(ItemStack ingredient){
		inputs.add(ingredient);
		return this;
	}
	
	public boolean doesMatch(ArrayList<ItemStack> parInputs, World world){
		ArrayList<ItemStack> tempInputs = (ArrayList<ItemStack>)parInputs.clone();
		if (tempInputs.size() == inputs.size() && inputs.size() > 0){
			for (int i = 0; i < inputs.size(); i ++){
				for (int j = 0; j < tempInputs.size(); j ++){
					if (tempInputs.get(j).getItem() == inputs.get(i).getItem() && tempInputs.get(j).getMetadata() == inputs.get(i).getMetadata()){
						if (tempInputs.get(j).getItem() == aaItemManager.compoundMatter){
							if (tempInputs.get(j).hasTagCompound()){
								NBTTagCompound itag = tempInputs.get(j).getTagCompound();
								if (getCorrectness(itag.getDouble("fire"),itag.getDouble("earth"),itag.getDouble("water"),itag.getDouble("air"),itag.getDouble("light"),itag.getDouble("void"),world) < 0.7){
									return false;
								}
							}
							else {
								return false;
							}
						}
						tempInputs.remove(j);
						break;
					}
				}
			}
			if (tempInputs.size() == 0){
				return true;
			}
		}
		return false;
	}
	
	public double[] getExactRequirements(World world){
		Random random = new Random();
		random.setSeed(world.getSeed());
		double tfireReq = 4.0*Math.round((fireReq + ((fireReq*variability)*2.0*(random.nextFloat()-0.5)))/4.0);
		double tearthReq = 4.0*Math.round((earthReq + ((earthReq*variability)*2.0*(random.nextFloat()-0.5)))/4.0);
		double twaterReq = 4.0*Math.round((waterReq + ((waterReq*variability)*2.0*(random.nextFloat()-0.5)))/4.0);
		double tairReq = 4.0*Math.round((airReq + ((airReq*variability)*2.0*(random.nextFloat()-0.5)))/4.0);
		double tlightReq = 4.0*Math.round((lightReq + ((lightReq*variability)*2.0*(random.nextFloat()-0.5)))/4.0);
		double tvoidReq = 4.0*Math.round((voidReq + ((voidReq*variability)*2.0*(random.nextFloat()-0.5)))/4.0);
		return new double[]{tfireReq,tearthReq,twaterReq,tairReq,tlightReq,tvoidReq};
	}
	
	public double getCorrectness(double parFire, double parEarth, double parWater, double parAir, double parLight, double parVoid, World world){
		Random random = new Random();
		random.setSeed(world.getSeed());
		double tfireReq = 4.0*Math.round((fireReq + ((fireReq*variability)*2.0*(random.nextFloat()-0.5)))/4.0);
		double tearthReq = 4.0*Math.round((earthReq + ((earthReq*variability)*2.0*(random.nextFloat()-0.5)))/4.0);
		double twaterReq = 4.0*Math.round((waterReq + ((waterReq*variability)*2.0*(random.nextFloat()-0.5)))/4.0);
		double tairReq = 4.0*Math.round((airReq + ((airReq*variability)*2.0*(random.nextFloat()-0.5)))/4.0);
		double tlightReq = 4.0*Math.round((lightReq + ((lightReq*variability)*2.0*(random.nextFloat()-0.5)))/4.0);
		double tvoidReq = 4.0*Math.round((voidReq + ((voidReq*variability)*2.0*(random.nextFloat()-0.5)))/4.0);
		double fireAccuracy = 0, earthAccuracy = 0, waterAccuracy = 0, airAccuracy = 0, lightAccuracy = 0, voidAccuracy = 0;
		double totalValues = 0;
		if (fireReq > 0){
			fireAccuracy = 1.0-Math.abs(tfireReq-parFire)/tfireReq;
			totalValues += 1.0;
		}
		if (earthReq > 0){
			earthAccuracy = 1.0-Math.abs(tearthReq-parEarth)/tearthReq;
			totalValues += 1.0;
		}
		if (waterReq > 0){
			waterAccuracy = 1.0-Math.abs(twaterReq-parWater)/twaterReq;
			totalValues += 1.0;
		}
		if (airReq > 0){
			airAccuracy = 1.0-Math.abs(tairReq-parAir)/tairReq;
			totalValues += 1.0;
		}
		if (lightReq > 0){
			lightAccuracy = 1.0-Math.abs(tlightReq-parLight)/tlightReq;
			totalValues += 1.0;
		}
		if (voidReq > 0){
			voidAccuracy = 1.0-Math.abs(tvoidReq-parVoid)/tvoidReq;
			totalValues += 1.0;
		}
		double correctness = (fireAccuracy+earthAccuracy+waterAccuracy+airAccuracy+lightAccuracy+voidAccuracy)/totalValues;
		return correctness;
	}
}
