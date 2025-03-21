package me.gruzdeva.imageLoader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "me.gruzdeva", exclude = {DataSourceAutoConfiguration.class})
public class ImageLoaderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImageLoaderApplication.class, args);
    }
}
