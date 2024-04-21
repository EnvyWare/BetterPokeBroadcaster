package com.envyful.better.poke.broadcaster.api.type;

import com.envyful.api.text.Placeholder;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public interface BroadcasterType {

    String id();

    boolean isCorrectEvent(Event e);

    PixelmonEntity getPixelmon(Event e);

    ServerPlayer getNearestPlayer(Event e, PixelmonEntity entity, double range);

    Placeholder asPlaceholder(Event e, PixelmonEntity pixelmon, ServerPlayer nearestPlayer);

}
