package com.dev;


import com.dev.utils.Crypto;
import com.dev.utils.Zlib;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.*;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
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
            String option = args[1].trim();
            if (option.equals("-p")) {
                String filename = args[2];
                String path = String.format(".git/objects/%s/%s", filename.substring(0, 2), filename.substring(2));
                File file = new File(path);
                try {
                    String content = new BufferedReader(new InputStreamReader(new FileInputStream(file))).readLine();
                    System.out.print(Arrays.toString(Zlib.decompress(content.getBytes())));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        }
        else {
            System.out.println("Unknown command: " + command);
        }
    }
}