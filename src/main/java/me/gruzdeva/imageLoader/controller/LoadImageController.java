package me.gruzdeva.imageLoader.controller;

import me.gruzdeva.Except4SupportDocumented;
import me.gruzdeva.ExceptInfoUser;
import me.gruzdeva.imageLoader.service.ParseHtmlService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping()
public class LoadImageController extends ControllerBase {
    public static final String ROUTE_FORM = "/form";
    public static final String ROUTE_DOWNLOAD_IMAGES = "/downloadImages";

    public static final String DOWNLOAD_COMPLETE_PAGE = "downloadComplete";

    private final ParseHtmlService parseHtmlService;

    public LoadImageController(ParseHtmlService parseHtmlService) {
        this.parseHtmlService = parseHtmlService;
    }

    @GetMapping(ROUTE_FORM)
    public ModelAndView showForm(Model model) {
        ModelAndView modelAndView = new ModelAndView("form");
        modelAndView.addObject("apiRequestDto", new ApiRequestDto());
        return modelAndView;
    }

    @PostMapping(ROUTE_DOWNLOAD_IMAGES)
    public ModelAndView loadImages(@ModelAttribute ApiRequestDto requestDto) throws ExceptInfoUser, Except4SupportDocumented {
        parseHtmlService.getPageImages(requestDto.getUrl(), requestDto.getDirectoryName());
        ModelAndView modelAndView = new ModelAndView(DOWNLOAD_COMPLETE_PAGE);
        return modelAndView;
    }

}
