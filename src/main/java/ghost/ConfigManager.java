package ghost;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigManager {
    JSONObject config;

    public void readConfig(String configPath) {
        try {
            Object conf = new JSONParser().parse(new FileReader(configPath));
            this.config = (JSONObject) conf;
        } catch (IOException e) {
        // 处理文件找不到的错误
        }catch (ParseException pe){
            // 处理文件内部格式错误
            System.out.println("处理文件内部格式错误");
        }
    }

    public Grid initializeGrid() {
        return new Grid((String) this.config.get("map"));
    }

    public Waka initializeWaka(){
        return new Waka(Math.toIntExact((Long) this.config.get("speed")), Math.toIntExact((Long) this.config.get("lives")));
    }

    public ArrayList<Ghost> initializeGhost() {
        ArrayList<Ghost> arrayList = new ArrayList<>();
        for (int i = 1; i <= 1; i++) {
            arrayList.add(new Ghost((JSONArray) config.get("modeLengths"), Math.toIntExact((Long) this.config.get("speed"))));
        }
        return arrayList;
    }
}
