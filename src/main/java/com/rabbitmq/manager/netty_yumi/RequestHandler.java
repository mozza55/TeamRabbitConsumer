package com.rabbitmq.manager.netty_yumi;

import com.rabbitmq.manager.rabbitmq_jieun.MessageConvert;
import com.rabbitmq.manager.vo.QueueMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.Timer;

@Component
@RequiredArgsConstructor
public class RequestHandler {

    //private final MessageHandler messageHandler;
    private final ChannelGroup ChannelList;
    private final ResponseSync responseSync;
    private MessageConvert messageConvert = new MessageConvert();
    Logger logger =  LoggerFactory.getLogger(this.getClass());




    public String request(Message message) throws Exception {
        QueueMessage msg = messageConvert.getQueueMessage(message);
        //초기화 될때까지 기다려야함.. 엠큐가 더 빨리 동작해 ㅠㅠ
        while(ChannelList.isEmpty()){

        }
        logger.info("request to channel: "+msg.toString());
        logger.info("block id :"+msg.getId());
        //String request =msg.toString();
        String request = msg.getId();
        NettyMessage nettyMessage = new NettyMessage((byte)1,(byte)1,request.getBytes().length,request);
        ChannelList.writeAndFlush(nettyMessage);
        String key = msg.getId();
        String value = null;
        try {
            value = responseSync.getResult(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("자원 사용 완료 추출물 : "+value);
        return value;
    }


}


