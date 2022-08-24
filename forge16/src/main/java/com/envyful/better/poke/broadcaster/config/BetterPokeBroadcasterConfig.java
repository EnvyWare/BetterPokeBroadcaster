package com.envyful.better.poke.broadcaster.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.pixelmonmod.api.pokemon.PokemonSpecification;
import com.pixelmonmod.api.pokemon.PokemonSpecificationProxy;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;
import java.util.Map;

@ConfigSerializable
@ConfigPath("config/BetterPokeBroadcaster/config.yml")
public class BetterPokeBroadcasterConfig extends AbstractYamlConfig {

    private Map<String, BroadcastOption> broadcastOptions = ImmutableMap.of(
            "one", new BroadcastOption("shiny", Lists.newArrayList(
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
        private List<String> broadcasts;

        public BroadcastOption(String spec, List<String> broadcasts) {
            this.spec = spec;
            this.broadcasts = broadcasts;
        }

        public BroadcastOption() {
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
    }
}
