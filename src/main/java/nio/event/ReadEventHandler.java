package nio.event;


import nio.EventHandler;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ReadEventHandler implements EventHandler {


    public void handle(SelectionKey handle) throws Exception {
        System.out.println("===== Read Event Handler =====");

        ByteBuffer inputBuffer = ByteBuffer.allocate(1024);

        SocketChannel socketChannel = (SocketChannel) handle.channel();

        if (socketChannel.read(inputBuffer) > 0) {
            // Read data from client

            inputBuffer.flip();
            // Rewind the buffer to start reading from the beginning

            System.out.println("Received message from client : " + new String(inputBuffer.array(), 0, inputBuffer.limit()));

            // Rewind the buffer to start reading from the beginning
            // Register the interest for writable readiness event for
            // this channel in order to echo back the message

            socketChannel.register(handle.selector(), SelectionKey.OP_WRITE, inputBuffer);
        } else {
            socketChannel.close();
        }



    }
}
