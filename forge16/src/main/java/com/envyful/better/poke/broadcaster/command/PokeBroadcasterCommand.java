package com.envyful.better.poke.broadcaster.command;

import com.envyful.api.command.annotate.Command;
import com.envyful.api.command.annotate.Permissible;
import com.envyful.api.command.annotate.executor.CommandProcessor;
import com.envyful.api.command.annotate.executor.Sender;
import com.envyful.better.poke.broadcaster.BetterPokeBroadcaster;
import net.minecraft.command.ICommandSource;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;

@Command(
        value = "pokebroadcaster",
        description = "Reloads the config",
        aliases = {
                "betterpokebroadcaster"
        }
)
@Permissible("better.poke.broadcaster.reload")
public class PokeBroadcasterCommand {

    @CommandProcessor
    public void onCommand(@Sender ICommandSource source, String[] args) {
        BetterPokeBroadcaster.getInstance().reloadConfig();
        source.sendMessage(new StringTextComponent("Reloaded config"), Util.NIL_UUID);
    }
}
