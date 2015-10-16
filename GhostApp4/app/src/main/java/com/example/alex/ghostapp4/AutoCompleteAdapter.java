package com.example.alex.ghostapp4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex van der Meer
 * Student number: 10400958
 * on 12-10-2015.
 *
 * This Class handles the autocompletion, when a player chooses a name.
 * It gives a clickable list of previously used playernames
 *
 * It takes the PlayersArray and applies a filter and filters on what the user has already typed.
 *
 * Source of how to create a custom array adapter for auto completion of tekst
 * http://stackoverflow.com/questions/8784249/android-autocompletetextview-with-custom-adapter-filtering-not-working
 */

public class AutoCompleteAdapter extends ArrayAdapter<Player> {
    private List<Player> PlayersArray;
    private List<Player> Suggestions;
    private List<Player> PlayersArrayAll;
    private int ViewLayoutId;
    private int TextViewId;

    // The Constructor takes a viewlayout ID to make it easy to use a different layout file
    // for the suggestions list items
    public AutoCompleteAdapter(Context context, int viewLayoutId, int textViewId, List<Player> playersArray) {
        super(context, viewLayoutId, textViewId, playersArray);
        PlayersArray = playersArray;
        // Clone the Array
        PlayersArrayAll = new ArrayList<Player>(PlayersArray);
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
            List<Player> filteredList = (List<Player>) results.values;
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