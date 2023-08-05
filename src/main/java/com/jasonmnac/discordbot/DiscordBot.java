package com.jasonmnac.discordbot;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class DiscordBot
{
    private final ShardManager shardManager;

    public DiscordBot()
    {
        String token = "MTEzNzE1MjcyNDc2OTcxODQwNQ.GTTfdm.hEHBEOac747pCJT_a5lsr9Trs7QoEedPBGSTUI";
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("Fortnite"));
        shardManager = builder.build();
    }

    public ShardManager getShardManager()
    {
        return shardManager;
    }

    public static void main(String[] args)
    {
        try
        {
            DiscordBot bot = new DiscordBot();
        }
        catch (InvalidTokenException e)
        {
            System.out.println("ERROR: Invalid token inputted");
        }
    }
}
