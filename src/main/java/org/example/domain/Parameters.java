package org.example.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Parameters {

    private String optionO;
    private String optionP;
    private boolean optionA;
    private boolean optionS;
    private boolean optionF;

    private List<String> listFiles = new ArrayList<>();

    public void addFile(String file) {
        listFiles.add(file);
    }
}
