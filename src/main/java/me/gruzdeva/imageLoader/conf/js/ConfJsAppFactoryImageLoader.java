package me.gruzdeva.imageLoader.conf.js;

import me.gruzdeva.config.js.ConfJsApp;
import me.gruzdeva.config.js.ConfJsAppFactory;
import me.gruzdeva.config.js.ConfJsDbFactory_I;

import java.util.HashMap;

public class ConfJsAppFactoryImageLoader extends ConfJsAppFactory {

    private static final ConfJsAppFactoryImageLoader instance = new ConfJsAppFactoryImageLoader();

    public static ConfJsAppFactoryImageLoader getInstance() {
        return instance;
    }

    @Override
    public ConfJsApp newObj(HashMap<String, ConfJsDbFactory_I> factoriesDb) {
        return new ConfJsAppImageLoader();
    }
}
