package com.dev;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) {
        final String command = args[0];

        if(command == "init"){
            final File root = new File(".git");
            new File(root, "hooks").mkdirs();
            new File(root, "objects").mkdirs();
            new File(root, "refs").mkdirs();
            final File head = new File(root, "HEAD");

            try {
                head.createNewFile();
                Files.write(head.toPath(), "ref: refs/heads/master\n".getBytes());
                System.out.printf("Dépôt Git vide initialisé dans ¨%s.git/", root.getCanonicalPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            System.out.println("Unknown command: " + command);
        }
    }
}