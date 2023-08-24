package com.envyful.better.poke.broadcaster.api.type;

import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public interface BroadcasterType<A extends Event> {

    String id();

    boolean isCorrectEvent(Event e);

    PixelmonEntity getPixelmon(Event e);

    ServerPlayer getNearestPlayer(Event e, PixelmonEntity entity, double range);

    String translateMessage(Event e, String line, PixelmonEntity pixelmon, ServerPlayer nearestPlayer);

}
