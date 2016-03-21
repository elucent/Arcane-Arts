package elucent.aa;

import javax.vecmath.Vector3d;

import net.minecraft.util.Vec3i;

public class aaToolKit {
	public static int intColor(Vec3i color){
		return (color.getX()*65536 + color.getY()*256 + color.getZ());
	}
	
	public static double dotProduct(Vector3d a, Vector3d b){
		return a.x*b.x + a.y*b.y + a.z*b.z;
	}
}
