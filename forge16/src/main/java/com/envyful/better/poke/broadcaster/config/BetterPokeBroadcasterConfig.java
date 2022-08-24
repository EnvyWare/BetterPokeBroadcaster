package com.envyful.better.poke.broadcaster.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.envyful.api.discord.DiscordWebHook;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.pixelmonmod.api.pokemon.PokemonSpecification;
import com.pixelmonmod.api.pokemon.PokemonSpecificationProxy;
import org.apache.commons.io.FileUtils;
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
            "one", new BroadcastOption("shiny", 30, "none", Lists.newArrayList(
                    "&8-------",
                    "&a%pokemon% %nearest_name% %x%, %y%, %z%, %world%",
                    "&8-------"
            ))
    );

    public BetterPokeBroadcasterConfig() {
        super();
    }

    public List<BroadcastOption> getOptions() {
        return Lists.newArrayList(broadcastOptions.values());
    }

    @ConfigSerializable
    public static class BroadcastOption {

        private String spec;
        private transient PokemonSpecification pokemonSpec;
        private double nearestPlayerRadius;
        private List<String> broadcasts;
        private String webhook;
        private String readFile = null;

        public BroadcastOption(String spec, double nearestPlayerRadius, String webhook, List<String> broadcasts) {
            this.spec = spec;
            this.nearestPlayerRadius = nearestPlayerRadius;
            this.broadcasts = broadcasts;
        }

        public BroadcastOption() {
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
            return this.webhook.equalsIgnoreCase("none");
        }

        public DiscordWebHook getWebHook() {
            if (this.readFile == null) {
                try {
                    this.readFile = String.join(System.lineSeparator(), Files.readAllLines(Paths.get(this.webhook), StandardCharsets.UTF_8));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return DiscordWebHook.fromJson(this.readFile);
        }
    }
}
