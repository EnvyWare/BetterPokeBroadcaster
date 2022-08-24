package com.envyful.better.poke.broadcaster.listener;

import com.envyful.api.concurrency.UtilConcurrency;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.api.forge.world.UtilWorld;
import com.envyful.better.poke.broadcaster.BetterPokeBroadcaster;
import com.envyful.better.poke.broadcaster.config.BetterPokeBroadcasterConfig;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class PokeSpawnListener {
    public PokeSpawnListener() {
        Pixelmon.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPixelmonSpawn(SpawnEvent event) {
        Entity entity = event.action.getOrCreateEntity();

        if (!(entity instanceof PixelmonEntity)) {
            return;
        }

        UtilConcurrency.runAsync(() -> {
            PixelmonEntity pixelmon = (PixelmonEntity) entity;

            if (pixelmon.getOwner() != null) {
                return;
            }

            for (BetterPokeBroadcasterConfig.BroadcastOption option : BetterPokeBroadcaster.getInstance().getConfig().getOptions()) {
                if (!option.getSpec().matches(pixelmon)) {
                    continue;
                }

                PlayerEntity nearestPlayer = pixelmon.level.getNearestPlayer(pixelmon, option.getNearestPlayerRadius());

                for (String broadcast : option.getBroadcasts()) {
                    ServerLifecycleHooks.getCurrentServer().getPlayerList().broadcastMessage(
                            UtilChatColour.colour(
                                    broadcast
                                            .replace("%nearest_player%", nearestPlayer.getName().getString())
                                            .replace("%x%", pixelmon.getX() + "")
                                            .replace("%y%", pixelmon.getY() + "")
                                            .replace("%z%", pixelmon.getZ() + "")
                                            .replace("%world%", UtilWorld.getName(pixelmon.level) + "")
                            ),
                            ChatType.CHAT,
                            Util.NIL_UUID
                    );
                }
            }
        });
    }
}
