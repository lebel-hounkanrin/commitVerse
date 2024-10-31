package com.dev.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.InflaterInputStream;

public class Command {
    public static void init() {
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

    public static void catFile(String hash) {
        String path = String.format(".git/objects/%s/%s", hash.substring(0, 2), hash.substring(2));
        File file = new File(path);
        try {
            String _content = new BufferedReader(new InputStreamReader(new InflaterInputStream(new FileInputStream(file)))).readLine();
            String content = _content.substring(_content.indexOf("\0")+1);
            System.out.println(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void hashObject(String filename) {
        try {
            File file = new File(filename);
            long fileSize = file.length();
            List<String> lines = Files.readAllLines(file.toPath());
            String allLines = String.join("\n", lines);

            StringBuilder stringToHash = new StringBuilder();

            stringToHash.append("blob ").append(fileSize).append("\0").append(allLines);

            String hashName = Crypto.computeStringSHA1(stringToHash.toString());
            new File("objects", hashName.substring(0, 2)).mkdirs();
            File content = new File(String.valueOf(Path.of("objects", hashName.substring(0, 2))), hashName.substring(2));
            try {
                content.createNewFile();
                Files.write(content.toPath(), Files.readAllLines(file.toPath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void lsTree(String hash){
        String path = String.format(".git/objects/%s/%s", hash.substring(0, 2), hash.substring(2));
        File file = new File(path);
        ArrayList<String> treeContent = new ArrayList<>();
        try {
            String _content = new BufferedReader(new InputStreamReader(new InflaterInputStream(new FileInputStream(file)))).readLine();
            String content = _content.substring(_content.indexOf("\0")+1);
            String[] splitByMode = content.split("100644|040000|100755|120000|40000");;
            List.of(splitByMode).forEach(splittedString -> {
                treeContent.add(splittedString.split("\0")[0]);
            });
            Collections.sort(treeContent);
            System.out.println(treeContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
