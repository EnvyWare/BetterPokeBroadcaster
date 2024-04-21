package com.envyful.better.poke.broadcaster.config;

import com.envyful.api.config.data.ScalarSerializers;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.envyful.api.discord.yaml.DiscordWebHookConfig;
import com.envyful.api.reforged.config.PokemonSpecSerializer;
import com.envyful.api.text.Placeholder;
import com.envyful.better.poke.broadcaster.BetterPokeBroadcaster;
import com.pixelmonmod.api.pokemon.PokemonSpecification;
import com.pixelmonmod.api.pokemon.PokemonSpecificationProxy;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

import java.io.IOException;
import java.util.List;

@ConfigSerializable
@ScalarSerializers(PokemonSpecSerializer.class)
public class BroadcastOption extends AbstractYamlConfig {

    @Comment("If this is false then this broadcast option will not be checked. This option is useful for disabling the defaults provided with the mod as you cannot delete those files, only modify them.")
    private boolean enabled = true;

    @Comment("The type of broadcast. This defines when the broadcast will attempt to be sent.")
    private String type;

    @Comment("This is used to check if the Pokemon in the event is the correct Pokemon for this broadcast. For more information read: https://pixelmonmod.com/wiki/Pokemon_spec")
    private PokemonSpecification pokemonSpec;

    @Comment("The radius in blocks that the mod will check for the `nearby player` placeholders and options")
    private double nearestPlayerRadius;

    @Comment("The messages that will be broadcasted when the event is triggered. These messages can contain placeholders.")
    private List<String> broadcasts;

    @Comment("The webhook that will be executed when the event is triggered. This webhook can contain placeholders.")
    private DiscordWebHookConfig webhook;

    @Comment("If this is true then the broadcast will only be sent to the nearest player to the Pokemon that triggered the event.")
    private boolean nearestPlayerOnly;

    private BroadcastOption(Builder builder) {
        super();

        this.type = builder.type;
        this.pokemonSpec = builder.pokemonSpec;
        this.nearestPlayerRadius = builder.nearestPlayerRadius;
        this.broadcasts = builder.broadcasts;
        this.webhook = builder.webhook;
        this.nearestPlayerOnly = builder.nearestPlayerOnly;
    }

    public BroadcastOption() {
        super();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String getType() {
        return this.type;
    }

    public double getNearestPlayerRadius() {
        return this.nearestPlayerRadius;
    }

    public PokemonSpecification getSpec() {
        return this.pokemonSpec;
    }

    public List<String> getBroadcasts() {
        return this.broadcasts;
    }

    public boolean isNearestPlayerOnly() {
        return this.nearestPlayerOnly;
    }

    public void executeWebhook(Placeholder... placeholders) {
        try {
            this.webhook.execute(placeholders);
        } catch (IOException e) {
            BetterPokeBroadcaster.getLogger().error("Failed to execute webhook", e);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String type;
        private PokemonSpecification pokemonSpec;
        private double nearestPlayerRadius;
        private List<String> broadcasts;
        private DiscordWebHookConfig webhook;
        private boolean nearestPlayerOnly;

        private Builder() {}

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder spec(String spec) {
            this.pokemonSpec = PokemonSpecificationProxy.create(spec).get();
            return this;
        }

        public Builder nearestPlayerRadius(double nearestPlayerRadius) {
            this.nearestPlayerRadius = nearestPlayerRadius;
            return this;
        }

        public Builder broadcasts(String... broadcasts) {
            this.broadcasts = List.of(broadcasts);
            return this;
        }

        public Builder webhook(DiscordWebHookConfig webhook) {
            this.webhook = webhook;
            return this;
        }

        public Builder nearestPlayerOnly(boolean nearestPlayerOnly) {
            this.nearestPlayerOnly = nearestPlayerOnly;
            return this;
        }

        public BroadcastOption build() {
            return new BroadcastOption(this);
        }
    }
}
