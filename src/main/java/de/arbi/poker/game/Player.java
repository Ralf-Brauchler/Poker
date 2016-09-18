package de.arbi.poker.game;

import com.google.common.base.MoreObjects;
import com.google.common.net.HostAndPort;

public class Player {
    private String name;
    private HostAndPort hostAndPort;

    public Player(String name, HostAndPort hostAndPort) {
        this.name = name;
        this.hostAndPort = hostAndPort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HostAndPort getHostAndPort() {
        return hostAndPort;
    }

    public void setHostAndPort(HostAndPort hostAndPort) {
        this.hostAndPort = hostAndPort;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", name).add("host", hostAndPort.getHostText()).add("port", hostAndPort.getPort()).toString();
    }
}
