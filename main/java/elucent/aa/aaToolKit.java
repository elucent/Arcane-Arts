package elucent.aa;

import javax.vecmath.Vector3d;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class aaToolKit {
	public static int intColor(Vec3i color){
		return (color.getX()*65536 + color.getY()*256 + color.getZ());
	}
	
	public static double dotProduct(Vector3d a, Vector3d b){
		return a.x*b.x + a.y*b.y + a.z*b.z;
	}
	
	public IBlockState solidify(IBlockState state){
		if (state == Blocks.sand.getDefaultState()){
			return Blocks.sandstone.getDefaultState();
		}
		else if (state == Blocks.sandstone.getDefaultState()){
			return Blocks.sandstone.getStateFromMeta(2);
		}
		else if (state == Blocks.cobblestone.getDefaultState()){
			return Blocks.stone.getDefaultState();
		}
		else if (state == Blocks.stone.getStateFromMeta(1)){
			return Blocks.stone.getStateFromMeta(2);
		}
		else if (state == Blocks.stone.getStateFromMeta(3)){
			return Blocks.stone.getStateFromMeta(4);
		}
		else if (state == Blocks.stone.getStateFromMeta(5)){
			return Blocks.stone.getStateFromMeta(6);
		}
		else if (state == Blocks.stonebrick.getStateFromMeta(2)){
			return Blocks.stonebrick.getDefaultState();
		}
		else if (state == Blocks.sand.getStateFromMeta(1)){
			return Blocks.red_sandstone.getDefaultState();
		}
		else if (state == Blocks.red_sandstone.getDefaultState()){
			return Blocks.red_sandstone.getStateFromMeta(2);
		}
		else if (state == Blocks.gravel.getDefaultState()){
			return Blocks.cobblestone.getDefaultState();
		}
		else if (state == Blocks.prismarine.getDefaultState()){
			return Blocks.prismarine.getStateFromMeta(1);
		}
		else if (state == Blocks.snow.getDefaultState()){
			return Blocks.ice.getDefaultState();
		}
		else if (state == Blocks.ice.getDefaultState()){
			return Blocks.packed_ice.getDefaultState();
		}
		return state;
	}
}
