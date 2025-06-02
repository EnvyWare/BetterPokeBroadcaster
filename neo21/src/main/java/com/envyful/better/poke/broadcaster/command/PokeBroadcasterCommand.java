package com.envyful.better.poke.broadcaster.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.command.annotate.permission.Permissible;
import com.envyful.api.platform.Messageable;
import com.envyful.better.poke.broadcaster.BetterPokeBroadcaster;

@Command(
        value = {
                "pokebroadcaster",
                "betterpokebroadcaster"
        }
)
@Permissible("better.poke.broadcaster.reload")
public class PokeBroadcasterCommand {

    @CommandProcessor
    public void onCommand(@Sender Messageable<?> source, String[] args) {
        BetterPokeBroadcaster.reloadConfig();
        source.message("Reloaded config");
    }
}
