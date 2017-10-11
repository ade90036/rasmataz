package nio.event;


import nio.EventHandler;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class WriteEventHandler implements EventHandler {
    public void handle(SelectionKey handle) throws Exception {
        System.out.println("===== Write Event Handler =====");

        SocketChannel socketChannel = (SocketChannel) handle.channel();
        //ByteBuffer bb = ByteBuffer.wrap("Hello Client!\n".getBytes());
        ByteBuffer inputBuffer = (ByteBuffer) handle.attachment();
        socketChannel.write(inputBuffer);
        socketChannel.register(handle.selector(), SelectionKey.OP_READ);


    }
}
