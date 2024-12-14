package ca.jrvs.apps.grep;
import java.io.File;
import java.util.List;

import java.io.IOException;

public interface JavaGrep {
    /**
     * top level flow
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
