# README

## Introduction
This project is a text-searching Java application called `grep`, designed to find specific patterns in text files. It utilizes Core Java features such as file handling, regular expressions, and streams to process files efficiently. The application is packaged using Maven, dockerized for easier distribution, and leverages technologies like Lambda, IDE tools, and Docker for development and deployment.

## Quick Start
To use the app, follow these steps:
1. Clone the repository and navigate to the `core_java/grep` directory.
2. Package the application:
   ```bash
   mvn clean package
   docker build -t rocky/grep .
   docker run --rm -v $(pwd)/data:/data -v $(pwd)/log:/log rocky/grep .*Romeo.*Juliet.* /data /log/grep.out

## Pseudocode

1. Initialize an empty list `matchedLines` to store lines matching the pattern.
2. For each file in the list of files retrieved from the root path:
    - Read all lines from the current file.
    - For each line in the file:
        - Check if the line matches the regex pattern.
        - If the line matches, add it to the `matchedLines` list.
3. Write all lines in `matchedLines` to the output file.

## Performance Issue
The app may encounter memory issues when processing very large files, as it reads them entirely into memory. To mitigate this, the application can be updated to process files line-by-line using buffered readers.

## Deployment

1. Created a Dockerfile to package the application.
2. Built the Docker image using Maven to package the JAR and Docker to containerize it.
3. The image can be run locally or pushed to Docker Hub for global accessibility.