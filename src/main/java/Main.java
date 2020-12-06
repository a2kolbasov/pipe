/*
 * Copyright © 2020 Александр Колбасов
 */

import lombok.val;
import pipe.Processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        val sb = new StringBuilder();
        val programFilePath = Paths.get(args[0]);
        Files.readAllLines(programFilePath).forEach(x -> sb.append(x).append(' '));
        val processor = new Processor(sb.toString());
        processor.run();
    }
}
