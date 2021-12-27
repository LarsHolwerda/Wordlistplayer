package nl.holwerda.lars.homeworksayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CreateFileActivity extends AppCompatActivity {
    String languageFromSelected = "";
    String languageToSelected = "";
    private void switchIntent(String fileName){
        Intent intent = new Intent(this,AddWordActivity.class);
        intent.putExtra("filename",fileName);
        startActivity(intent);
    }
    private void populateAdapter(ArrayAdapter<CharSequence> adapter){
        CharSequence[] lan = {"Nederlands","Frans","Duits"};
        for (CharSequence s : lan) {
            adapter.add(s);
        }
    }

    private void createNewFile(String fileName, View view, String languageFrom, String languageTo){
        FileManger fileManger =  new FileManger();
        try {
            fileManger.writeFile(getApplicationContext(), fileName, new JSONObject());

            JSONObject lan_item = new JSONObject();
            lan_item.put(languageFrom, languageTo);
            fileManger.writeFile(getApplicationContext(), fileName+"_Lan", lan_item);
        } catch (IOException | JSONException e) {
            Snackbar alreadyExistMessage = Snackbar.make(view, e.toString(), Snackbar.LENGTH_LONG);
            alreadyExistMessage.show();
        }
        switchIntent(fileName);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_wordlist);
        Button createButton = findViewById(R.id.createFile);
        EditText fileName = findViewById(R.id.fileName);
        Spinner languageFromSpinner = findViewById(R.id.LanguageFrom);
        Spinner languageToSpinner = findViewById(R.id.LanguageTo);



        //logic for languageFrom spinner
        ArrayAdapter<CharSequence> languageFromAdapter = new ArrayAdapter<CharSequence>(getApplicationContext(),android.R.layout.simple_spinner_item);
        languageFromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        populateAdapter(languageFromAdapter);
        languageFromSpinner.setAdapter(languageFromAdapter);
        languageFromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CharSequence item = languageFromAdapter.getItem(i);
                languageFromSelected = item.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //logic for languageTo spinner
        ArrayAdapter<CharSequence> languageToAdapter = new ArrayAdapter<CharSequence>(getApplicationContext(),android.R.layout.simple_spinner_item);
        languageToAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        populateAdapter(languageToAdapter);
        languageToSpinner.setAdapter(languageToAdapter);
        languageToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CharSequence item = languageToAdapter.getItem(i);
                languageToSelected = item.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = fileName.getText().toString();
                if (!filename.equals("") && !languageFromSelected.equals("")&& !languageToSelected.equals("")){
                    createNewFile(filename,view,languageFromSelected,languageToSelected);
                }

            }
        });

    }
}
