package org.itas.core.net.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.ByteOrder;
import java.util.List;
import java.util.Objects;

import net.itas.core.Rule;
import net.itas.core.User;
import net.itas.util.Logger;

//@Sharable
public class MessageDecoder extends ByteToMessageDecoder implements Rule {

	
    public MessageDecoder() {
    	super();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf ioBuf, List<Object> out) throws Exception {
    	if (ioBuf.readableBytes() < 4) {
    		return;
    	}
    	
		short length = ioBuf.order(ByteOrder.LITTLE_ENDIAN).getShort(ioBuf.readerIndex());
		if (length < 4) {
			Logger.warn( "message too small[4]: lenght={}, totalBufferSize={}, userId={}", new Object[]{length,  ioBuf.readableBytes(), getUserInfo(ctx.channel())});
			ctx.channel().close();
			return;
		}

		if (length > max_buffer_size) {
			ctx.channel().close();
			Logger.warn( "message too large[{}]: lenght={}, totalBufferSize={}, userId={}", new Object[]{max_buffer_size, length,  ioBuf.readableBytes(), getUserInfo(ctx.channel())});
			return;
		}
		
		if (length > ioBuf.readableBytes()) {
			return;
		}

		ByteBuf buf = ioBuf.readSlice(length);
		buf.skipBytes(2);
		
		buf = buf.copy().order(ByteOrder.LITTLE_ENDIAN);
		out.add(Message.allocate(buf.readShort(), buf, ctx.channel()));
	}

    private String getUserInfo(Channel channel) {
    	User user = channel.attr(key).get();
    	if (Objects.isNull(user)) {
    		return "userid=0, nickname=null";
    	}
    	
    	return String.format("userid=%s, nickname=%s", user.getId(), user.getName());
    }

}
