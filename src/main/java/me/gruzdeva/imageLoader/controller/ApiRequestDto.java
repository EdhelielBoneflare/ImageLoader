package me.gruzdeva.imageLoader.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ApiRequestDto {

    @NotBlank(message = "URL is mandatory and can't be empty")
    private String url;

    @NotBlank(message = "Directory name is mandatory and can't be empty")
    private String directoryName;

}
