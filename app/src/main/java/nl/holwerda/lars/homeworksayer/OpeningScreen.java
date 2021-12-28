package nl.holwerda.lars.homeworksayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpeningScreen extends AppCompatActivity {

    private void switchIntent(Class<?> intentClass, String extra){
        Intent intent = new Intent(this,intentClass);
        if(!extra.equals("")){
            intent.putExtra("goToActivity",extra);
        }
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);
        Button CreateFilepageButton = findViewById(R.id.createFilepageButton);
        Button EditWordlistButton = findViewById(R.id.EditWordlistButton);
        Button RehearseWordlistButton = findViewById(R.id.RehearseWordlistButton);
        Button DeleteWordlistButton = findViewById(R.id.DeleteWordlistButton);

        CreateFilepageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchIntent(CreateFileActivity.class,"");
            }
        });

        EditWordlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchIntent(ChooseFileActivity.class,"edit");
            }
        });

        RehearseWordlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchIntent(ChooseFileActivity.class,"rehearse");
            }
        });

        DeleteWordlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchIntent(DeleteFiles.class,"");
            }
        });
    }
}