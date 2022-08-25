package com.envyful.better.poke.broadcaster.type;

import com.envyful.better.poke.broadcaster.type.impl.DefeatPokemonBroadcasterType;
import com.envyful.better.poke.broadcaster.type.impl.SimpleBroadcasterType;
import com.google.common.collect.Maps;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;

import java.util.Map;

public class BroadcasterTypeRegistry {

    private static final Map<String, BroadcasterType> TYPES = Maps.newHashMap();

    static {
        register(new SimpleBroadcasterType<>("spawn", SpawnEvent.class));
        register(new SimpleBroadcasterType<>("capture", CaptureEvent.SuccessfulCapture.class));
        register(new DefeatPokemonBroadcasterType());
    }

    public static void register(BroadcasterType<?> type) {
        TYPES.put(type.id().toLowerCase(), type);
    }

    public static BroadcasterType<?> get(String id) {
        return TYPES.get(id.toLowerCase());
    }

}
