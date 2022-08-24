package com.envyful.better.poke.broadcaster;

import com.envyful.api.forge.command.ForgeCommandFactory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod(BetterPokeBroadcaster.MOD_ID)
public class BetterPokeBroadcaster {

    public static final String MOD_ID = "betterpokebroadcaster";

    private static BetterPokeBroadcaster instance;

    private ForgeCommandFactory commandFactory = new ForgeCommandFactory();

    public BetterPokeBroadcaster() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStart(FMLServerStartingEvent event) {

    }

    @SubscribeEvent
    public void onCommandRegister(RegisterCommandsEvent event) {

    }

}
