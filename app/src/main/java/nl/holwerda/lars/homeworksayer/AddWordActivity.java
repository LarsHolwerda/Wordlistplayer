package nl.holwerda.lars.homeworksayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class AddWordActivity extends AppCompatActivity {
    ArrayList<AdapterRecyclerItem> dataArrayList = new ArrayList<>();
    private void addToFile(String wordFrom, String wordTo, String fileName) {
        FileManger fileManger = new FileManger();
        JSONObject fileData = new JSONObject();
        try {
            fileData.put(wordFrom, wordTo);
            fileManger.updateFile(getApplicationContext(), fileName, fileData);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }
    //delete function from adapter and file for item on click V
    //add to adapter and file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        Button addWordButton = findViewById(R.id.addword);
        EditText wordFrom = findViewById(R.id.wordfrom_edit);
        EditText wordTo = findViewById(R.id.wordto_edit);
        RecyclerView recyclerView = findViewById(R.id.recycler);

        Intent intent = getIntent();
        String fileName = intent.getStringExtra("filename");

        FileManger fileManger = new FileManger();


        try {
            JSONObject dataJson = fileManger.readFile(getApplicationContext(), fileName);
            dataArrayList = fileManger.jsonToArrayListObjects(dataJson);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        AdapterRecycler adapterRecycler = new AdapterRecycler(dataArrayList, getApplicationContext(), fileName);
        recyclerView.setAdapter(adapterRecycler);

        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value_wordFrom = wordFrom.getText().toString();
                String value_wordTo = wordTo.getText().toString();
                addToFile(value_wordFrom,value_wordTo,fileName);
                dataArrayList.add(new AdapterRecyclerItem(value_wordFrom,value_wordTo));
                adapterRecycler.notifyDataSetChanged();
            }
        });
    }
}
