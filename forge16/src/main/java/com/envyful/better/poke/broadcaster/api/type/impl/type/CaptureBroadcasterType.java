package com.envyful.better.poke.broadcaster.api.type.impl.type;

import com.envyful.api.forge.world.UtilWorld;
import com.envyful.better.poke.broadcaster.api.type.impl.AbstractBroadcasterType;
import com.envyful.better.poke.broadcaster.api.util.BroadcasterUtil;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.api.util.helpers.BiomeHelper;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Locale;

public class CaptureBroadcasterType extends AbstractBroadcasterType<CaptureEvent.SuccessfulCapture> {

    public CaptureBroadcasterType() {
        super("capture", CaptureEvent.SuccessfulCapture.class);
    }

    @Override
    protected boolean isEvent(CaptureEvent.SuccessfulCapture event) {
        return true;
    }

    @Override
    protected PixelmonEntity getEntity(CaptureEvent.SuccessfulCapture event) {
        return event.getPokemon();
    }

    @Override
    protected String translateEventMessage(CaptureEvent.SuccessfulCapture event, String line, PixelmonEntity pixelmon, ServerPlayerEntity nearestPlayer) {
        final int ivs = (int) Math.round(pixelmon.getPokemon().getIVs().getPercentage(2));

        return line.replace("%player%", nearestPlayer.getName().getString())
                .replace("%x%", pixelmon.getX() + "")
                .replace("%y%", pixelmon.getY() + "")
                .replace("%z%", pixelmon.getZ() + "")
                .replace("%world%", UtilWorld.getName(pixelmon.level) + "")
                .replace("%pokemon%", pixelmon.getPokemonName())
                .replace("%species%", pixelmon.getSpecies().getLocalizedName())
                .replace("%species_lower%", pixelmon.getSpecies().getLocalizedName().toLowerCase(Locale.ROOT))
                .replace("%ivs%", Integer.toString(ivs))
                .replace("%biome%", BiomeHelper.getLocalizedBiomeName(pixelmon.level.getBiome(pixelmon.blockPosition())).getString());
    }

    @Override
    protected ServerPlayerEntity findNearestPlayer(CaptureEvent.SuccessfulCapture successfulCapture, PixelmonEntity entity, double range) {
        return successfulCapture.getPlayer();
    }

    @SubscribeEvent
    public void onCapture(CaptureEvent.SuccessfulCapture event) {
        BroadcasterUtil.handleEvent(event);
    }
}
