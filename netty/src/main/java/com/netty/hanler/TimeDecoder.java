package com.netty.hanler;

import com.netty.model.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Janus on 2018/11/19.
 */
public class TimeDecoder extends ByteToMessageDecoder { // (1)
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) { // (2)
        System.out.println("进入TimeDecoder.decode方法");
        if (in.readableBytes() < 4) {
            System.out.println("Bytes is not reach 4");
            return; // (3)
        }
//        out.add(in.readBytes(4)); // (4)
        out.add(new UnixTime(in.readUnsignedInt())); // (4)
    }
}
