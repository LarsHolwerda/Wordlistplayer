package nl.holwerda.lars.homeworksayer;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class FileManger {
    public ArrayList<AdapterRecyclerItem> jsonToArrayListObjects(JSONObject jsonData) throws JSONException {
        JSONArray keys = jsonData.names();
        ArrayList<AdapterRecyclerItem> adapterRecyclerItems = new ArrayList<>();

        if (keys != null) {
            for (int i = 0; i < keys.length(); i++) {

                String key = keys.getString (i);
                String value = jsonData.getString (key);

                adapterRecyclerItems.add(new AdapterRecyclerItem(key, value));
            }
        }
        return adapterRecyclerItems;
    }

    public JSONObject ArrayListObjectsToJson(ArrayList<AdapterRecyclerItem> arrayListData) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < arrayListData.size(); i++){
            AdapterRecyclerItem item = arrayListData.get(i);
            jsonObject.put(item.wordFrom,item.wordTo);
        }
        return jsonObject;
    }


    private String changeExtension(String fileName){
        if (!fileName.contains(".json")){
            fileName = fileName + ".json";
        }
        return fileName;
    }
    private Boolean checkFileExistence(File file){
        return file.exists();
    }

    public void deleteFile(Context context, String fileName) throws IOException {
        fileName = changeExtension(fileName);
        File file = new File(context.getFilesDir(), fileName);
        Boolean existence = this.checkFileExistence(file);
        if (!existence) {
            throw new IOException("not exists");
        }
        file.delete();
    }

    public void writeFile(Context context, String fileName, JSONObject jsonData) throws IOException {
        fileName = changeExtension(fileName);
        File file = new File(context.getFilesDir(), fileName);
        Boolean existence = this.checkFileExistence(file);
        if (existence) {
            throw new IOException("already exists");
        }
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(jsonData.toString());
        bufferedWriter.close();
    }
    public JSONObject readFile(Context context, String fileName) throws IOException, JSONException {
        fileName = changeExtension(fileName);
        File file = new File(context.getFilesDir(),fileName);
        Boolean existence = this.checkFileExistence(file);
        if (!existence) {
            throw new IOException("not exists");
        }
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        String response = stringBuilder.toString();
        return new JSONObject(response);
    }

    public void updateFile(Context context, String fileName, JSONObject jsonData) throws IOException, JSONException {
        fileName = changeExtension(fileName);
        JSONObject original_object = this.readFile(context,fileName);
        this.deleteFile(context,fileName);
        JSONObject merged = new JSONObject();
        JSONObject[] array_of_json_objects = new JSONObject[] { original_object, jsonData };
        for (JSONObject obj : array_of_json_objects) {
            Iterator it = obj.keys();
            while (it.hasNext()) {
                String key = (String)it.next();
                merged.put(key, obj.get(key));
            }
        }
        this.writeFile(context,fileName,merged);
    }
}
