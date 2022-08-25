package com.envyful.better.poke.broadcaster.type.impl;

import com.pixelmonmod.pixelmon.api.battles.BattleResults;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.WildPixelmonParticipant;
import net.minecraft.entity.EntityType;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class DefeatPokemonBroadcasterType extends SimpleBroadcasterType<BattleEndEvent> {

    public DefeatPokemonBroadcasterType() {
        super("defeat", BattleEndEvent.class);
    }

    @Override
    public boolean isEvent(BattleEndEvent battleEndEvent) {
        BattleResults battleResults = this.getResult(battleEndEvent, EntityType.PLAYER).orElse(null);

        if (battleResults == null) {
            return false;
        }

        WildPixelmonParticipant wildPokemon = this.getWildPokemon(battleEndEvent);

        if (wildPokemon == null) {
            return false;
        }

        return battleResults == BattleResults.VICTORY;
    }

    public Optional<BattleResults> getResult(BattleEndEvent event, EntityType<?> entityType) {
        for (Map.Entry<BattleParticipant, BattleResults> entry : event.results.entrySet()) {
            if (Objects.equals(entityType, entry.getKey().getEntity().getType())) {
                return Optional.ofNullable(entry.getValue());
            }
        }

        return Optional.empty();
    }

    private WildPixelmonParticipant getWildPokemon(BattleEndEvent event) {
        for (BattleParticipant battleParticipant : event.results.keySet()) {
            if (battleParticipant instanceof WildPixelmonParticipant) {
                return (WildPixelmonParticipant)battleParticipant;
            }
        }

        return null;
    }
}
