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
        // �����ļ��Ҳ����Ĵ���
        }catch (ParseException pe){
            // �����ļ��ڲ���ʽ����
            System.out.println("�����ļ��ڲ���ʽ����");
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
