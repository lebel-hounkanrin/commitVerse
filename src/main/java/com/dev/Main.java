package com.dev;

import com.dev.utils.Command;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        final String command = args[0];
        if(Objects.equals(command, "init")){
            Command.init();
        }
        else if(Objects.equals(command, "cat-file")){
            String option = args[1].trim();
            if (option.equals("-p")) {
                String hash = args[2];
                Command.catFile(hash);
            }
        } else if (Objects.equals(command, "hash-object")) {
            String option = args[1];
            if (option.equals("-w")) {
                String filename = args[2];
                Command.hashObject(filename);
            }
        } else if (Objects.equals(command, "ls-tree")) {
            String hash = args[1];
            Command.lsTree(hash);
        }
        else {
            System.out.println("Unknown command: " + command);
        }
    }
}