package net.nexreon.terrafirmamineralogy;

import net.dries007.tfc.util.SelfTests;
import net.neoforged.fml.loading.FMLEnvironment;
import net.nexreon.terrafirmamineralogy.common.items.TFCMItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(TFCM.MODID)
public class TFCM {
    public static final String MODID = "tfcm";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TFCM(
            ModContainer mod,
            IEventBus bus
    ) {
        LOGGER.info("Initializing TerraFirmaMineralogy");
        LOGGER.info("Options: Assertions = {}, Debug = {}, Production = {}, Dist = {}, Self Test = {} (Fatal = {})",
                detectAssertionsEnabled(),
                LOGGER.isDebugEnabled(),
                FMLEnvironment.production,
                FMLEnvironment.dist,
                SelfTests.ENABLED,
                SelfTests.THROW_ON_FAIL);

        SelfTests.runWorldVersionTest();
        TFCMItems.ITEMS.register(bus);

        //mod.registerConfig(ModConfig.Type.CLIENT, TFCMConfig.CLIENT.spec());
        //mod.registerConfig(ModConfig.Type.SERVER, TFCMConfig.SERVER.spec());
        //mod.registerConfig(ModConfig.Type.COMMON, TFCMConfig.COMMON.spec());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }
    @SuppressWarnings({"AssertWithSideEffects", "ConstantConditions"})
    private boolean detectAssertionsEnabled()
    {
        boolean enabled = false;
        assert enabled = true;
        return enabled;
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Hello from TFCM Server Starting!");
    }
}
