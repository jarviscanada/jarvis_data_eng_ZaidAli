package ca.jrvs.apps.grep;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

    private String rootPath;
    private String regex;
    private String outFile;

    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();
        for (File file : listFiles(getRootPath())) {
            for (String line : readLines(file)) {
                if (containsPattern(line)) {
                    matchedLines.add(line);
                }
            }
        }
        writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir) {
        List<File> files = new ArrayList<>();
        File dir = new File(rootDir);

        if (dir.isDirectory()) {
            File[] fileArray = dir.listFiles();
            if (fileArray != null) {
                for (File file : fileArray) {
                    files.addAll(listFiles(file.getAbsolutePath()));
                }
            }
        } else if (dir.isFile()) {
            files.add(dir);
        } else {
            throw new IllegalArgumentException("Invalid directory or file: " + rootDir);
        }
        return files;
    }

    @Override
    public List<String> readLines(File inputFile) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + inputFile.getAbsolutePath());
            e.printStackTrace();
        }
        return lines;
    }

    @Override
    public boolean containsPattern(String line) {
        return Pattern.compile(getRegex()).matcher(line).find();
    }

    @Override
    public void writeToFile(List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getOutFile(), true))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + getOutFile());
            e.printStackTrace();
        }
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: JavaGrepImp <regex> <rootPath> <outFile>");
            System.exit(1);
        }

        JavaGrepImp javaGrep = new JavaGrepImp();
        javaGrep.setRegex(args[0]);
        javaGrep.setRootPath(args[1]);
        javaGrep.setOutFile(args[2]);

        try {
            javaGrep.process();
        } catch (Exception e) {
            System.err.println("Error processing files");
            e.printStackTrace();
        }
    }
}