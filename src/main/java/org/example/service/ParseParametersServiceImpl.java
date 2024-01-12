package org.example.service;

import org.example.domain.Parameters;

import java.util.Collections;

public class ParseParametersServiceImpl implements ParseParametersService {
    private Parameters parameters;

    public ParseParametersServiceImpl(Parameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public void parse(String[] args) {
        parseArgsToFileList(args);
        parseArgsToParamList(args);
    }

    private void parseArgsToParamList(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '-') {
                if (args[i].equals("-p")) {
                    if ((i + 1) < args.length && args[i + 1].charAt(0) != '-') {
                        if (args[i + 1].charAt(0) != '-') {
                            parameters.setOptionP(args[i + 1]);
                        } else {
                            parameters.setOptionP("default-");
                        }
                    }
                } else if (args[i].equals("-o")) {
                    if (args[i + 1].charAt(0) != '-') {
                        if (args[i + 1].charAt(0) == '/') {
                            parameters.setOptionO(args[i + 1].substring(1));
                        } else {
                            parameters.setOptionO(args[i + 1]);
                        }
                    } else {
                        parameters.setOptionO("");
                    }
                } else if (args[i].equals("-a")) {
                    parameters.setOptionA(true);
                } else if (args[i].equals("-s")) {
                    parameters.setOptionS(true);
                } else if (args[i].equals("-f")) {
                    parameters.setOptionF(true);
                }
            }
        }
    }

    private void parseArgsToFileList(String[] args) {
        for (int i = args.length - 1; i > 0; i--) {
            if (args[i].contains(".txt")) {
                parameters.addFile(args[i]);
            }
        }
        Collections.reverse(parameters.getListFiles());
    }
}
