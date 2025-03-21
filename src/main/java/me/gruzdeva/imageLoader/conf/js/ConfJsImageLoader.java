package me.gruzdeva.imageLoader.conf.js;

import me.gruzdeva.config.js.ConfJs;
import me.gruzdeva.config.js.ExceptCJsNoObject;
import me.gruzdeva.config.js.ExceptConf;
import me.gruzdeva.config.js.ExceptCJsUnsupported;

import java.io.FileNotFoundException;

public class ConfJsImageLoader extends ConfJs {

    public static final String APP_NAME = "image_loader_server";
    public static final ConfJsImageLoader instance = new ConfJsImageLoader();
    public static final String CONF_FILE_NAME = "conf_image_loader_serv.json";

    private ConfJsImageLoader() {
        super(APP_NAME, ConfJsAppFactoryImageLoader.getInstance());
        try {
            load(CONF_FILE_NAME, "../" + CONF_FILE_NAME);
        } catch (FileNotFoundException ex) {
            throw new ExceptConf("ErrConf1", "Can't load project configuration", "Can't find configuration file " + CONF_FILE_NAME, ex);
        } catch (ExceptCJsUnsupported ex) {
            throw new ExceptConf("ErrConf2", "Can't process project configuration", "Cant't parse configuration file " + CONF_FILE_NAME, ex);
        }
    }

    public void updateConf() {
        try {
            load(CONF_FILE_NAME, "../" + CONF_FILE_NAME);
        } catch (FileNotFoundException ex) {
            throw new ExceptConf("ErrConf1", "Can't load project configuration", "Can't find configuration file " + CONF_FILE_NAME, ex);
        } catch (ExceptCJsUnsupported ex) {
            throw new ExceptConf("ErrConf2", "Can't process project configuration", "Cant't parse configuration file " + CONF_FILE_NAME, ex);
        }
    }

    public static ConfJsImageLoader getInstance() {
        return instance;
    }

    public ConfJsAppImageLoader getApp() {
        try {
            return (ConfJsAppImageLoader) super.getApp(APP_NAME);
        } catch (ExceptCJsNoObject ex) {
            throw new ExceptConf("ErrConf3", "Can't process project configuration",
                    String.format("Cant't get app %s in file %s", APP_NAME, CONF_FILE_NAME), ex);
        }
    }
}
