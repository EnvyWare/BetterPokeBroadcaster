# BetterPokeBroadcaster [![Discord](https://img.shields.io/discord/831966641586831431)](https://discord.gg/7vqgtrjDGw)

Sends chat broadcasts and Discord webhooks when Pokémon spawn, are captured, are defeated, or are fled from.

## Requirements

- Minecraft 1.21.1 (NeoForge)
- Pixelmon 9.4.2

Downloads are on the [releases page](https://github.com/EnvyWare/BetterPokeBroadcaster/releases).

## Config

Each broadcast is its own file in `config/BetterPokeBroadcaster/broadcasts/`. Some examples are generated in `broadcasts/examples/` (shiny spawns, legendary spawns, captures and defeats). Set `enabled: false` to turn one off.

- `type`: `spawn`, `capture`, `defeat` or `flee`
- `pokemon-spec`: which Pokémon it applies to ([Pokémon spec](https://pixelmonmod.com/wiki/Pokemon_spec))
- `broadcasts`: the chat lines to send
- `webhook`: optional Discord webhook
- `nearest-player-only` / `nearest-player-radius`: only send it to the closest player within the radius

Placeholders: `%pokemon%`, `%nearest_name%`, `%x%`, `%y%`, `%z%`, `%world%`, `%biome%`

## Commands

`/pokebroadcaster` reloads the config. Permission: `better.poke.broadcaster.reload`
