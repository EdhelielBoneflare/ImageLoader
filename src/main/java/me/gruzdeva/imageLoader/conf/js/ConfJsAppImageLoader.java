package me.gruzdeva.imageLoader.conf.js;

import me.gruzdeva.config.js.ConfJsApp;
import me.gruzdeva.config.js.ConfJsDb;
import me.gruzdeva.config.js.ExceptConf;
import me.gruzdeva.config.js.ExceptCJsUnsupported;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ConfJsAppImageLoader extends ConfJsApp {

    private String nameServer;
    private String urlBase;
    private String serverType;
    private String headDirectory;
    public String domain;

    public static final String SERVER_TYPE_DEV = "dev";
    public static final String SERVER_TYPE_TEST = "test";

    public ConfJsAppImageLoader() {
        super(ConfJsDb.knownDb);
    }

    @Override
    protected void initApp(JsonNode p_xParser) throws ExceptCJsUnsupported {
        try {
            // TECHNICAL
            nameServer = getStringRequired(p_xParser, "name");
            urlBase = getStringRequired(p_xParser, "url_base");
            serverType = getStringRequired(p_xParser, "server_type");
            domain = getStringRequired(p_xParser, "domain");
            headDirectory = getStringRequired(p_xParser, "head_directory");
        } catch (RuntimeException ex) {
            throw new ExceptConf("ErrConfA1", "Can't process project configuration",
                    ex.getMessage(), ex);
        }
    }

    public String getDomain() {
        return domain;
    }

    public String getServerType() {
        return serverType;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public String getNameServer() {
        return nameServer;
    }

    public String getHeadDirectory() {
        return headDirectory;
    }

    @Override
    public String toString() {
        return "urlBase=" + urlBase + "\n"
                + "serverType=" + serverType + "\n"
                + "domain=" + domain + "\n"
                + "headDirectory=" + headDirectory + "\n";
    }
}
