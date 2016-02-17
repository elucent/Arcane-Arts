package elucent.aa;

import net.minecraft.util.Vec3i;

import com.sun.javafx.geom.Vec3f;

public class aaElement {
	public String name;
	public int color;
	public aaElement(String name, Vec3i color){
		this.name = name;
		this.color = aaToolKit.intColor(color);
	}
	
	public String getName(){
		return name;
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
			return "gaseous";
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
		return "null";
	}
	
	public boolean getIsItemClass(){
		if (name == "fire" || name == "earth" || name == "water" || name == "air" || name == "light" || name == "void"){
			return false;
		}
		return true;
	}
}
