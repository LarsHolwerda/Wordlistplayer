package nl.holwerda.lars.homeworksayer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private long langid;
    private TextToSpeech tts;
    private EditText inputnl;
    private EditText inputtranslated;
    private ArrayList<stringsholder> translatedlist = new ArrayList<>();
    private UtteranceProgressListener listener;
    ListView simpleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleList = (ListView) findViewById(R.id.simpleListView);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), translatedlist);
        simpleList.setAdapter(customAdapter);

        listener = new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
                Log.d("test", "onDone: ");
            }

            @Override
            public void onError(String utteranceId) {

            }
        };
        //test
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(new Locale("nl_NL"));
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                              result==TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("error", "This Language is not supported");
                    }

                    int resultaat=tts.setLanguage(Locale.GERMANY);
                    if(resultaat==TextToSpeech.LANG_MISSING_DATA ||
                           resultaat==TextToSpeech.LANG_NOT_SUPPORTED){
                         Log.e("error", "This Language is not supported");
                    }
                    tts.setOnUtteranceProgressListener(listener);
                }
            }
        });;

        inputnl = (EditText) findViewById(R.id.editText1);
        inputtranslated = (EditText) findViewById(R.id.editText2);

        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Engels", "Duits"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                langid = id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button clearbutton = (Button) findViewById(R.id.button2);
        clearbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                translatedlist.clear();


            }
        });


        Button addbutton = (Button) findViewById(R.id.button1);
        addbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                stringsholder sh = new stringsholder();
                sh.setLocaltext(inputnl.getText().toString());
                sh.setTranslatedtext(inputtranslated.getText().toString());
                if (langid == 0) {
                    sh.setTranslation(Locale.ENGLISH);
                }
                if (langid == 1) {
                    sh.setTranslation(Locale.GERMAN);
                }
                translatedlist.add(sh);

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Afspelen();

            }
        });
    }


    private void Afspelen() {

        for (int i = 0; i < translatedlist.size(); i++) {
            tts.setLanguage(new Locale("nl_NL"));
            tts.speak(translatedlist.get(i).getLocaltext(), TextToSpeech.QUEUE_ADD, null);
            tts.playSilentUtterance(800, TextToSpeech.QUEUE_ADD, null);

            tts.setLanguage(translatedlist.get(i).getTranslation());
            tts.speak(translatedlist.get(i).getTranslatedtext(), TextToSpeech.QUEUE_ADD, null);
            tts.playSilentUtterance(1100, TextToSpeech.QUEUE_ADD, null);

        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close the Wordlist Player?")
                .setNegativeButton("Sure, i wil do it", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setPositiveButton("Of course not!", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        Spinner spinner;

        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"1", "2", "three"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        return super.onOptionsItemSelected(item);
    }
}
