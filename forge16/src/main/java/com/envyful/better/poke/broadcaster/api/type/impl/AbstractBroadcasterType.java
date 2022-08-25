package com.envyful.better.poke.broadcaster.api.type.impl;

import com.envyful.better.poke.broadcaster.api.type.BroadcasterType;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Event;

public abstract class AbstractBroadcasterType<A extends Event> implements BroadcasterType<A> {

    protected final String id;
    protected final Class<A> clazz;

    public AbstractBroadcasterType(String id, Class<A> clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public boolean isCorrectEvent(Event e) {
        if (!e.getClass().isAssignableFrom(this.clazz)) {
            return false;
        }

        return this.isEvent(this.clazz.cast(e));
    }

    protected abstract boolean isEvent(A a);

    @Override
    public PixelmonEntity getPixelmon(Event e) {
        return this.getEntity(this.clazz.cast(e));
    }

    protected abstract PixelmonEntity getEntity(A a);

    @Override
    public String translateMessage(Event e, String line, PixelmonEntity pixelmon, ServerPlayerEntity nearestPlayer) {
        return this.translateEventMessage(this.clazz.cast(e), line, pixelmon, nearestPlayer);
    }

    protected abstract String translateEventMessage(A a, String line, PixelmonEntity pixelmon,  ServerPlayerEntity nearestPlayer);

    @Override
    public ServerPlayerEntity getNearestPlayer(Event event, PixelmonEntity entity, double range) {
        return this.findNearestPlayer(this.clazz.cast(event), entity, range);
    }

    protected abstract ServerPlayerEntity findNearestPlayer(A a, PixelmonEntity entity, double range);
}
