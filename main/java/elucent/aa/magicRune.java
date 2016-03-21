package elucent.aa;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class magicRune extends Block {
	public static final PropertyEnum type = PropertyEnum.create("type", magicRune.runeType.class);
	
	public enum runeType implements IStringSerializable {
		enumNull(0,"null"),
	    enumSolidity(1, "solidity"),
	    enumFluidity(2, "fluidity"),
	    enumTransformation(3, "transformation"),
	    enumForce(4, "force"),
	    enumRadiance(5, "radiance"),
	    enumEmptiness(6, "emptiness");

	    private int ID;
	    private String name;
	    
	    private runeType(int ID, String name) {
	        this.ID = ID;
	        this.name = name;
	    }
	    
	    public static runeType fromMeta(int meta){
	    	switch(meta){
	    		default: return enumNull;
	    		case 1: return enumSolidity;
	    		case 2: return enumFluidity;
	    		case 3: return enumTransformation;
	    		case 4: return enumForce;
	    		case 5: return enumRadiance;
	    		case 6: return enumEmptiness;
	    	}
	    }
	    
	    @Override
	    public String getName() {
	        return name;
	    }

	    public int getID() {
	        return ID;
	    }
	}
	
	public magicRune(){
		super(Material.ground);
		setUnlocalizedName("magicRune");
		setHardness(0.2f);
		setResistance(20.0f);
		translucent = true;
		setBlockBounds(0, 0, 0, 1, 0.0625f, 1);
	}
	
	@Override
	public BlockState createBlockState(){
		return new BlockState(this, new IProperty[]{type});
	}
	
	@Override
	public int getMetaFromState(IBlockState state){
		runeType TYPE = (runeType)state.getValue(type);
		return TYPE.getID();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta){
		return getDefaultState().withProperty(type,runeType.fromMeta(meta));
	}
	
	@Override
	public boolean canRenderInLayer(EnumWorldBlockLayer layer){
		if (layer == EnumWorldBlockLayer.TRANSLUCENT){
			return true;
		}
		return false;
	}
	
	@Override
    public boolean isFullCube(){
        return false;
    }
	
	@Override
	public boolean isOpaqueCube(){
		return false;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune){
		return null;
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random){
		return 0;
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelBakery.addVariantName(Item.getItemFromBlock(this),"arcanearts:magicRune_1","arcanearts:magicRune_2","arcanearts:magicRune_3","arcanearts:magicRune_4","arcanearts:magicRune_5","arcanearts:magicRune_6");
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, new ModelResourceLocation("arcanearts:magicRune_1","inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 1, new ModelResourceLocation("arcanearts:magicRune_1","inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 2, new ModelResourceLocation("arcanearts:magicRune_2","inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 3, new ModelResourceLocation("arcanearts:magicRune_3","inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 4, new ModelResourceLocation("arcanearts:magicRune_4","inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 5, new ModelResourceLocation("arcanearts:magicRune_5","inventory"));
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 6, new ModelResourceLocation("arcanearts:magicRune_6","inventory"));
	}
}
