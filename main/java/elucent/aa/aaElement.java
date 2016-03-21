package elucent.aa;

import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3i;

public class aaElement {
	public String name;
	public int color;
	public Vec3i vColor;
	public aaElement(String name, Vec3i color){
		this.name = name;
		this.vColor = color;
		this.color = aaToolKit.intColor(color);
	}
	
	public String getName(){
		return name;
	}
	
	public DamageSource getDamageSource(){
		if (name == "fire"){
			return DamageSource.inFire;
		}
		else if (name == "earth"){
			return DamageSource.inWall;
		}
		else if (name == "water"){
			return DamageSource.drown;
		}
		else if (name == "air"){
			return DamageSource.fall;
		}
		else if (name == "light"){
			return DamageSource.magic;
		}
		else if (name == "void"){
			return DamageSource.wither;
		}
		else {
			return DamageSource.generic;
		}
	}

	public String getAdjective() {
		if (name == "fire"){
			return "fiery";
		}
		if (name == "earth"){
			return "solid";
		}
		if (name == "water"){
			return "fluid";
		}
		if (name == "air"){
			return "expanding";
		}
		if (name == "light"){
			return "radiant";
		}
		if (name == "void"){
			return "dark";
		}
		if (name == "stone"){
			return "rocky";
		}
		if (name == "metal"){
			return "lustrous";
		}
		if (name == "gem"){
			return "crystalline";
		}
		if (name == "reactant"){
			return "energetic";
		}
		if (name == "bone"){
			return "skeletal";
		}
		if (name == "flesh"){
			return "fleshy";
		}
		if (name == "plant"){
			return "vegetable";
		}
		if (name == "cloth"){
			return "woolly";
		}
		if (name == "raw"){
			return "entropic";
		}
		return "null";
	}
	
	public boolean getIsItemClass(){
		if (name == "fire" || name == "earth" || name == "water" || name == "air" || name == "light" || name == "void" || name == "raw"){
			return false;
		}
		return true;
	}
}
