package com.envyful.better.poke.broadcaster;

import com.envyful.api.config.yaml.YamlConfigFactory;
import com.envyful.api.concurrency.UtilLogger;
import com.envyful.api.forge.command.ForgeCommandFactory;
import com.envyful.better.poke.broadcaster.api.type.BroadcasterTypeRegistry;
import com.envyful.better.poke.broadcaster.command.PokeBroadcasterCommand;
import com.envyful.better.poke.broadcaster.config.BetterPokeBroadcasterConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@Mod(BetterPokeBroadcaster.MOD_ID)
public class BetterPokeBroadcaster {

    public static final String MOD_ID = "betterpokebroadcaster";

    private static BetterPokeBroadcaster instance;

    private ForgeCommandFactory commandFactory = new ForgeCommandFactory();

    private BetterPokeBroadcasterConfig config;
    private Logger logger = LogManager.getLogger(MOD_ID);

    public BetterPokeBroadcaster() {
        UtilLogger.setLogger(logger);
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
        BroadcasterTypeRegistry.init();
    }

    @SubscribeEvent
    public void onServerStart(FMLServerStartingEvent event) {
        this.reloadConfig();
    }

    public void reloadConfig() {
        try {
            this.config = YamlConfigFactory.getInstance(BetterPokeBroadcasterConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event) {
        this.commandFactory.registerCommand(event.getDispatcher(), new PokeBroadcasterCommand());
    }

    public static BetterPokeBroadcaster getInstance() {
        return instance;
    }

    public BetterPokeBroadcasterConfig getConfig() {
        return this.config;
    }
}
