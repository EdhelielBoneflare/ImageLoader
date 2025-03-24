package me.gruzdeva.imageLoader.controller;

import me.gruzdeva.Except4SupportDocumented;
import me.gruzdeva.ExceptInfoUser;
import me.gruzdeva.imageLoader.service.ParseHtmlService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class LoadImageController extends ControllerBase {
    public static final String ROUTE_FORM = "/form";
    public static final String ROUTE_DOWNLOAD_IMAGES = "/downloadImages";

    public static final String FORM_PAGE = "form";
    public static final String DOWNLOAD_COMPLETE_PAGE = "downloadComplete";

    private final ParseHtmlService parseHtmlService;

    public LoadImageController(ParseHtmlService parseHtmlService) {
        this.parseHtmlService = parseHtmlService;
    }

    @GetMapping(ROUTE_FORM)
    public String showForm(Model model) {
        model.addAttribute("apiRequestDto", new ApiRequestDto());
        return FORM_PAGE;
    }

    @PostMapping(ROUTE_DOWNLOAD_IMAGES)
    public String loadImages(@ModelAttribute ApiRequestDto requestDto) throws ExceptInfoUser, Except4SupportDocumented {
        parseHtmlService.getPageImages(requestDto.getUrl(), requestDto.getDirectoryName());
        return DOWNLOAD_COMPLETE_PAGE;
    }

}
