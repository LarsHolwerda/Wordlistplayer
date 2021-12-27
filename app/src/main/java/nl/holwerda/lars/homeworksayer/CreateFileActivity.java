package nl.holwerda.lars.homeworksayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.IOException;

public class CreateFileActivity extends AppCompatActivity {

    private void switchIntent(String fileName){
        Intent intent = new Intent(this,AddWordActivity.class);
        intent.putExtra("filename",fileName);
        startActivity(intent);
    }

    private void createNewFile(String fileName, View view){
        FileManger fileManger =  new FileManger();
        try {
            fileManger.writeFile(getApplicationContext(), fileName, new JSONObject());
        } catch (IOException e) {
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

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = fileName.getText().toString();
                if (!filename.equals("")){
                    createNewFile(filename,view);
                }

            }
        });

    }
}
