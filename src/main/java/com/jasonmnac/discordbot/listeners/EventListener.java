package com.jasonmnac.discordbot.listeners;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static javax.swing.text.html.HTML.Tag.U;


//sends a message when a user reacts to a message
public class EventListener extends ListenerAdapter
{
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event)
    {
        User user = event.getUser();
        String emoji = event.getReaction().getEmoji().getAsReactionCode();
        Channel channel = event.getChannel();
        String channelMention = event.getChannel().getAsMention();
        String jumpLink = event.getJumpUrl();

        String message = user.getAsTag() + " reacted to a message with " + emoji + " in the " + channelMention + " channel.";
        event.getGuild().getDefaultChannel().asStandardGuildMessageChannel().sendMessage(message).queue();
    }


    //test function: returns "hooray" when a user types "hip hip"
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        String message = event.getMessage().getContentRaw();
        if(message.equalsIgnoreCase("hip hip"))
        {
            event.getChannel().sendMessage("hooray").queue();
        }
    }


    //sends a message when a user joins the server
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event)
    {
        String name = event.getUser().getName();
        String message = name + " joined the server!";
        event.getGuild().getTextChannelsByName("welcome", true).get(0).sendMessage(message).queue();
    }


    //updates the status of a user on the server when they change discord status
    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event)
    {
        List<Member> members = event.getGuild().getMembers();
        int onlineMembers = 0;
        for(Member member: members)
        {
            if(member.getOnlineStatus() == OnlineStatus.ONLINE)
                onlineMembers++;
        }

        //WILL NOT WORK WITHOUT USER CACHE
        User user = event.getUser();
        String message = "**" + user.getAsTag() + "** updated their online status to " + event.getNewOnlineStatus().toString().toLowerCase() + " There are now " + onlineMembers + " users online.";
        event.getGuild().getTextChannelsByName("bot-commands", true).get(0).sendMessage(message).queue();
    }
}
