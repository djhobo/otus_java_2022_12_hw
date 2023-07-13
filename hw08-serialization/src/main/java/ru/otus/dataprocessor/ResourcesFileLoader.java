package ru.otus.dataprocessor;

import com.google.gson.Gson;
import jakarta.json.Json;
import ru.otus.model.Measurement;

import java.util.List;

public class ResourcesFileLoader implements Loader {
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override   //читает файл, парсит и возвращает результат
    public List<Measurement> load() {
        try (var jsonReader = Json.createReader(this.getClass().getClassLoader().getResourceAsStream(fileName))) {
            return List.of(new Gson().fromJson(jsonReader.readArray().toString(), Measurement[].class));
        } catch (RuntimeException re) {
            throw new FileProcessException(re);
        }
    }
}
