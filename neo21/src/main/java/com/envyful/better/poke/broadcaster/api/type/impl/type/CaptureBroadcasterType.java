package com.envyful.better.poke.broadcaster.api.type.impl.type;

import com.envyful.api.text.Placeholder;
import com.envyful.better.poke.broadcaster.BetterPokeBroadcaster;
import com.envyful.better.poke.broadcaster.api.type.impl.AbstractBroadcasterType;
import com.envyful.better.poke.broadcaster.api.util.BroadcasterUtil;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.api.util.helpers.BiomeHelper;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;

public class CaptureBroadcasterType extends AbstractBroadcasterType<CaptureEvent.SuccessfulCapture> {

    public CaptureBroadcasterType() {
        super("capture", CaptureEvent.SuccessfulCapture.class, Pixelmon.EVENT_BUS);
    }

    @Override
    protected boolean isEvent(CaptureEvent.SuccessfulCapture event) {
        return true;
    }

    @Override
    protected PixelmonEntity getEntity(CaptureEvent.SuccessfulCapture event) {
        return event.getPokemon().getEntity();
    }

    @Override
    protected Placeholder asEventPlaceholder(CaptureEvent.SuccessfulCapture event, PixelmonEntity pixelmon, ServerPlayer nearestPlayer) {
        return BetterPokeBroadcaster.getConfig().getPlaceholderFormat().getPokemonPlaceholders(pixelmon.getPokemon(),
                Placeholder.simple(line -> line.replace("%nearest_name%", nearestPlayer == null ? "None" : nearestPlayer.getName().getString())
                        .replace("%x%", pixelmon.getX() + "")
                        .replace("%y%", pixelmon.getY() + "")
                        .replace("%z%", pixelmon.getZ() + "")
                        .replace("%world%", com.envyful.api.neoforge.world.UtilWorld.getName(pixelmon.level()) + "")
                        .replace("%pokemon%", pixelmon.getPokemonName())
                        .replace("%biome%", BiomeHelper.getLocalizedBiomeName(pixelmon.level().getBiome(pixelmon.blockPosition())).getString())));
    }

    @Override
    protected ServerPlayer findNearestPlayer(CaptureEvent.SuccessfulCapture successfulCapture, PixelmonEntity entity, double range) {
        return successfulCapture.getPlayer();
    }

    @SubscribeEvent
    public void onCapture(CaptureEvent.SuccessfulCapture event) {
        BroadcasterUtil.handleEvent(event);
    }
}
