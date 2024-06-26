package com.envyful.better.poke.broadcaster.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.api.command.annotate.permission.Permissible;
import com.envyful.better.poke.broadcaster.BetterPokeBroadcaster;
import net.minecraft.commands.CommandSource;
import net.minecraft.network.chat.Component;

@Command(
        value = {
                "pokebroadcaster",
                "betterpokebroadcaster"
        }
)
@Permissible("better.poke.broadcaster.reload")
public class PokeBroadcasterCommand {

    @CommandProcessor
    public void onCommand(@Sender CommandSource source, String[] args) {
        BetterPokeBroadcaster.reloadConfig();
        source.sendSystemMessage(Component.literal("Reloaded config"));
    }
}
