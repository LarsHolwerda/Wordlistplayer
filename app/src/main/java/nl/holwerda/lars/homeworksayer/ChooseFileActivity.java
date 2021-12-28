package nl.holwerda.lars.homeworksayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.io.File;

public class ChooseFileActivity extends AppCompatActivity{
    String filename = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Spinner spinner = (Spinner) findViewById(R.id.dropdown);
        Button button = (Button) findViewById(R.id.editButton);

        Intent intent_from = getIntent();
        String goToActivity = intent_from.getStringExtra("goToActivity");

        button.setText(goToActivity);



        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!filename.equals("")){
                    Intent intent;
                    if (goToActivity.equals("edit")) {
                        intent = new Intent(ChooseFileActivity.this, AddWordActivity.class);
                    }
                    else{
                        intent = new Intent(ChooseFileActivity.this, RehearseWordlist.class);
                    }
                    intent.putExtra("filename", filename);
                    startActivity(intent);
                }
            }
        });

        ArrayAdapter<CharSequence> fileNamesAdapter = new ArrayAdapter<CharSequence>(getApplicationContext(),android.R.layout.simple_spinner_item);
        File path = new File("/data/data/nl.holwerda.lars.homeworksayer/files");
        File list[] = path.listFiles();
        if (list != null) {
            for (File file : list) {
                String file_name = file.getName();
                int pos = file_name.lastIndexOf(".");
                fileNamesAdapter.add(file_name.substring(0,pos));
            }
        }
        fileNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(fileNamesAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CharSequence item = fileNamesAdapter.getItem(i);
                filename = item.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}