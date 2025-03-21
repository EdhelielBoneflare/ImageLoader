package me.gruzdeva.imageLoader.service;

import me.gruzdeva.Except4SupportDocumented;
import me.gruzdeva.ExceptInfoUser;
import me.gruzdeva.imageLoader.Msg;
import me.gruzdeva.imageLoader.conf.js.ConfJsAppImageLoader;
import me.gruzdeva.imageLoader.conf.js.ConfJsImageLoader;
import me.gruzdeva.imageLoader.utils.Validator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParseHtmlService {
    private final String IMG_SRC_START = "http";
    private final String IMG_SRC_ATTR = "src";
    private final String IMG_TAG_REGEX = "img[src~=(?i)\\.(png|jpe?g|gif)]";

    private final LoadImageService loadImageService;

    public ParseHtmlService(LoadImageService loadImageService) {
        this.loadImageService = loadImageService;
    }

    public void getPageImages(String url, String directoryName)
            throws Except4SupportDocumented, ExceptInfoUser{
        try {
            Validator.validateUrl(url);
        } catch (MalformedURLException e) {
            throw new ExceptInfoUser(Msg.i().getMessage(Msg.CODE_INVALID_URL));
        }

        String directoryPath = createFullDirectoryPath(directoryName);

        try {
            Document webPage = Jsoup.connect(url).get();
            List<URL> imgURLS = extractImgURLS(webPage, url);
            loadImageService.downloadImages(directoryPath, imgURLS);
        } catch (IOException ex) {
            throw new Except4SupportDocumented("ConnectErr01", "Couldn't fetch the page",
                    "Couldn't fetch HTML from page: " + url + ". " + ex.getMessage());
        }
    }

    private String createFullDirectoryPath(String directoryName) throws Except4SupportDocumented, ExceptInfoUser {
        Validator.validateDirectoryName(directoryName);
        Validator.validateDirectory(directoryName);

        ConfJsAppImageLoader config = ConfJsImageLoader.getInstance().getApp();
        String headDirectory = config.getHeadDirectory();
        return headDirectory + File.separator + directoryName;
    }

    private List<URL> extractImgURLS(Document webPage, String url) throws Except4SupportDocumented {
        List<URL> imgUrls = new ArrayList<>();

        Elements imgElements = webPage.select(IMG_TAG_REGEX);
        for (Element element: imgElements) {
            String imgSrc = element.attr(IMG_SRC_ATTR);
            if (!imgSrc.startsWith(IMG_SRC_START)) {
                imgSrc = url + imgSrc;
            }
            try {
                Validator.validateUrl(imgSrc);
                imgUrls.add(new URL(imgSrc));
            } catch (MalformedURLException ex) {
                throw new Except4SupportDocumented("UrlCreationError01", "Error getting img url",
                        "Created invalid url " + imgSrc + ". " + ex.getMessage());
            }
        }
        return imgUrls;
    }


}
