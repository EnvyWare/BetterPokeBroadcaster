package com.envyful.better.poke.broadcaster.api.type;

import com.envyful.better.poke.broadcaster.api.type.impl.type.CaptureBroadcasterType;
import com.envyful.better.poke.broadcaster.api.type.impl.type.DefeatBroadcasterType;
import com.envyful.better.poke.broadcaster.api.type.impl.type.SpawnBroadcasterType;
import com.google.common.collect.Maps;
import com.pixelmonmod.pixelmon.Pixelmon;
import net.minecraftforge.common.MinecraftForge;

import java.util.Map;

public class BroadcasterTypeRegistry {

    private static final Map<String, BroadcasterType<?>> TYPES = Maps.newHashMap();

    static {
        register(new SpawnBroadcasterType());
        register(new CaptureBroadcasterType());
        register(new DefeatBroadcasterType());
    }

    public static void register(BroadcasterType<?> type) {
        TYPES.put(type.id().toLowerCase(), type);

        MinecraftForge.EVENT_BUS.register(type);
        Pixelmon.EVENT_BUS.register(type);
    }

    public static BroadcasterType<?> get(String id) {
        return TYPES.get(id.toLowerCase());
    }

}
