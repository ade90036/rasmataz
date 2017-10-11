package com.rasmataz.network;

import java.util.UUID;

public class NodeIdentifier {

    public static String getIdentifier() {
        return UUID.randomUUID().toString();
    }
}
