package com.rabbitmq.manager.config;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.context.annotation.Bean;


public class NettyConfiguration {


    @Bean
    public ChannelGroup ChannelList(){
        return new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }
}