package com.envyful.better.poke.broadcaster.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.envyful.api.discord.DiscordWebHook;
import com.envyful.api.reforged.pixelmon.config.SpriteConfig;
import com.envyful.api.reforged.pixelmon.sprite.UtilSprite;
import com.envyful.better.poke.broadcaster.BetterPokeBroadcaster;
import com.envyful.better.poke.broadcaster.api.type.BroadcasterType;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.pixelmonmod.api.pokemon.PokemonSpecification;
import com.pixelmonmod.api.pokemon.PokemonSpecificationProxy;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@ConfigSerializable
@ConfigPath("config/BetterPokeBroadcaster/config.yml")
public class BetterPokeBroadcasterConfig extends AbstractYamlConfig {

    private Map<String, BroadcastOption> broadcastOptions = ImmutableMap.of(
            "one", new BroadcastOption("spawn", "shiny", 30, "none", false, Lists.newArrayList(
                    "&8-------",
                    "&a%pokemon% %nearest_name% %x%, %y%, %z%, %world%",
                    "&8-------"
            )),
                    "two", new BroadcastOption("spawn", "legendary", 30, "none", false, Lists.newArrayList(
                    "&8-------",
                    "&a%pokemon% %nearest_name% %x%, %y%, %z%, %world%",
                    "&8-------")
            ),
            "three", new BroadcastOption("defeat", "legendary", 30, "none", false, Lists.newArrayList(
                    "&8-------",
                    "&a%pokemon% %nearest_name% %x%, %y%, %z%, %world%",
                    "&8-------")
            )
    );

    private SpriteConfig placeholderFormat = new SpriteConfig();

    public BetterPokeBroadcasterConfig() {
        super();
    }

    public List<BroadcastOption> getOptions() {
        return Lists.newArrayList(broadcastOptions.values());
    }

    public SpriteConfig getPlaceholderFormat() {
        return this.placeholderFormat;
    }

    @ConfigSerializable
    public static class BroadcastOption {

        private String type;
        private String spec;
        private transient PokemonSpecification pokemonSpec;
        private double nearestPlayerRadius;
        private List<String> broadcasts;
        private String webhook;
        private String readFile = null;
        private boolean nearestPlayerOnly;

        public BroadcastOption(String type, String spec, double nearestPlayerRadius, String webhook,
                               boolean nearestPlayerOnly, List<String> broadcasts) {
            this.type = type;
            this.spec = spec;
            this.nearestPlayerRadius = nearestPlayerRadius;
            this.broadcasts = broadcasts;
            this.webhook = webhook;
            this.nearestPlayerOnly = nearestPlayerOnly;
        }

        public BroadcastOption() {
        }

        public String getType() {
            return this.type;
        }

        public double getNearestPlayerRadius() {
            return this.nearestPlayerRadius;
        }

        public PokemonSpecification getSpec() {
            if (this.pokemonSpec == null) {
                this.pokemonSpec = PokemonSpecificationProxy.create(this.spec);
            }

            return this.pokemonSpec;
        }

        public List<String> getBroadcasts() {
            return this.broadcasts;
        }

        public boolean isWebHookEnabled() {
            return this.webhook == null || !this.webhook.equalsIgnoreCase("none");
        }

        public boolean isNearestPlayerOnly() {
            return this.nearestPlayerOnly;
        }

        public DiscordWebHook getWebHook(Event event, ServerPlayer nearestPlayer, BroadcasterType<?> type, PixelmonEntity pixelmon) {
            if (this.readFile == null) {
                try {
                    this.readFile = UtilSprite.replacePokemonPlaceholders(String.join(System.lineSeparator(), Files.readAllLines(Paths.get(this.webhook), StandardCharsets.UTF_8)), pixelmon.getPokemon(), BetterPokeBroadcaster.getInstance().getConfig().getPlaceholderFormat());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return DiscordWebHook.fromJson(
                    type.translateMessage(event, this.readFile, pixelmon, nearestPlayer)
            );
        }
    }
}
