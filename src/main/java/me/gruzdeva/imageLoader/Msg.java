/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.gruzdeva.imageLoader;

import me.gruzdeva.imageLoader.utils.MapMap;
import java.util.HashMap;

/**
 *
 * @author vlitenko
 */
public class Msg {

    public static final String US_en = "US_en";
    public static final String RU_ru = "RU_ru";

    public static final String CODE_INVALID_DIRECTORY_NAME = "INVALID_DIRECTORY_NAME";
    public static final String US_INVALID_DIRECTORY_NAME = "Unsupported directory name. Use 1 or more characters. " +
            "Only latin letters, digits, and _ are allowed";
    private static final String RU_INVALID_DIRECTORY = "Не поддерживаемое имя директории. Используйте 1 или больше " +
            "символов. Разрешены только латинские буквы, цифры и _";

    public static final String CODE_INVALID_URL = "INVALID_URL";
    public static final String US_INVALID_URL = "Unsupported URL";
    private static final String RU_INVALID_URL = "Не поддерживаемый URL";

    private static final HashMap<String, Msg> map = new HashMap<>();
    private static final MapMap<String, String, String> msg = new MapMap<>();   // locale, code, translation

    static {
        map.put(RU_ru, new Msg(RU_ru));
        map.put(US_en, new Msg(US_en));

        HashMap<String, String> messages;
        messages = msg.getOrCreate(RU_ru);
        messages.put(CODE_INVALID_DIRECTORY_NAME, RU_INVALID_DIRECTORY);
        messages.put(CODE_INVALID_URL, RU_INVALID_URL);
    }

    private static Msg instance = map.get(RU_ru);
    private String locale;

    public Msg(String locale) {
        this.locale = locale;
    }

    public static Msg i() {
        return instance;
    }

    public static void changeLocale(String locale) {
        instance = map.get(locale);
        instance.locale = locale;
    }

    public Message4User getMessage(String messageCode) {
        String sRes = msg.get(locale, messageCode);
        return (sRes != null) ? new Message4User(sRes) : new Message4User(messageCode);
    }

}
