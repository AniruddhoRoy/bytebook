package com.example.project_7;

import java.io.*;

public class FILEIO {
    // Write object to a file
    public static void writeProcess(Process_Class process, String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(process);
            System.out.println("Process written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read object from a file
    public static Process_Class readProcess(String path) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return (Process_Class) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
