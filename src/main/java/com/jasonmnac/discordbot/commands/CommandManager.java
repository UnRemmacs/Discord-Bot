package com.jasonmnac.discordbot.commands;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter
{
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName(); //gets name of command to match it to existing commands
        if(command.equals("welcome")) //command /welcome
        {
            String userTag = event.getUser().getAsTag();
            event.reply("Welcome to the server, **" + userTag + "**!").queue();
        }

        if(command.equals("roles")) //command /roles
        {
            String response = "";
            for(Role role : event.getGuild().getRoles())
            {
                response += role.getAsMention() + "\n";
            }
            event.reply(response).queue();
        }

        if(command.equals("say"))
        {
            event.getOption("message");
            OptionMapping messageOption = event.getOption("message");

            MessageChannel channel;
            OptionMapping channelOption = event.getOption(("channel"));
            if(channelOption != null)
            {
                channel = channelOption.getAsChannel().asGuildMessageChannel();
            }
            else
            {
                channel = event.getChannel();
            }

            String message = messageOption.getAsString();
            channel.sendMessage(message).queue();
            event.reply("Your message was sent!").setEphemeral(true).queue();

        }
    }

    @Override
    public void onGuildReady(GuildReadyEvent event)
    {
        List<CommandData> commandData = new ArrayList<>(); //creates list for commands
        // Command: /welcome
        commandData.add(Commands.slash("welcome", "Get welcomed by the bot."));

        // Command: roles
        commandData.add(Commands.slash("roles", "Display all roles on the server."));
        event.getGuild().updateCommands().addCommands(commandData).queue(); //adds ArrayList of all commands to guild

        // Command: /say <message>
        OptionData option1 = new OptionData(OptionType.STRING, "message", "The message you want the bot to say.", true);
        OptionData option2 = new OptionData(OptionType.CHANNEL, "channel", "The channel you want to send this message in.", false)
                .setChannelTypes(ChannelType.TEXT, ChannelType.NEWS, ChannelType.GUILD_PUBLIC_THREAD);
        commandData.add(Commands.slash("say", "Make the bot say a message.").addOptions(option1, option2));

        event.getGuild().updateCommands().addCommands(commandData).queue();

        //Command: /emote [type]

    }
/*
    @Override
    public void onGuildJoin(GuildJoinEvent event)
    {
        List<CommandData> commandData = new ArrayList<>(); //creates list for commands
        commandData.add(Commands.slash("welcome", "Get welcomed by the bot."));
        event.getGuild().updateCommands().addCommands(commandData).queue(); //adds ArrayList of all commands to guild
    }
 */

}
