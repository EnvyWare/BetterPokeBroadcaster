package com.envyful.better.poke.broadcaster.type.impl;

import com.envyful.better.poke.broadcaster.type.BroadcasterType;
import net.minecraftforge.eventbus.api.Event;

import java.util.Objects;

public class SimpleBroadcasterType<A extends Event> implements BroadcasterType<A> {

    private final String id;
    private final Class<A> event;

    public SimpleBroadcasterType(String id, Class<A> event) {
        this.id = id;
        this.event = event;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public boolean isCorrectEvent(Object o) {
        return Objects.equals(this.event, o.getClass());
    }

    @Override
    public boolean isEvent(A a) {
        return true;
    }
}
