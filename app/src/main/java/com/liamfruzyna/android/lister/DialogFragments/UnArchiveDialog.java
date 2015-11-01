package com.liamfruzyna.android.lister.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.liamfruzyna.android.lister.Activities.WLActivity;
import com.liamfruzyna.android.lister.Data.IO;
import com.liamfruzyna.android.lister.Data.WishList;
import com.liamfruzyna.android.lister.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mail929 on 10/31/15.
 */
public class UnArchiveDialog extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Use the Builder class for convenient dialog construction
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.share_list_item, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ListView list = (ListView) v.findViewById(R.id.listView);
        final List<WishList> archived = new ArrayList<>();
        for(int i = 0; i < WLActivity.lists.size(); i++)
        {
            if(WLActivity.lists.get(i).archived)
            {
                archived.add(WLActivity.lists.get(i));
            }
        }

        list.setAdapter(new ArrayAdapter<WishList>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, archived) {
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView == null) {
                    LayoutInflater infl = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
                    convertView = infl.inflate(android.R.layout.simple_list_item_1, parent, false);
                }
                view = super.getView(position, convertView, parent);

                ((TextView) view.findViewById(android.R.id.text1)).setText(archived.get(position).name);
                return view;
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                List<WishList> unArchieved = WLActivity.unArchieved;
                unArchieved.add(archived.get(position));
                archived.get(position).archived = false;
                IO.save(WLActivity.lists);
            }
        });
        builder.setMessage("Choose a list to unarchive")
                .setTitle("Unarchive a List")
                .setView(v)
                .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do nothing
                    }
                });
        return builder.create();
    }
}