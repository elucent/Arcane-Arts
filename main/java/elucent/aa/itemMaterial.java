package elucent.aa;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class itemMaterial extends Item {
	public itemMaterial(){
		super();
		setRegistryName("itemMaterial");
		setUnlocalizedName("itemMaterial");
		setCreativeTab(arcaneArts.tab);
	}
	
	@Override
	public boolean getHasSubtypes(){
		return true;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack){
		if (stack.getMetadata() == 0){
			return "item.t1Compound";
		}
		else if (stack.getMetadata() == 1){
			return "item.t1Mixture";
		}
		else if (stack.getMetadata() == 2){
			return "item.t1Crystal";
		}
		else if (stack.getMetadata() == 3){
			return "item.t2Compound";
		}
		else if (stack.getMetadata() == 4){
			return "item.t2Mixture";
		}
		else if (stack.getMetadata() == 5){
			return "item.t2Crystal";
		}
		else if (stack.getMetadata() == 6){
			return "item.t3Compound";
		}
		else if (stack.getMetadata() == 7){
			return "item.t3Mixture";
		}
		else if (stack.getMetadata() == 8){
			return "item.t3Crystal";
		}
		else if (stack.getMetadata() == 9){
			return "item.ingotSodium";
		}
		else if (stack.getMetadata() == 10){
			return "item.ingotAntimony";
		}
		else if (stack.getMetadata() == 11){
			return "item.ingotQuicksilver";
		}
		else if (stack.getMetadata() == 12){
			return "item.ingotMagnesium";
		}
		else if (stack.getMetadata() == 13){
			return "item.ingotBismuth";
		}
		else if (stack.getMetadata() == 14){
			return "item.ingotTantalum";
		}
		else if (stack.getMetadata() == 15){
			return "item.ingotReceptive";
		}
		else if (stack.getMetadata() == 16){
			return "item.matterStone";
		}
		else if (stack.getMetadata() == 17){
			return "item.matterMetal";
		}
		else if (stack.getMetadata() == 18){
			return "item.matterGem";
		}
		else if (stack.getMetadata() == 19){
			return "item.matterReactant";
		}
		else if (stack.getMetadata() == 20){
			return "item.matterDry";
		}
		else if (stack.getMetadata() == 21){
			return "item.matterFlesh";
		}
		else if (stack.getMetadata() == 22){
			return "item.matterPlant";
		}
		else if (stack.getMetadata() == 23){
			return "item.matterGel";
		}
		else if (stack.getMetadata() == 24){
			return "item.reducedEphemera";
		}
		else if (stack.getMetadata() == 25){
			return "item.dustIron";
		}
		else if (stack.getMetadata() == 26){
			return "item.dustGold";
		}
		else {
			return "null";
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems){
		subItems.add(new ItemStack(item,1,0));
		subItems.add(new ItemStack(item,1,1));
		subItems.add(new ItemStack(item,1,2));
		subItems.add(new ItemStack(item,1,3));
		subItems.add(new ItemStack(item,1,4));
		subItems.add(new ItemStack(item,1,5));
		subItems.add(new ItemStack(item,1,6));
		subItems.add(new ItemStack(item,1,7));
		subItems.add(new ItemStack(item,1,8));
		subItems.add(new ItemStack(item,1,9));
		subItems.add(new ItemStack(item,1,10));
		subItems.add(new ItemStack(item,1,11));
		subItems.add(new ItemStack(item,1,12));
		subItems.add(new ItemStack(item,1,13));
		subItems.add(new ItemStack(item,1,14));
		subItems.add(new ItemStack(item,1,15));
		subItems.add(new ItemStack(item,1,16));
		subItems.add(new ItemStack(item,1,17));
		subItems.add(new ItemStack(item,1,18));
		subItems.add(new ItemStack(item,1,19));
		subItems.add(new ItemStack(item,1,20));
		subItems.add(new ItemStack(item,1,21));
		subItems.add(new ItemStack(item,1,22));
		subItems.add(new ItemStack(item,1,23));
		subItems.add(new ItemStack(item,1,24));
		subItems.add(new ItemStack(item,1,25));
		subItems.add(new ItemStack(item,1,26));
	}
	
	public static int getCatalystLevel(ItemStack stack){
		if (stack.getItem() instanceof itemMaterial){
			if (stack.getMetadata() == 0){
				return 1;
			}
			if (stack.getMetadata() == 3){
				return 2;
			}
			if (stack.getMetadata() == 6){
				return 3;
			}
		}
		return 0;
	}
	
	public void addTex(String texName, int meta){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(this, meta, new ModelResourceLocation(texName,"inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelBakery.addVariantName(this, 
				"arcanearts:t1Compound",
				"arcanearts:t1Mixture",
				"arcanearts:t1Crystal",
				"arcanearts:t2Compound",
				"arcanearts:t2Mixture",
				"arcanearts:t2Crystal",
				"arcanearts:t3Compound",
				"arcanearts:t3Mixture",
				"arcanearts:t3Crystal",
				"arcanearts:ingotSodium",
				"arcanearts:ingotAntimony",
				"arcanearts:ingotQuicksilver",
				"arcanearts:ingotMagnesium",
				"arcanearts:ingotBismuth",
				"arcanearts:ingotTantalum",
				"arcanearts:ingotReceptive",
				"arcanearts:matterStone",
				"arcanearts:matterMetal",
				"arcanearts:matterGem",
				"arcanearts:matterReactant",
				"arcanearts:matterDry",
				"arcanearts:matterFlesh",
				"arcanearts:matterPlant",
				"arcanearts:matterGel",
				"arcanearts:reducedEphemera",
				"arcanearts:dustIron",
				"arcanearts:dustGold"
		);
		addTex("arcanearts:t1Compound",0);
		addTex("arcanearts:t1Mixture",1);
		addTex("arcanearts:t1Crystal",2);
		addTex("arcanearts:t2Compound",3);
		addTex("arcanearts:t2Mixture",4);
		addTex("arcanearts:t2Crystal",5);
		addTex("arcanearts:t3Compound",6);
		addTex("arcanearts:t3Mixture",7);
		addTex("arcanearts:t3Crystal",8);
		addTex("arcanearts:ingotSodium",9);
		addTex("arcanearts:ingotAntimony",10);
		addTex("arcanearts:ingotQuicksilver",11);
		addTex("arcanearts:ingotMagnesium",12);
		addTex("arcanearts:ingotBismuth",13);
		addTex("arcanearts:ingotTantalum",14);
		addTex("arcanearts:ingotReceptive",15);
		addTex("arcanearts:matterStone",16);
		addTex("arcanearts:matterMetal",17);
		addTex("arcanearts:matterGem",18);
		addTex("arcanearts:matterReactant",19);
		addTex("arcanearts:matterDry",20);
		addTex("arcanearts:matterFlesh",21);
		addTex("arcanearts:matterPlant",22);
		addTex("arcanearts:matterGel",23);
		addTex("arcanearts:reducedEphemera",24);
		addTex("arcanearts:dustIron",25);
		addTex("arcanearts:dustGold",26);
	}
}
