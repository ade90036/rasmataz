package nio;

import java.nio.channels.SelectionKey;

public interface EventHandler {

    void handle(SelectionKey handle) throws Exception;
}
