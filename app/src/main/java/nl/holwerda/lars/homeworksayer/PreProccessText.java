package nl.holwerda.lars.homeworksayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class PreProccessText extends AppCompatActivity {
    ArrayList<String> l1;
    ArrayList<String> l2;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList("l1", l1);
        savedInstanceState.putStringArrayList("l2", l2);

        // Always call the superclass so it can save the view hierarchy state

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            l1 = savedInstanceState.getStringArrayList("l1");
            l2 = savedInstanceState.getStringArrayList("l2");
        }
        else{
            l1 = new ArrayList<String>();
            l2 = new ArrayList<String>();
        }


        setContentView(R.layout.activity_pre_proccess_text);
        final Button next = (Button) findViewById(R.id.nextImage);
        final Button save = (Button) findViewById(R.id.save);
        ListView listview = findViewById(R.id.list_of_words);
        Intent intent = getIntent();
        ArrayList<String> wordlist = intent.getStringArrayListExtra("wordlist");
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordlist);
        if(l1.isEmpty()) {
            save.setVisibility(View.GONE);
        }
        else{
            next.setVisibility(View.GONE);
        }

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                l1 = wordlist;
                Intent intent = new Intent(getApplicationContext(), CameraCaptureActivity.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    l2 = wordlist;
                    saveFile(l1,l2);
                    l1 = new ArrayList<String>();
                    l2 = new ArrayList<String>();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wordlist.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        listview.setAdapter(adapter);
    }

    private void saveFile(ArrayList<String> l1, ArrayList<String> l2) throws JSONException, IOException {
        FileManger fileManger = new FileManger();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < l1.size(); i++) {
            jsonObject.put(l1.get(i), l2.get(i));
        }
        fileManger.writeFile(getApplicationContext(), "w.Json", jsonObject);
    }

}