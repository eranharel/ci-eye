package org.netmelody.cieye.server.configuration;

import java.io.IOException;
import java.net.InetAddress;
import java.util.jar.Manifest;

import org.netmelody.cieye.core.logging.LogKeeper;
import org.netmelody.cieye.core.logging.Logbook;
import org.netmelody.cieye.server.CiEyeServerInformationFetcher;

public final class ServerInformation implements CiEyeServerInformationFetcher {

    private static final Logbook LOG = LogKeeper.logbookFor(ServerInformation.class);
    private final String settingsLocation;
    
    public ServerInformation(String settingsLocation) {
        this.settingsLocation = settingsLocation;
    }

    @Override
    public String settingsLocation() {
        try {
            return new StringBuilder()
                            .append(InetAddress.getLocalHost().getHostName())
                            .append(" (").append(InetAddress.getLocalHost().getHostAddress()).append(")")
                            .append(" ")
                            .append(settingsLocation)
                            .toString();
        }
        catch (Exception e) {
            return "";
        }
    }
    
    public String getVersion() {
        String result = "";
        try {
            final Manifest manifest = new Manifest(ServerConfiguration.class.getResourceAsStream("/META-INF/MANIFEST.MF"));
            result = manifest.getMainAttributes().getValue("Implementation-Version");
        } catch (IOException e) {
            LOG.warn("Failed to read Implementation-Version", e);
        }
        return result;
    }
    
}
