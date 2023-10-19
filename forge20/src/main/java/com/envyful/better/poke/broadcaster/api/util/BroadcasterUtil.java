package com.envyful.better.poke.broadcaster.api.util;

import com.envyful.api.concurrency.UtilConcurrency;
import com.envyful.api.discord.DiscordWebHook;
import com.envyful.api.forge.chat.UtilChatColour;
import com.envyful.better.poke.broadcaster.BetterPokeBroadcaster;
import com.envyful.better.poke.broadcaster.api.type.BroadcasterType;
import com.envyful.better.poke.broadcaster.api.type.BroadcasterTypeRegistry;
import com.envyful.better.poke.broadcaster.config.BetterPokeBroadcasterConfig;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.io.IOException;

public class BroadcasterUtil {

    public static void handleEvent(Event event) {
        UtilConcurrency.runAsync(() -> {
            for (BetterPokeBroadcasterConfig.BroadcastOption option : BetterPokeBroadcaster.getInstance().getConfig().getOptions()) {
                BroadcasterType<?> broadcasterType = BroadcasterTypeRegistry.get(option.getType());

                if (broadcasterType == null || !broadcasterType.isCorrectEvent(event)) {
                    continue;
                }

                PixelmonEntity pixelmon = broadcasterType.getPixelmon(event);

                if (pixelmon == null || !option.getSpec().matches(pixelmon)) {
                    continue;
                }

                ServerPlayer nearestPlayer = broadcasterType.getNearestPlayer(event, pixelmon, option.getNearestPlayerRadius());

                if (nearestPlayer == null && option.isNearestPlayerOnly()) {
                    continue;
                }

                for (String broadcast : option.getBroadcasts()) {
                    if (option.isNearestPlayerOnly()) {
                        nearestPlayer.sendSystemMessage(UtilChatColour.colour(broadcasterType.translateMessage(event, broadcast, pixelmon, nearestPlayer)));
                    } else {
                        ServerLifecycleHooks.getCurrentServer().getPlayerList().broadcastSystemMessage(
                                UtilChatColour.colour(broadcasterType.translateMessage(event, broadcast, pixelmon, nearestPlayer)), false
                        );
                    }
                }

                if (option.isWebHookEnabled()) {
                    DiscordWebHook webHook = option.getWebHook(event, nearestPlayer, broadcasterType, pixelmon);

                    if (webHook != null) {
                        try {
                            webHook.execute();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
    }
}
