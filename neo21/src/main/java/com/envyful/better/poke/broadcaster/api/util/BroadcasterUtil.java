package com.envyful.better.poke.broadcaster.api.util;

import com.envyful.api.concurrency.UtilConcurrency;
import com.envyful.api.platform.PlatformProxy;
import com.envyful.better.poke.broadcaster.BetterPokeBroadcaster;
import com.envyful.better.poke.broadcaster.api.type.BroadcasterTypeRegistry;
import com.envyful.better.poke.broadcaster.config.BroadcastOption;
import net.neoforged.bus.api.Event;

public class BroadcasterUtil {

    public static void handleEvent(Event event) {
        UtilConcurrency.runAsync(() -> {
            for (var option : BetterPokeBroadcaster.getConfig().getOptions()) {
                if (!option.isEnabled()) {
                    continue;
                }

                var broadcasterType = BroadcasterTypeRegistry.get(option.getType());

                if (broadcasterType == null || !broadcasterType.isCorrectEvent(event)) {
                    continue;
                }

                var pixelmon = broadcasterType.getPixelmon(event);

                if (pixelmon == null || !option.getSpec().matches(pixelmon)) {
                    continue;
                }

                var nearestPlayer = broadcasterType.getNearestPlayer(event, pixelmon, option.getNearestPlayerRadius());

                if (nearestPlayer == null && option.isNearestPlayerOnly()) {
                    continue;
                }

                var placeholder = broadcasterType.asPlaceholder(event, pixelmon, nearestPlayer);

                if (option.isNearestPlayerOnly()) {
                    PlatformProxy.sendMessage(nearestPlayer, option.getBroadcasts(), placeholder);
                } else {
                    PlatformProxy.broadcastMessage(option.getBroadcasts(), placeholder);
                }

                option.executeWebhook(placeholder);
            }
        });
    }
}
