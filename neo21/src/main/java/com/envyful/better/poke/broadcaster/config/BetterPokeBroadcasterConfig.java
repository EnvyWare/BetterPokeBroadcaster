package com.envyful.better.poke.broadcaster.config;

import com.envyful.api.config.data.ConfigPath;
import com.envyful.api.config.yaml.AbstractYamlConfig;
import com.envyful.api.config.yaml.DefaultConfig;
import com.envyful.api.config.yaml.YamlConfigFactory;
import com.envyful.api.discord.yaml.DiscordEmbedConfig;
import com.envyful.api.discord.yaml.DiscordWebHookConfig;
import com.envyful.api.reforged.pixelmon.config.SpriteConfig;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.io.IOException;
import java.util.List;

@ConfigSerializable
@ConfigPath("config/BetterPokeBroadcaster/config.yml")
public class BetterPokeBroadcasterConfig extends AbstractYamlConfig {

    private transient List<BroadcastOption> broadcastOptions;

    private SpriteConfig placeholderFormat = new SpriteConfig();

    public BetterPokeBroadcasterConfig() throws IOException {
        super();

        this.broadcastOptions = YamlConfigFactory.getInstances(BroadcastOption.class, "config/BetterPokeBroadcaster/broadcasts",
                DefaultConfig.onlyNew("examples/shiny_spawns.yml", BroadcastOption.builder()
                                .type("spawn")
                                .nearestPlayerOnly(false)
                                .nearestPlayerRadius(100)
                                .spec("shiny isboss:notboss !leg !ub")
                                .broadcasts(
                                        "&8&m---------------------------------",
                                        " ",
                                        "       &6&lSHINY SPAWN",
                                        "   &e%pokemon% &7has spawned at &a%x%&7, &a%y%&7, &a%z%&7 in &f%biome%&7!",
                                        "   &7The nearest player is &e%nearest_name%&7",
                                        " ",
                                        "&8&m---------------------------------"
                                )
                                .webhook(DiscordWebHookConfig.builder()
                                        .disabled()
                                        .url("YOUR WEBHOOK URL HERE")
                                        .tts(false)
                                        .username("EnvyWare Ltd BetterPokeBroadcaster")
                                        .embeds(DiscordEmbedConfig.builder()
                                                .color(new DiscordEmbedConfig.DiscordColor(255, 255, 0, 255))
                                                .author(new DiscordEmbedConfig.Author("EnvyWare Ltd", "https://envyware.co.uk", ""))
                                                .footer(new DiscordEmbedConfig.Footer("Powered by EnvyWare Ltd", ""))
                                                .description("A shiny %pokemon% has spawned at %x%, %y%, %z% in %biome%!")
                                                .fields(new DiscordEmbedConfig.Field("Nearest Player", "%nearest_name%", false),
                                                        new DiscordEmbedConfig.Field("World", "%world%", false),
                                                        new DiscordEmbedConfig.Field("Biome", "%biome%", false),
                                                        new DiscordEmbedConfig.Field("X", "%x%", false),
                                                        new DiscordEmbedConfig.Field("Y", "%y%", false),
                                                        new DiscordEmbedConfig.Field("Z", "%z%", false))
                                                .build())
                                        .build())
                        .build()
                ),
                DefaultConfig.onlyNew("examples/legendary_spawns.yml", BroadcastOption.builder()
                        .type("spawn")
                        .nearestPlayerOnly(false)
                        .nearestPlayerRadius(100)
                        .spec("legendary")
                        .broadcasts(
                                "&8&m---------------------------------",
                                " ",
                                "       &5&lLEGENDARY SPAWN",
                                "   &d%pokemon% &7has spawned at &a%x%&7, &a%y%&7, &a%z%&7 in &f%biome%&7!",
                                "   &7The nearest player is &d%nearest_name%&7",
                                " ",
                                "&8&m---------------------------------"
                        )
                        .webhook(DiscordWebHookConfig.builder()
                                .disabled()
                                .url("YOUR WEBHOOK URL HERE")
                                .tts(false)
                                .username("EnvyWare Ltd BetterPokeBroadcaster")
                                .embeds(DiscordEmbedConfig.builder()
                                        .color(new DiscordEmbedConfig.DiscordColor(255, 255, 0, 255))
                                        .author(new DiscordEmbedConfig.Author("EnvyWare Ltd", "https://envyware.co.uk", ""))
                                        .footer(new DiscordEmbedConfig.Footer("Powered by EnvyWare Ltd", ""))
                                        .description("A legendary %pokemon% has spawned at %x%, %y%, %z% in %biome%!")
                                        .fields(new DiscordEmbedConfig.Field("Nearest Player", "%nearest_name%", false),
                                                new DiscordEmbedConfig.Field("World", "%world%", false),
                                                new DiscordEmbedConfig.Field("Biome", "%biome%", false),
                                                new DiscordEmbedConfig.Field("X", "%x%", false),
                                                new DiscordEmbedConfig.Field("Y", "%y%", false),
                                                new DiscordEmbedConfig.Field("Z", "%z%", false))
                                        .build())
                                .build())
                        .build()
                ),
                DefaultConfig.onlyNew("examples/legendary_defeat.yml", BroadcastOption.builder()
                        .type("defeat")
                        .nearestPlayerOnly(false)
                        .nearestPlayerRadius(100)
                        .spec("legendary")
                        .broadcasts(
                                "&8&m---------------------------------",
                                " ",
                                "       &4&lLEGENDARY DEFEATED",
                                "   &c%pokemon% &7has been defeated at &a%x%&7, &a%y%&7, &a%z%&7 in &f%biome%&7!",
                                "   &7The player is &d%nearest_name%&7",
                                " ",
                                "&8&m---------------------------------"
                        )
                        .webhook(DiscordWebHookConfig.builder()
                                .disabled()
                                .url("YOUR WEBHOOK URL HERE")
                                .tts(false)
                                .username("EnvyWare Ltd BetterPokeBroadcaster")
                                .embeds(DiscordEmbedConfig.builder()
                                        .color(new DiscordEmbedConfig.DiscordColor(255, 0, 0, 255))
                                        .author(new DiscordEmbedConfig.Author("EnvyWare Ltd", "https://envyware.co.uk", ""))
                                        .footer(new DiscordEmbedConfig.Footer("Powered by EnvyWare Ltd", ""))
                                        .description("A legendary %pokemon% has been defeated at %x%, %y%, %z% in %biome%!")
                                        .fields(new DiscordEmbedConfig.Field("Player", "%nearest_name%", false),
                                                new DiscordEmbedConfig.Field("World", "%world%", false),
                                                new DiscordEmbedConfig.Field("Biome", "%biome%", false),
                                                new DiscordEmbedConfig.Field("X", "%x%", false),
                                                new DiscordEmbedConfig.Field("Y", "%y%", false),
                                                new DiscordEmbedConfig.Field("Z", "%z%", false))
                                        .build())
                                .build())
                        .build()
                ),
                DefaultConfig.onlyNew("examples/legendary_capture.yml", BroadcastOption.builder()
                        .type("capture")
                        .nearestPlayerOnly(false)
                        .nearestPlayerRadius(100)
                        .spec("legendary")
                        .broadcasts(
                                "&8&m---------------------------------",
                                " ",
                                "       &4&lLEGENDARY CAPTURED",
                                "   &c%pokemon% &7has been captured at &a%x%&7, &a%y%&7, &a%z%&7 in &f%biome%&7!",
                                "   &7The player is &d%nearest_name%&7",
                                " ",
                                "&8&m---------------------------------"
                        )
                        .webhook(DiscordWebHookConfig.builder()
                                .disabled()
                                .url("YOUR WEBHOOK URL HERE")
                                .tts(false)
                                .username("EnvyWare Ltd BetterPokeBroadcaster")
                                .embeds(DiscordEmbedConfig.builder()
                                        .color(new DiscordEmbedConfig.DiscordColor(255, 0, 0, 255))
                                        .author(new DiscordEmbedConfig.Author("EnvyWare Ltd", "https://envyware.co.uk", ""))
                                        .footer(new DiscordEmbedConfig.Footer("Powered by EnvyWare Ltd", ""))
                                        .description("A legendary %pokemon% has been captured at %x%, %y%, %z% in %biome%!")
                                        .fields(new DiscordEmbedConfig.Field("Player", "%nearest_name%", false),
                                                new DiscordEmbedConfig.Field("World", "%world%", false),
                                                new DiscordEmbedConfig.Field("Biome", "%biome%", false),
                                                new DiscordEmbedConfig.Field("X", "%x%", false),
                                                new DiscordEmbedConfig.Field("Y", "%y%", false),
                                                new DiscordEmbedConfig.Field("Z", "%z%", false))
                                        .build())
                                .build())
                        .build()
                )
        );
    }

    public List<BroadcastOption> getOptions() {
        return this.broadcastOptions;
    }

    public SpriteConfig getPlaceholderFormat() {
        return this.placeholderFormat;
    }

}
