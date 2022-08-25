package com.envyful.better.poke.broadcaster.type;

import net.minecraftforge.eventbus.api.Event;

public interface BroadcasterType<A extends Event> {

    String id();

    boolean isCorrectEvent(Object o);

    boolean isEvent(A a);

}
