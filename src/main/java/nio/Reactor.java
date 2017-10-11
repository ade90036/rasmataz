package nio;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class Reactor implements Runnable {
    private Map<Integer, EventHandler> registeredHandlers = new ConcurrentHashMap<Integer, EventHandler>();
    private Selector demultiplexer;

    public Reactor() throws Exception {
        demultiplexer = Selector.open();
    }

    public Selector getDemultiplexer() {
        return demultiplexer;
    }

    public void registerEventHandler(
            int eventType, EventHandler eventHandler) {
        registeredHandlers.put(eventType, eventHandler);
    }

    public void registerChannel(
            int eventType, SelectableChannel channel) throws Exception {
        channel.register(demultiplexer, eventType);
    }

    public void run() {
        try {
            while (true) { // Loop indefinitely
                demultiplexer.select();

                Set<SelectionKey> readyHandles =
                        demultiplexer.selectedKeys();
                Iterator<SelectionKey> handleIterator =
                        readyHandles.iterator();

                while (handleIterator.hasNext()) {
                    SelectionKey key = handleIterator.next();
                    handleIterator.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isAcceptable()) {
                        EventHandler handler =
                                registeredHandlers.get(SelectionKey.OP_ACCEPT);
                        handler.handle(key);
                    } else if (key.isReadable()) {
                        EventHandler handler =
                                registeredHandlers.get(SelectionKey.OP_READ);
                        handler.handle(key);
                    } else if (key.isWritable()) {
                        EventHandler handler =
                                registeredHandlers.get(SelectionKey.OP_WRITE);
                        handler.handle(key);
                    }
                }

                Thread.sleep(200);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
