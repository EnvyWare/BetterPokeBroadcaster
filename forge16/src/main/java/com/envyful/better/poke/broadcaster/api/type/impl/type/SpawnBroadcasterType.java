package com.envyful.better.poke.broadcaster.api.type.impl.type;

import com.envyful.api.forge.world.UtilWorld;
import com.envyful.api.reforged.pixelmon.sprite.UtilSprite;
import com.envyful.api.text.Placeholder;
import com.envyful.better.poke.broadcaster.BetterPokeBroadcaster;
import com.envyful.better.poke.broadcaster.api.type.impl.AbstractBroadcasterType;
import com.envyful.better.poke.broadcaster.api.util.BroadcasterUtil;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.api.util.helpers.BiomeHelper;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SpawnBroadcasterType extends AbstractBroadcasterType<SpawnEvent> {

    public SpawnBroadcasterType() {
        super("spawn", SpawnEvent.class, Pixelmon.EVENT_BUS);
    }

    @Override
    protected boolean isEvent(SpawnEvent spawnEvent) {
        Entity entity = spawnEvent.action.getOrCreateEntity();

        if (!(entity instanceof PixelmonEntity)) {
            return false;
        }

        PixelmonEntity pixelmon = (PixelmonEntity) entity;

        if (pixelmon.getOwner() != null) {
            return false;
        }

        return true;
    }

    @Override
    protected PixelmonEntity getEntity(SpawnEvent spawnEvent) {
        return (PixelmonEntity) spawnEvent.action.getOrCreateEntity();
    }

    @Override
    protected Placeholder asEventPlaceholder(SpawnEvent spawnEvent, PixelmonEntity pixelmon, ServerPlayerEntity nearestPlayer) {
        return Placeholder.simple(line -> UtilSprite.replacePokemonPlaceholders(line.replace("%nearest_name%", nearestPlayer == null ? "None" : nearestPlayer.getName().getString())
                .replace("%x%", pixelmon.getX() + "")
                .replace("%y%", pixelmon.getY() + "")
                .replace("%z%", pixelmon.getZ() + "")
                .replace("%world%", UtilWorld.getName(pixelmon.level))
                .replace("%pokemon%", pixelmon.getPokemonName())
                .replace("%biome%", BiomeHelper.getLocalizedBiomeName(pixelmon.level.getBiome(pixelmon.blockPosition())).getString()),
                pixelmon.getPokemon(), BetterPokeBroadcaster.getConfig().getPlaceholderFormat()));
    }

    @Override
    public ServerPlayerEntity findNearestPlayer(SpawnEvent event, PixelmonEntity entity, double range) {
        return (ServerPlayerEntity) entity.level.getNearestPlayer(event.action.spawnLocation.cause, range);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPixelmonSpawn(SpawnEvent event) {
        BroadcasterUtil.handleEvent(event);
    }
}
