package me.gruzdeva.imageLoader.utils;

import me.gruzdeva.Except4SupportDocumented;
import me.gruzdeva.ExceptInfoUser;
import me.gruzdeva.imageLoader.Msg;
import me.gruzdeva.imageLoader.conf.js.ConfJsAppImageLoader;
import me.gruzdeva.imageLoader.conf.js.ConfJsImageLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Validator {

    private static final String FILE_NAME_REGEX = "^[a-zA-Z0-9_]+$";

    public static void validateUrl(String urlString) throws MalformedURLException {
        new URL(urlString);
    }

    public static void validateDirectory(String directoryName) throws Except4SupportDocumented {
        ConfJsAppImageLoader config = ConfJsImageLoader.getInstance().getApp();
        String headDirectory = config.getHeadDirectory();

        File directory = new File(headDirectory, directoryName);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new Except4SupportDocumented("DirAccessErr01", "Directory creation error", "Cannot create directory: " + directoryName);
            }
        } else if (!directory.isDirectory() || !directory.canWrite()) {
            throw new Except4SupportDocumented("DirAccessErr02", "Directory access error", "Cannot access directory: " + directoryName);
        }
    }

    public static void validateDirectoryName(String directoryName) throws ExceptInfoUser {
        if (!directoryName.matches(FILE_NAME_REGEX))    {
            throw new ExceptInfoUser(Msg.i().getMessage(Msg.CODE_INVALID_DIRECTORY_NAME));
        }
    }
}
