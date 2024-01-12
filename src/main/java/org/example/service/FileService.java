package org.example.service;

import org.example.domain.Parameters;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private final int BUFFER_SIZE = 1024;

    private final String[] fileNames;

    private Parameters parameters;

    private final String STRINGS = "strings";
    private final String INTEGERS = "integers";
    private final String DOUBLES = "doubles";

    public FileService(Parameters parameters) {
        this.parameters = parameters;
        fileNames = new String[]{
                parameters.getOptionO() + "/" + parameters.getOptionP() + STRINGS + ".txt",
                parameters.getOptionO() + "/" + parameters.getOptionP() + INTEGERS + ".txt",
                parameters.getOptionO() + "/" + parameters.getOptionP() + DOUBLES + ".txt"
        };
    }

    public void getStatistics() {
        for (String fileName : fileNames) {
            if (parameters.isOptionS()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    int count = 0;
                    while (reader.readLine() != null) {
                        count++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (parameters.isOptionF()) {
                if (fileName.contains(INTEGERS)) {
                    printIntegerStatistic(fileName);
                } else if (fileName.contains(DOUBLES)) {
                    printDoubleStatistic(fileName);
                } else {
                    try (BufferedReader reader = new BufferedReader((new FileReader(fileName)))) {
                        String line;
                        int count = 0;
                        int shortestString = 0;
                        int longestString = 0;
                        while ((line = reader.readLine()) != null) {
                            if (line.length() < shortestString) {
                                shortestString = line.length();
                            }
                            if (line.length() > longestString) {
                                longestString = line.length();
                            }
                            count++;
                        }
                        System.out.println(fileName);
                        System.out.println("Count: " + count);
                        System.out.println("Shortest string: " + shortestString);
                        System.out.println("Longest string: " + longestString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void printDoubleStatistic(String fileName) {
        try (BufferedReader reader = new BufferedReader((new FileReader(fileName)))) {
            double sum = 0;
            int count = 0;
            double max = Double.MIN_VALUE;
            double min = Double.MAX_VALUE;
            double avg = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                double value = Double.parseDouble(line);
                sum += value;
                count++;
                max = Math.max(max, value);
                min = Math.min(min, value);
            }
            avg = sum / count;
            System.out.println(fileName);
            System.out.println("Sum: " + sum);
            System.out.println("Count: " + count);
            System.out.println("Max: " + max);
            System.out.println("Min: " + min);
            System.out.println("Avg: " + avg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printIntegerStatistic(String fileName) {
        try (BufferedReader reader = new BufferedReader((new FileReader(fileName)))) {
            long sum = 0;
            int count = 0;
            long max = Long.MIN_VALUE;
            long min = Long.MAX_VALUE;
            long avg = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                long value = Long.parseLong(line);
                sum += value;
                count++;
                max = Math.max(max, value);
                min = Math.min(min, value);
            }
            avg = sum / count;
            System.out.println(fileName);
            System.out.println("Sum: " + sum);
            System.out.println("Count: " + count);
            System.out.println("Max: " + max);
            System.out.println("Min: " + min);
            System.out.println("Avg: " + avg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFiles() throws IOException {

        List<BufferedReader> readers = getBufferedReaders();

        List<String> listStrings = new ArrayList<>();
        List<String> listLongs = new ArrayList<>();
        List<String> listDoubles = new ArrayList<>();

        createResultFiles();

        while (!readers.isEmpty()) {
            for (int i = 0; i < readers.size(); i++) {
                BufferedReader reader = readers.get(i);
                String line;
                if ((line = reader.readLine()) != null) {
                    if (isLong(line)) {
                        if (checkLimitSizeOfLists(listLongs, BUFFER_SIZE)) {
                            writeToFile(listLongs, INTEGERS + ".txt");
                            listLongs.clear();
                        }
                        listLongs.add(line);
                    } else if (isDouble(line)) {
                        if (checkLimitSizeOfLists(listDoubles, BUFFER_SIZE)) {
                            writeToFile(listDoubles, DOUBLES + ".txt");
                            listDoubles.clear();
                        }
                        listDoubles.add(line);
                    } else {
                        if (checkLimitSizeOfLists(listStrings, BUFFER_SIZE)) {
                            writeToFile(listStrings, STRINGS + ".txt");
                            listStrings.clear();
                        }
                        listStrings.add(line);
                    }
                } else {
                    reader.close();
                    readers.remove(reader);
                }
            }
        }
        writeToFile(listLongs, INTEGERS + ".txt");
        writeToFile(listDoubles, DOUBLES + ".txt");
        writeToFile(listStrings, STRINGS + ".txt");

        deleteEmptyFiles();
    }

    private void deleteEmptyFiles() {
        for (String fileName : fileNames) {
            File file = new File(fileName);
            if (file.length() == 0) {
                file.delete();
            }
        }
    }

    private void writeToFile(List<String> list, String fileName) throws IOException {
        FileWriter writer = new FileWriter(parameters.getOptionO() + "/" + parameters.getOptionP() + fileName, true);
        for (String line : list) {
            writer.write(line + "\n");
        }
        writer.close();
    }

    private void createResultFiles() throws IOException {

        for (String fileName : fileNames) {
            File dir = new File(parameters.getOptionO());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                if (!parameters.isOptionA()) {
                    file.delete();
                    file.createNewFile();
                }
            }
        }
    }

    private boolean checkLimitSizeOfLists(List list, int limit) {
        return list.size() >= limit;
    }

    private List<BufferedReader> getBufferedReaders() throws IOException {
        List<BufferedReader> readers = new ArrayList<>();
        for (String file : parameters.getListFiles()) {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(file);
            if (resource != null) {
                InputStream inputStream = resource.openStream();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(inputStream));
                readers.add(reader);
            }
        }
        return readers;
    }

    private boolean isLong(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDouble(String str) {
        if (str.contains(".") || str.contains(",")) {
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
