package com.envyful.better.poke.broadcaster.api.type.impl.type;

import com.envyful.api.neoforge.world.UtilWorld;
import com.envyful.api.reforged.pixelmon.sprite.UtilSprite;
import com.envyful.api.text.Placeholder;
import com.envyful.better.poke.broadcaster.BetterPokeBroadcaster;
import com.envyful.better.poke.broadcaster.api.type.impl.AbstractBroadcasterType;
import com.envyful.better.poke.broadcaster.api.util.BroadcasterUtil;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.battles.BattleEndCause;
import com.pixelmonmod.pixelmon.api.battles.BattleResults;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.api.util.helpers.BiomeHelper;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.WildPixelmonParticipant;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.Map;
import java.util.Objects;

public class FleeBroadcasterType extends AbstractBroadcasterType<BattleEndEvent> {

    public FleeBroadcasterType() {
        super("flee", BattleEndEvent.class, Pixelmon.EVENT_BUS);
    }

    @Override
    protected boolean isEvent(BattleEndEvent event) {
        PixelmonEntity entity = this.getEntity(event);

        if (entity == null) {
            return false;
        }

        BattleResults result = this.getResult(event, EntityType.PLAYER);

        if (result == null) {
            return false;
        }

        return event.getCause() == BattleEndCause.FLEE;
    }

    public BattleResults getResult(BattleEndEvent event, EntityType<?> entityType) {
        for (Map.Entry<BattleParticipant, BattleResults> entry : event.getResults().entrySet()) {
            if (Objects.equals(entityType, entry.getKey().getEntity().getType())) {
                return entry.getValue();
            }
        }

        return null;
    }

    @Override
    protected PixelmonEntity getEntity(BattleEndEvent event) {
        for (BattleParticipant battleParticipant : event.getResults().keySet()) {
            if (battleParticipant instanceof WildPixelmonParticipant) {
                return (PixelmonEntity)(battleParticipant).getEntity();
            }
        }

        return null;
    }

    @Override
    protected Placeholder asEventPlaceholder(BattleEndEvent event, PixelmonEntity pixelmon, ServerPlayer nearestPlayer) {
        return BetterPokeBroadcaster.getConfig().getPlaceholderFormat().getPokemonPlaceholders(pixelmon.getPokemon(),
                Placeholder.simple(line -> line.replace("%nearest_name%", nearestPlayer == null ? "None" : nearestPlayer.getName().getString())
                .replace("%x%", pixelmon.getX() + "")
                .replace("%y%", pixelmon.getY() + "")
                .replace("%z%", pixelmon.getZ() + "")
                .replace("%world%", UtilWorld.getName(pixelmon.level()) + "")
                .replace("%pokemon%", pixelmon.getPokemonName())
                .replace("%biome%", BiomeHelper.getLocalizedBiomeName(pixelmon.level().getBiome(pixelmon.blockPosition())).getString())));
    }

    @Override
    public ServerPlayer findNearestPlayer(BattleEndEvent event, PixelmonEntity entity, double range) {
        return (ServerPlayer) entity.level().getNearestPlayer(entity, range);
    }

    @SubscribeEvent
    public void onBattleEnd(BattleEndEvent event) {
        BroadcasterUtil.handleEvent(event);
    }
}
