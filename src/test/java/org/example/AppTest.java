package org.example;

import org.example.domain.Parameters;
import org.example.service.ParseParametersService;
import org.example.service.ParseParametersServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    Parameters parameters;
    ParseParametersService parseParametersService;

    @BeforeEach
    public void init() {
        parameters = new Parameters();
        parseParametersService = new ParseParametersServiceImpl(parameters);
    }

    @Test
    public void test1() {
        String[] args = new String[]{
                "-s",
                "-a",
                "-p",
                "sample-",
                "in1.txt",
                "in2.txt"
        };

        parseParametersService.parse(args);
        assertEquals(parameters.getOptionO(), null);
        assertEquals(parameters.getOptionP(), "sample-");
        assertEquals(parameters.isOptionA(), true);
        assertEquals(parameters.isOptionS(), true);
        assertEquals(parameters.isOptionF(), false);
        assertEquals(parameters.getListFiles().size(), 2);
        assertEquals(parameters.getListFiles().get(0), "in1.txt");
        assertEquals(parameters.getListFiles().get(1), "in2.txt");
    }

    @Test
    public void test2() {
        String[] args = new String[]{
                "-s",
                "-a",
                "-p",
                "sample-",
                "-o",
                "/some/path",
                "in1.txt",
                "in2.txt"
        };
        parseParametersService.parse(args);
        assertEquals(parameters.getOptionO(), "some/path");
        assertEquals(parameters.getOptionP(), "sample-");
        assertEquals(parameters.isOptionA(), true);
        assertEquals(parameters.isOptionS(), true);
        assertEquals(parameters.isOptionF(), false);
        assertEquals(parameters.getListFiles().size(), 2);
        assertEquals(parameters.getListFiles().get(0), "in1.txt");
        assertEquals(parameters.getListFiles().get(1), "in2.txt");
    }
}
