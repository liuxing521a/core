package org.itas.core.net.nio.server;

import org.itas.core.Factory;
import org.itas.core.net.nio.MessageDecoder;
import org.itas.core.net.nio.MessageEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

class NioServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //pipeline.addLast("ping", new IdleStateHandler(30, 15, 10, TimeUnit.SECONDS));
        pipeline.addLast("decoder", new MessageDecoder());
        pipeline.addLast("encoder", new MessageEncoder());

        pipeline.addLast("handler", Factory.getInstance(NioServerHandler.class));
    }
}
