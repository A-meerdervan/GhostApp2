package com.example.alex.ghostapp2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alex on 12-10-2015.
 *
 * Source of how to create a custom array adapter for auto completion of tekst
 * http://stackoverflow.com/questions/8784249/android-autocompletetextview-with-custom-adapter-filtering-not-working
 */

public class AutoCompleteAdapter extends ArrayAdapter<Player> {
    private ArrayList<Player> PlayersArray;
    private ArrayList<Player> Suggestions;
    private ArrayList<Player> PlayersArrayAll;
    private int ViewLayoutId;
    private int TextViewId;

    public AutoCompleteAdapter(Context context, int viewLayoutId, int textViewId, ArrayList<Player> playersArray) {
        super(context, viewLayoutId, textViewId, playersArray);
        PlayersArray = playersArray;
        PlayersArrayAll = (ArrayList<Player>) PlayersArray.clone();
        Suggestions = new ArrayList<>();
        ViewLayoutId = viewLayoutId;
        TextViewId = textViewId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View V = convertView;
        if (V == null) {
            LayoutInflater Inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            V = Inflater.inflate(ViewLayoutId, null);
        }
        Player player = PlayersArray.get(position);
        if (player != null) {
            TextView PlayerName = (TextView) V.findViewById(TextViewId);
            if (PlayerName != null) {
//              Log.i(MY_DEBUG_TAG, "getView Customer Name:"+customer.getName());
                PlayerName.setText(player.Name);
            }
        }
        return V;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object player) {
            String Name = ((Player)(player)).Name;
            return Name;
        }
        @Override
        protected Filter.FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                Suggestions.clear();
                for (Player player : PlayersArrayAll) {
                    if(player.Name.toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        Suggestions.add(player);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = Suggestions;
                filterResults.count = Suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Player> filteredList = (ArrayList<Player>) results.values;
            if(results != null && results.count > 0) {
                clear();
                for (Player player : filteredList) {
                    add(player);
                }
                notifyDataSetChanged();
            }
        }
    };

}