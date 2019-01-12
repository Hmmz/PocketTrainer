package com.pockettrainer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HistoriqueFragment extends Fragment {
    View view;
    DataBaseHelper db;
    ListView list_historique;
    ArrayList<String> list_Date = new ArrayList<>();
    ArrayList<String> list_Titre = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    String date = "";
    String check = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.historique_fragment, container, false);
        list_historique = view.findViewById(R.id.list_historique);
        db = new DataBaseHelper(getContext());

        list_historique.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Exercices.class);
                intent.putExtra("position",position);
                startActivity(intent);

            }
        });

        viewDate();

        return view;
    }

    public void viewDate()
    {
        list_Date.clear();
        Cursor cursor = db.viewData();

        if (cursor.getCount() != 0 )
        {
            while(cursor.moveToNext())
            {
                 String historique = cursor.getString(0);
                 historique = historique + " - ";

                 if (!check.equals(cursor.getString(1)))
                 {
                     historique = historique + cursor.getString(1);
                     list_Date.add(historique);
                     list_Titre.add(cursor.getString(0));
                     check = cursor.getString(1);

                 }
            }
            arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list_Date);
            list_historique.setAdapter(arrayAdapter);
        }

    }


}
