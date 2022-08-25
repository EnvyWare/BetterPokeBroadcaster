package com.envyful.better.poke.broadcaster.api.type;

import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.eventbus.api.Event;

import java.util.Optional;

public interface BroadcasterType<A extends Event> {

    String id();

    boolean isCorrectEvent(Event e);

    PixelmonEntity getPixelmon(Event e);

    ServerPlayerEntity getNearestPlayer(Event e, PixelmonEntity entity, double range);

    String translateMessage(Event e, String line, PixelmonEntity pixelmon, ServerPlayerEntity nearestPlayer);

}
