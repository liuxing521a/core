package net.itas.core.io.nio;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import net.itas.core.Factory;

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
