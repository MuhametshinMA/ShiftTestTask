package org.example;

import org.example.domain.Parameters;
import org.example.service.FileService;
import org.example.service.ParseParametersServiceImpl;

public class App {
    public static void main(String[] args) {
        Parameters parameters = new Parameters();
        ParseParametersServiceImpl parseParametersService = new ParseParametersServiceImpl(parameters);
        parseParametersService.parse(args);

        FileService fileService = new FileService(parameters);
        try {
            fileService.readFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileService.getStatistics();
    }
}
