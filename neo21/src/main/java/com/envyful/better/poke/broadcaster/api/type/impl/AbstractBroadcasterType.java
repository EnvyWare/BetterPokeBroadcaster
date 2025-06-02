package com.envyful.better.poke.broadcaster.api.type.impl;

import com.envyful.api.text.Placeholder;
import com.envyful.better.poke.broadcaster.api.type.BroadcasterType;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;

public abstract class AbstractBroadcasterType<A extends Event> implements BroadcasterType {

    protected final String id;
    protected final Class<A> clazz;

    protected AbstractBroadcasterType(String id, Class<A> clazz, IEventBus... eventBus) {
        this.id = id;
        this.clazz = clazz;

        for (var bus : eventBus) {
            bus.register(this);
        }
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isCorrectEvent(Event e) {
        if (!e.getClass().isAssignableFrom(this.clazz)) {
            return false;
        }

        return this.isEvent((A) e);
    }

    protected abstract boolean isEvent(A a);

    @Override
    public PixelmonEntity getPixelmon(Event e) {
        return this.getEntity(this.clazz.cast(e));
    }

    protected abstract PixelmonEntity getEntity(A a);

    @SuppressWarnings("unchecked")
    public Placeholder asPlaceholder(Event e, PixelmonEntity pixelmon, ServerPlayer nearestPlayer) {
        return this.asEventPlaceholder((A) e, pixelmon, nearestPlayer);
    }

    protected abstract Placeholder asEventPlaceholder(A a, PixelmonEntity pixelmon,  ServerPlayer nearestPlayer);

    @Override
    @SuppressWarnings("unchecked")
    public ServerPlayer getNearestPlayer(Event event, PixelmonEntity entity, double range) {
        return this.findNearestPlayer((A) event, entity, range);
    }

    protected abstract ServerPlayer findNearestPlayer(A a, PixelmonEntity entity, double range);
}
