package nl.holwerda.lars.homeworksayer;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {

    private final ArrayList<AdapterRecyclerItem> localDataSet;
    private final Context localContext;
    private final String localFileName;

    private void refreshFile(){
        FileManger fileManger = new FileManger();
        try {
            fileManger.deleteFile(localContext,localFileName);
            fileManger.writeFile(localContext,localFileName,fileManger.ArrayListObjectsToJson(localDataSet));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView wordFrom;
        private TextView wordTo;
        private Button delete;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            wordFrom = (TextView) view.findViewById(R.id.wordFrom);
            wordTo = (TextView) view.findViewById(R.id.wordTo);
            delete = (Button) view.findViewById(R.id.delete);

        }

        public TextView getWordFrom() {
            return wordFrom;
        }
        public TextView getWordTo() {
            return wordTo;
        }
        public Button getDelete() {
            return delete;
        }
    }

    public AdapterRecycler(ArrayList<AdapterRecyclerItem> dataSet, Context context, String filename) {
        localDataSet = dataSet;
        localContext = context;
        localFileName = filename;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_recycler_item, viewGroup, false);

        return new ViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getWordFrom().setText(localDataSet.get(position).wordFrom);
        viewHolder.getWordTo().setText(localDataSet.get(position).wordTo);
        viewHolder.getDelete().setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                localDataSet.remove(viewHolder.getAdapterPosition());
                refreshFile();
                notifyDataSetChanged();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
