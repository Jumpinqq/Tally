package me.jumpinqq.tally;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import me.jumpinqq.tally.listeners.GuildMessageReceived;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Tally {
    public static JDA jda = null;

    private Tally() throws LoginException {

        // Your discord bot token
        String token = "insert token here";
        jda = JDABuilder.createDefault(token).build();

        CommandClientBuilder builder = new CommandClientBuilder();
        // Command prefix
        builder.setPrefix("+");
        // Sends some help message
        builder.setHelpWord("help word/phrase");
        // Don't worry about it.
        builder.setActivity(Activity.watching("you sleep."));

        // Commands (none atm)
        CommandClient cmd = builder.build();

        // Just some listeners
        jda.addEventListener(cmd);
        jda.addEventListener(new GuildMessageReceived());
    }
    public static void main(String[] args) throws LoginException {
        new Tally();
    }
}
