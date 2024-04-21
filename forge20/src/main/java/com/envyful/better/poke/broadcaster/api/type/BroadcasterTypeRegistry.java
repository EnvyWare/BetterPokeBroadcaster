package com.envyful.better.poke.broadcaster.api.type;

import com.envyful.better.poke.broadcaster.api.type.impl.type.CaptureBroadcasterType;
import com.envyful.better.poke.broadcaster.api.type.impl.type.DefeatBroadcasterType;
import com.envyful.better.poke.broadcaster.api.type.impl.type.FleeBroadcasterType;
import com.envyful.better.poke.broadcaster.api.type.impl.type.SpawnBroadcasterType;
import com.google.common.collect.Maps;

import java.util.Locale;
import java.util.Map;

public class BroadcasterTypeRegistry {

    private static final Map<String, BroadcasterType> TYPES = Maps.newHashMap();

    public static void init() {
        register(new SpawnBroadcasterType());
        register(new CaptureBroadcasterType());
        register(new DefeatBroadcasterType());
        register(new FleeBroadcasterType());
    }

    public static void register(BroadcasterType type) {
        TYPES.put(type.id().toLowerCase(Locale.ROOT), type);
    }

    public static BroadcasterType get(String id) {
        return TYPES.get(id.toLowerCase(Locale.ROOT));
    }
}
