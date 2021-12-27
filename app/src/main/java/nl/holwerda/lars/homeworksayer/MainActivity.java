package nl.holwerda.lars.homeworksayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private long langid;
    private TextToSpeech tts;

    private ArrayList<Stringsholder> translatedlist = new ArrayList<>();
    private UtteranceProgressListener listener;
    private ListView simpleList;
    private List<String> myList = new ArrayList<>();
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleList = (ListView) findViewById(R.id.simpleListView);
        customAdapter = new CustomAdapter(getApplicationContext(), translatedlist);
        simpleList.setAdapter(customAdapter);

        initializeCameraButton();

        initializeTextToSpeech();

        initializeClearButton();

        initializeDropDown();

        addListWithButton();

        InitializeAddWordButton();

        initializePlayButton();
    }

    //aanmaken van de camera button
    private void initializeCameraButton() {
        final Button button = (Button) findViewById(R.id.capture_activity);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CameraCaptureActivity.class);
                startActivity(intent);
            }
        });
    }

    //text to speech regelen
    private void initializeTextToSpeech() {
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
    }

    //aanmaken van de clear button
    private void initializeClearButton() {
        Button clearbutton = (Button) findViewById(R.id.button2);
        clearbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                translatedlist.clear();
                customAdapter.notifyDataSetChanged();

            }
        });
    }

    private JSONObject arrayListToJsonObject(ArrayList<Stringsholder> inputtranslatedlist) {
        JSONObject jsonObjectToSave = new JSONObject();

        for (Stringsholder s : inputtranslatedlist) {
            String localtekst = s.getLocaltext();
            String translatedtext = s.getTranslatedtext();
            try {
                jsonObjectToSave.put(localtekst, translatedtext);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObjectToSave;
    }

    //creeren van een json file van de woordenlijst
    private void addListWithButton() {
        Button lijstopslaanbutton = (Button) findViewById(R.id.button4);
        lijstopslaanbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FileManger fileManger = new FileManger();
                try {
                    fileManger.writeFile(getApplicationContext(), "doeffgek", arrayListToJsonObject(translatedlist));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    //aanmaken van de dropdown en de items van de files showen in de listview
    private void initializeDropDown() {
        File path = new File("/data/data/nl.holwerda.lars.homeworksayer/files");
        File list[] = path.listFiles();
        for( int i=0; i< list.length; i++)
        {
            myList.add( list[i].getName() );
        }
        Spinner mySpinner = findViewById(R.id.spinner2);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, myList);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showListFromDropDown(myAdapter.getItem(position), getApplicationContext());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //voegt woorden toe aan de listview
    private void addWordsWithButton() {
        EditText inputnl = (EditText) findViewById(R.id.editText1);
        EditText inputtranslated = (EditText) findViewById(R.id.editText2);
        Stringsholder sh = new Stringsholder();
        sh.setLocaltext(inputnl.getText().toString());
        sh.setTranslatedtext(inputtranslated.getText().toString());
        if (langid == 0) {
            sh.setTranslation(Locale.ENGLISH);
        }
        if (langid == 1) {
            sh.setTranslation(Locale.GERMAN);
        }
        translatedlist.add(sh);
        customAdapter.notifyDataSetChanged();
    }

    //aanmaken van de toevoeg button
    private void InitializeAddWordButton() {
        Button addbutton = (Button) findViewById(R.id.button1);
        addbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                addWordsWithButton();
            }


        });
    }

    //speelt de woorden die in de listview staan af
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

    //stuurt de playbutton aan om de woorden af te spelen
    private void initializePlayButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Afspelen();

            }
        });
    }

    //voegt de woorden die zijn toegevoegd aan de adapter toe
    public void addItemToArrayAdapter(String key, String value) {
        Stringsholder item = new Stringsholder();
        item.setLocaltext(key);
        item.setTranslatedtext(value);
        translatedlist.add(item);
        customAdapter.notifyDataSetChanged();
    }

    //laad de woorden die opgeslagen in de lijst zitten in
    public void showListFromDropDown(String fileName, Context context) {
        translatedlist.clear();
        customAdapter.notifyDataSetChanged();
        FileManger fileManger = new FileManger();
        JSONObject wordlist;
        try {
            wordlist = fileManger.readFile(context, fileName);

            JSONArray keys = wordlist.names ();
            for (int i = 0; i < keys.length (); i++) {

                String key = keys.getString(i); // Here's your key
                String value = wordlist.getString(key); // Here's your value
                addItemToArrayAdapter(key, value);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    //laat zien wat er gebeurd als je op het pijltje terug klikt
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


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
