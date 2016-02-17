package elucent.aa;

import net.minecraft.util.Vec3i;

public class aaToolKit {
	public static int intColor(Vec3i color){
		return (color.getX()*65536 + color.getY()*256 + color.getZ());
	}
}
