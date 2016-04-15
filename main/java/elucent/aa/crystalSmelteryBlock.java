package elucent.aa;

import elucent.aa.magicRune.runeType;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class crystalSmelteryBlock extends Block implements ITileEntityProvider{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	   
	public crystalSmelteryBlock(){
		super(Material.rock);
		setUnlocalizedName("crystalSmelteryBlock");
		setRegistryName("crystalSmelteryBlock");
		setCreativeTab(arcaneArts.tab);	
		setHarvestLevel("pickaxe", 1);
		setHardness(2.0f);
		setResistance(30.0f);
		setBlockBounds(0, 0, 0, 1, 1, 1);
        setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	@Override
    public int getMetaFromState(IBlockState state){
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
	
	@Override
    protected BlockState createBlockState(){
        return new BlockState(this, new IProperty[] {FACING});
    }
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		if (world.getTileEntity(pos) instanceof crystalSmelteryEntity){
			((crystalSmelteryEntity)(world.getTileEntity(pos))).breakBlock(world, pos, state, player);
		}
	}
	
	@Override
	public boolean canRenderInLayer(EnumWorldBlockLayer layer){
		if (layer == EnumWorldBlockLayer.SOLID){
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
    public boolean isFullCube(){
        return true;
    }
	
	@Override
	public boolean isOpaqueCube(){
		return true;
	}
	
	@Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

	public void initModel() {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, new ModelResourceLocation("arcanearts:crystalSmelteryBlock","inventory"));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new crystalSmelteryEntity();
	}
}
