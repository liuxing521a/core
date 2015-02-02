package net.itas.core.io.nio;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NioClientHandler extends SimpleChannelInboundHandler<Message> {

    private static final Logger logger = Logger.getLogger(NioClientHandler.class.getName());

    @Override
    public void messageReceived(ChannelHandlerContext ctx, Message msg) throws Exception {
        System.err.println(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.log(
                Level.WARNING,
                "Unexpected exception from downstream.", cause);
        ctx.close();
    }
}
