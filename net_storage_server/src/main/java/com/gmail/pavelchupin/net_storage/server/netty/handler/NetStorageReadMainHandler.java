package com.gmail.pavelchupin.net_storage.server.netty.handler;

import com.gmail.pavelchupin.net_storage.common.ObjectSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NetStorageReadMainHandler extends ChannelInboundHandlerAdapter {
    //    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        System.out.println("Received and released");
//        ((ByteBuf) msg).release();
//    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        //List<Byte> b = new ArrayList<>();

        //ObjectInput objIn = new ObjectDecoderInputStream(new ByteArrayInputStream());

        //Производим десериализацию объекта
        try {
            while (in.isReadable()) {
                //b.add(in.readByte());
                System.out.print((char) in.readByte());
            }
        } finally {
            in.release();
        }

       /*byte[] g = new byte[b.size()];
        for (int i = 0; i < b.size(); i++) {
            g[i] = b.get(i);
        }*/

        /*try(ObjectInput objIn = new ObjectInputStream(new ByteArrayInputStream(g));) {
            ObjectSerialization obj = (ObjectSerialization) objIn.readObject();
            System.out.println(objIn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/



    }


}
