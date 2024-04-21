package com.envyful.better.poke.broadcaster.api.type;

import com.envyful.api.text.Placeholder;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Event;

public interface BroadcasterType {

    String id();

    boolean isCorrectEvent(Event e);

    PixelmonEntity getPixelmon(Event e);

    ServerPlayerEntity getNearestPlayer(Event e, PixelmonEntity entity, double range);

    Placeholder asPlaceholder(Event e, PixelmonEntity pixelmon, ServerPlayerEntity nearestPlayer);

}
