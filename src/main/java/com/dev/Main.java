package com.dev;


import com.dev.utils.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.zip.*;

public class Main {
    public static void main(String[] args) throws IOException {
        final String command = args[0];

        if(Objects.equals(command, "init")){
            final File root = new File(".git");
            new File(root, "hooks").mkdirs();
            new File(root, "objects").mkdirs();
            new File(root, "refs").mkdirs();
            final File head = new File(root, "HEAD");

            try {
                head.createNewFile();
                Files.write(head.toPath(), "ref: refs/heads/master\n".getBytes());
                System.out.printf("Dépôt Git vide initialisé dans %s%n", root.getCanonicalPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else if(Objects.equals(command, "cat-file")){
            String filename = args[1];
            String sha1sum = Util.computeFileSHA1(filename);
        }
        else {
            System.out.println("Unknown command: " + command);
        }
    }
}