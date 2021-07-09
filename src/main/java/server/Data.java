package server;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private static Data data;

    private Data() {

    }

    public static Data getInstance() {
        if (data == null) {
            data = new Data();
        }
        return data;
    }

    public void readFromJson() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("json.json")));
            if (json.length() > 0) {
                ArrayList<User> users = new YaGson().fromJson(json,
                        new TypeToken<List<User>>() {
                        }.getType()
                );
                for (User user : users) {
                    User.getUsers().add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("can't read from json");
        }
    }

    public void updateJson() {
        try {
            FileWriter writer = new FileWriter("json.json");
            writer.write(new YaGson().toJson(User.getUsers()));
            writer.close();
        } catch (IOException e) {
            System.out.println("can't create or update json");
        }
    }
}
