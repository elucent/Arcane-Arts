package elucent.aa;

import net.minecraftforge.fml.common.Loader;

public class jeiIntegrationManager {
	Class BlankModPlugin;
	public jeiIntegrationManager(){
		if (Loader.isModLoaded("JEI")){
			try {
				BlankModPlugin = Class.forName("BlankModPlugin");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
