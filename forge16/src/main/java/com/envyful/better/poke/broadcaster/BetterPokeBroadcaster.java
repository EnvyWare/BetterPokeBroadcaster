package com.envyful.better.poke.broadcaster;

import com.envyful.api.config.yaml.YamlConfigFactory;
import com.envyful.api.forge.command.ForgeCommandFactory;
import com.envyful.better.poke.broadcaster.command.PokeBroadcasterCommand;
import com.envyful.better.poke.broadcaster.config.BetterPokeBroadcasterConfig;
import com.envyful.better.poke.broadcaster.listener.PokeSpawnListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

import java.io.IOException;

@Mod(BetterPokeBroadcaster.MOD_ID)
public class BetterPokeBroadcaster {

    public static final String MOD_ID = "betterpokebroadcaster";

    private static BetterPokeBroadcaster instance;

    private ForgeCommandFactory commandFactory = new ForgeCommandFactory();

    private BetterPokeBroadcasterConfig config;

    public BetterPokeBroadcaster() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStart(FMLServerStartingEvent event) {
        this.reloadConfig();

        new PokeSpawnListener();
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
