package me.jumpinqq.tally.listeners;

import me.jumpinqq.tally.Tally;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GuildMessageReceived extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();
        if (!channel.getName().toLowerCase().contains("counting")) return;

        Message received = event.getMessage();
        String content = received.getContentRaw();

        Member author = event.getMember();
        if (author == null) return;

        User user = author.getUser();
        if (user.equals(Tally.jda.getSelfUser())) return;
        received.delete().queue();

        int num = -1;
        try {
            num = Integer.parseInt(content);
        } catch (Exception ignore) {
        }

        int prev = -1;
        for (Message message : channel.getHistory().retrievePast(3).complete()) {
            List<MessageEmbed> embeds = message.getEmbeds();
            if (message.getAuthor().equals(Tally.jda.getSelfUser()) && embeds.get(0) != null) {
                String description = embeds.get(0).getDescription();
                if (description != null) {
                    prev = Integer.parseInt(description.replace("Current Number: ", ""));
                    break;
                }
            }
        }

        if (num == prev + 1) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setDescription("Current Number: " + num);
            eb.setFooter("Updated by: " + author.getEffectiveName(), author.getUser().getAvatarUrl());
            eb.setColor(5806076);
            channel.sendMessage(eb.build()).queue();
        }
    }
}