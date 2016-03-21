package elucent.aa;

import elucent.aa.magicRune.runeType;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class diffuserBlock extends Block implements ITileEntityProvider{
	public static final PropertyEnum activated = PropertyEnum.create("activated", diffuserBlock.activatedState.class);
	
	public enum activatedState implements IStringSerializable {
		enumOff(0,"off"),
	    enumOn(1, "on");

	    private int ID;
	    private String name;
	    
	    private activatedState(int ID, String name) {
	        this.ID = ID;
	        this.name = name;
	    }
	    
	    public static activatedState fromMeta(int meta){
	    	switch(meta){
	    		default: return enumOff;
	    		case 0: return enumOff;
	    		case 1: return enumOn;
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
	
	public diffuserBlock(){
		super(Material.rock);
		setUnlocalizedName("diffuserBlock");
		setRegistryName("diffuserBlock");
		setCreativeTab(arcaneArts.tab);	
		this.setHarvestLevel("pickaxe", 1);
		setHardness(2.0f);
		setResistance(30.0f);
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		if (world.getTileEntity(pos) instanceof diffuserEntity){
			((diffuserEntity)(world.getTileEntity(pos))).breakBlock(world, pos, state, player);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ){
		((diffuserEntity)(world.getTileEntity(pos))).activate(world, pos, state, player);
		return true;	
	}

	public void initModel() {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, new ModelResourceLocation("arcanearts:diffuserBlock","inventory"));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new diffuserEntity();
	}
}
