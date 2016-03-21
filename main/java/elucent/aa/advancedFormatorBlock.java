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
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class advancedFormatorBlock extends Block implements ITileEntityProvider{
	
	public advancedFormatorBlock(){
		super(Material.rock);
		setUnlocalizedName("advancedFormatorBlock");
		setRegistryName("advancedFormatorBlock");
		setCreativeTab(arcaneArts.tab);	
		setHarvestLevel("pickaxe", 1);
		setHardness(2.0f);
		setResistance(30.0f);
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		if (world.getTileEntity(pos) instanceof advancedFormatorEntity){
			((advancedFormatorEntity)(world.getTileEntity(pos))).breakBlock(world, pos, state, player);
		}
	}
	
    @SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state){
        return getDefaultState();
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

	public void initModel() {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, new ModelResourceLocation("arcanearts:advancedFormatorBlock","inventory"));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new advancedFormatorEntity();
	}
}
