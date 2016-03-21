package elucent.aa;

import javax.vecmath.Vector3d;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3i;
import net.minecraftforge.fml.common.registry.GameData;

public class delocalizedFunctions {
	public static Vector3d getPlayerLookVec(EntityPlayer player){
		return new Vector3d(Math.sin(player.cameraYaw)*Math.cos(player.cameraPitch),Math.sin(player.cameraPitch),Math.cos(player.cameraPitch)*Math.cos(player.cameraYaw));
		
	}
	public static int receptiveToolColor(ItemStack stack, int renderPass){
		if (renderPass == 1 && stack.getItem() == aaItemManager.swordReceptive){
			if (stack.hasTagCompound()){
				if (stack.getTagCompound().getInteger("attunement") == 1){
					return aaElementManager.elementFire.color;
				}
				if (stack.getTagCompound().getInteger("attunement") == 2){
					return aaElementManager.elementEarth.color;
				}
				if (stack.getTagCompound().getInteger("attunement") == 3){
					return aaElementManager.elementWater.color;
				}
				if (stack.getTagCompound().getInteger("attunement") == 4){
					return aaElementManager.elementAir.color;
				}
				if (stack.getTagCompound().getInteger("attunement") == 5){
					return aaElementManager.elementLight.color;
				}
				if (stack.getTagCompound().getInteger("attunement") == 6){
					return aaElementManager.elementVoid.color;
				}
			}
		}
		return 0xFFFFFF;
	}
	
	public static String getRomanNumeralsFor(int i){
		if (i == 1){
			return "I";
		}
		if (i == 2){
			return "II";
		}
		if (i == 3){
			return "III";
		}
		if (i == 4){
			return "IV";
		}
		if (i == 5){
			return "V";
		}
		if (i == 6){
			return "VI";
		}
		if (i == 7){
			return "VII";
		}
		if (i == 8){
			return "VIII";
		}
		if (i == 9){
			return "IX";
		}
		if (i == 10){
			return "X";
		}
		else return "";
	}
	
	public static String potionName(String registryName){
		String name = registryName.substring(10);
		if (name.equalsIgnoreCase("instant_health")){
			name = "Instant Health";
		}
		else if (name.equalsIgnoreCase("instant_damage")){
			name = "Instant Damage";
		}
		else if (name.equalsIgnoreCase("health_boost")){
			name = "Health Boost";
		}
		else if (name.equalsIgnoreCase("mining_fatigue")){
			name = "Mining Fatigue";
		}
		else {
			name = name.substring(0,1).toUpperCase() + name.substring(1);
		}
		return name;
	}
	
	public static String getCorrectnessPhrase(double correctness){
		if (correctness >= 0.99){
			return "Flawless";
		}
		else if (correctness > 0.95){
			return "Precise";
		}
		else if (correctness > 0.9){
			return "Ordinary";
		}
		else if (correctness > 0.85){
			return "Rudimentary";
		}
		else if (correctness > 0.8){
			return "Chipped";
		}
		else if (correctness > 0.75){
			return "Cracked";
		}
		else if (correctness > 0.7){
			return "Broken";
		}
		else {
			return "Nonexistant";
		}
	}
	
	public static int mixElements(double f, double e, double w, double a, double l, double v){
		Vec3i fireColor = aaElementManager.elementFire.vColor;
		Vec3i earthColor = aaElementManager.elementEarth.vColor;
		Vec3i waterColor = aaElementManager.elementWater.vColor;
		Vec3i airColor = aaElementManager.elementAir.vColor;
		Vec3i lightColor = aaElementManager.elementLight.vColor;
		Vec3i voidColor = aaElementManager.elementVoid.vColor;
		
		Vec3i result = new Vec3i((int)(f*fireColor.getX()+e*earthColor.getX()+w*waterColor.getX()+a*airColor.getX()+l*lightColor.getX()+v*voidColor.getX()),
				(int)(f*fireColor.getY()+e*earthColor.getY()+w*waterColor.getY()+a*airColor.getY()+l*lightColor.getY()+v*voidColor.getY()),
				(int)(f*fireColor.getZ()+e*earthColor.getZ()+w*waterColor.getZ()+a*airColor.getZ()+l*lightColor.getZ()+v*voidColor.getZ()));
		return aaToolKit.intColor(result);
	}
}
