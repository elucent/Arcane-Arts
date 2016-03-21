package elucent.aa;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class arcaneForgeBlock extends Block implements ITileEntityProvider {
	public arcaneForgeBlock(){
		super(Material.rock);
		setUnlocalizedName("arcaneForgeBlock");
		setRegistryName("arcaneForgeBlock");
		setCreativeTab(arcaneArts.tab);	
		setHardness(2.0f);
		setHarvestLevel("pickaxe", 1);
		setResistance(30.0f);
		setBlockBounds(0, 0, 0, 1, 1, 1);
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
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		if (world.getTileEntity(pos) instanceof arcaneForgeEntity){
			((arcaneForgeEntity)(world.getTileEntity(pos))).breakBlock(world, pos, state, player);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ){
		((arcaneForgeEntity)(world.getTileEntity(pos))).activate(world, pos, state, player);
		return true;	
	}

	public void initModel() {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(this), 0, new ModelResourceLocation("arcanearts:arcaneForgeBlock","inventory"));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new arcaneForgeEntity();
	}
}
