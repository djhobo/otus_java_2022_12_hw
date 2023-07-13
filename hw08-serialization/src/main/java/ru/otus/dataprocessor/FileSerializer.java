package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.*;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        try (Writer pw = new FileWriter(fileName)) {
            String gson = new Gson().toJson(data);
            pw.write(gson);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
