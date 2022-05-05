package com.codecool.dungeoncrawl.dao.json;

import com.codecool.dungeoncrawl.model.GameStateModel;
import com.google.gson.Gson;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SaveSlotDaoJson {
    private static final String FILE_TYPE = ".json";

    public void exportToJson(GameStateModel gameStateModel, String filename) {
        try {
            Writer writer = new FileWriter(filename);
            new Gson().toJson(gameStateModel, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameStateModel importJson(String filename){
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(String.format("%s/%s%s", getSaveFolderPath(), filename, FILE_TYPE)));
            GameStateModel gameStateModel = gson.fromJson(reader, GameStateModel.class);
            reader.close();
            return gameStateModel;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void update(GameStateModel gameStateModel, String filename) {
        exportToJson(gameStateModel, filename);
    }

    public List<GameStateModel> getAll() {
        String loaderDir = getSaveFolderPath();
        try {
            try (Stream<Path> paths = Files.walk(Paths.get(loaderDir))) {
                return paths
                        .filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().contains(FILE_TYPE))
                        .map(path -> importJson(path.getFileName().toString().split(FILE_TYPE)[0]))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<String> getAllFileName() {
        String loaderDir = getSaveFolderPath();
        try {
            try (Stream<Path> paths = Files.walk(Paths.get(loaderDir))) {
                return paths
                        .filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().contains(FILE_TYPE))
                        .map(path -> path.getFileName().toString().split(FILE_TYPE)[0])
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getTargetSaveFolderPath() {
        URL urlLoader = SaveSlotDaoJson.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(urlLoader.getPath());
        return urlLoader.getPath() + "save";
    }

    private String getSaveFolderPath() {
        String path = "src/main/resources/save";
        File file = new File(path);
        return file.getAbsolutePath();
    }

}
