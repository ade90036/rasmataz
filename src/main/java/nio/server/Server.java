package nio.server;


import nio.event.AcceptEventHandler;
import nio.Reactor;
import nio.event.ReadEventHandler;
import nio.event.WriteEventHandler;
import nio.client.Client;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

public class Server {
    private static final int SERVER_PORT = 7070;

    public void startReactor(int port) throws Exception {

        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(port));

        Reactor reactor = new Reactor();
        reactor.registerChannel(SelectionKey.OP_ACCEPT, server);

        reactor.registerEventHandler(SelectionKey.OP_ACCEPT, new AcceptEventHandler());
        reactor.registerEventHandler(SelectionKey.OP_READ, new ReadEventHandler());
        reactor.registerEventHandler(SelectionKey.OP_WRITE, new WriteEventHandler());

        new Thread(reactor).start(); // Run the dispatcher loop

    }

    public static void main(String[] args) {
        System.out.println("Server Started at port : " + SERVER_PORT);
        try {
            new Server().startReactor(SERVER_PORT);
            Client.main(new String[]{});
            //Client.main(new String[]{});
            //Client.main(new String[]{});

            Thread.sleep(5);
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
