package me.gruzdeva.imageLoader.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiRequestDto {

    @NotBlank(message = "URL is mandatory and can't be empty")
    private String url;

    @NotBlank(message = "Directory name is mandatory and can't be empty")
    private String directoryName;

    public ApiRequestDto() {}

    public ApiRequestDto(String url, String directoryName) {
        this.url = url;
        this.directoryName = directoryName;
    }

}
