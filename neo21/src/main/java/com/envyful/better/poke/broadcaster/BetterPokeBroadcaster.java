package com.envyful.better.poke.broadcaster;

import com.envyful.api.concurrency.UtilLogger;
import com.envyful.api.config.yaml.YamlConfigFactory;
import com.envyful.api.neoforge.command.ForgeCommandFactory;
import com.envyful.api.neoforge.platform.ForgePlatformHandler;
import com.envyful.api.platform.PlatformProxy;
import com.envyful.better.poke.broadcaster.api.type.BroadcasterTypeRegistry;
import com.envyful.better.poke.broadcaster.command.PokeBroadcasterCommand;
import com.envyful.better.poke.broadcaster.config.BetterPokeBroadcasterConfig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(BetterPokeBroadcaster.MOD_ID)
public class BetterPokeBroadcaster {

    public static final String MOD_ID = "betterpokebroadcaster";

    private static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    private static BetterPokeBroadcaster instance;

    private ForgeCommandFactory commandFactory = new ForgeCommandFactory();

    private BetterPokeBroadcasterConfig config;

    public BetterPokeBroadcaster() {
        PlatformProxy.setHandler(ForgePlatformHandler.getInstance());
        UtilLogger.setLogger(LOGGER);
        instance = this;
        NeoForge.EVENT_BUS.register(this);
        BroadcasterTypeRegistry.init();
    }

    @SubscribeEvent
    public void onServerStart(ServerStartingEvent event) {
        reloadConfig();
    }

    public static void reloadConfig() {
        try {
            instance.config = YamlConfigFactory.getInstance(BetterPokeBroadcasterConfig.class);
        } catch (IOException e) {
            LOGGER.error("Error loading the config", e);
        }
    }

    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event) {
        this.commandFactory.registerCommand(event.getDispatcher(), this.commandFactory.parseCommand(new PokeBroadcasterCommand()));
    }

    public static BetterPokeBroadcasterConfig getConfig() {
        return instance.config;
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
