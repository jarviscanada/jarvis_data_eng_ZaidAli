package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {

    /**
     * top level flow
     *
     * @throws java.io.IOException
     */
    void process() throws IOException;

    /**
     *
     * @param rootDir
     * @return
     */
    List<File> listFiles(String rootDir);

    List<String> readLines(File inputFile);

    boolean containsPattern(String line);

    /**
     *
     * @param lines
     */
    void writeToFile(List<String> lines);

    String getRootPath();

    void setRootPath(String rootPath);

    String getRegex();

    void setRegex(String regex);

    String getOutFile();

    void setOutFile(String outFile);
}

//java ca.jrvs.apps.grep.JavaGrepImp "Java" "/home/rocky/dev/jarvis_data_eng_ZaidAli/core_java/grep/src/main/java/ca/jrvs/apps/grep" "/home/rocky/dev/jarvis_data_eng_ZaidAli/core_java/grep/src/main/java/ca/jrvs/apps/grep/output.txt"
