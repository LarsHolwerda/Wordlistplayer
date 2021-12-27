package nl.holwerda.lars.homeworksayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.File;
import java.util.ArrayList;

public class editActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Spinner spinner = (Spinner) findViewById(R.id.dropdown);
        ArrayAdapter<CharSequence> fileNamesAdapter = new ArrayAdapter<CharSequence>(getApplicationContext(),android.R.layout.simple_spinner_item);
        File path = new File("/data/data/nl.holwerda.lars.homeworksayer/files");
        File list[] = path.listFiles();
        if (list != null) {
            for (File file : list) {
                fileNamesAdapter.add(file.getName());
            }
        }
        fileNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(fileNamesAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}