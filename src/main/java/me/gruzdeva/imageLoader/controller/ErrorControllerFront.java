package me.gruzdeva.imageLoader.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ErrorControllerFront extends ControllerBase{
    public static final String JSP_ERROR = "error";
    public static final String DEFAULT_URL_ERROR = ROUTE_BASE + JSP_ERROR;

    @GetMapping(DEFAULT_URL_ERROR)
    public ModelAndView error() {
        return new ModelAndView(JSP_ERROR);
    }
}
