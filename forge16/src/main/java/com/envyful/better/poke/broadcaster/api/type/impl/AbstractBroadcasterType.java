package com.envyful.better.poke.broadcaster.api.type.impl;

import com.envyful.api.text.Placeholder;
import com.envyful.better.poke.broadcaster.api.type.BroadcasterType;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;

public abstract class AbstractBroadcasterType<A extends Event> implements BroadcasterType {

    protected final String id;
    protected final Class<A> clazz;

    protected AbstractBroadcasterType(String id, Class<A> clazz, IEventBus... eventBus) {
        this.id = id;
        this.clazz = clazz;

        for (IEventBus bus : eventBus) {
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
    public Placeholder asPlaceholder(Event e, PixelmonEntity pixelmon, ServerPlayerEntity nearestPlayer) {
        return this.asEventPlaceholder((A) e, pixelmon, nearestPlayer);
    }

    protected abstract Placeholder asEventPlaceholder(A a, PixelmonEntity pixelmon,  ServerPlayerEntity nearestPlayer);

    @Override
    @SuppressWarnings("unchecked")
    public ServerPlayerEntity getNearestPlayer(Event event, PixelmonEntity entity, double range) {
        return this.findNearestPlayer((A) event, entity, range);
    }

    protected abstract ServerPlayerEntity findNearestPlayer(A a, PixelmonEntity entity, double range);
}
