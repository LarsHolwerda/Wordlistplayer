package nl.holwerda.lars.homeworksayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Stringsholder> translatedlist;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, ArrayList<Stringsholder> translatedlist) {
        this.context = context;
        this.translatedlist = translatedlist;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return translatedlist.size();
    }

    @Override
    public Object getItem(int i) { return null;
    }

    @Override
    public long getItemId(int i) { return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_listview, null);
        TextView edit1 = (TextView) view.findViewById(R.id.textView1);
        TextView edit2 = (TextView) view.findViewById(R.id.textView2);

        Stringsholder a = translatedlist.get(i);
        edit1.setText(a.getLocaltext());
        edit2.setText(a.getTranslatedtext());
        return view;
    }

}