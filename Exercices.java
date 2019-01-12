package com.pockettrainer;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Exercices extends AppCompatActivity {
    TextView tv_muscle, tv_poids, tv_rep;
    GridLayout gl_historique;
    DataBaseHelper db;
    int compteur;
    String check  = "";
    String checkmuscle = "";
    int lignes;
    ArrayList<String> list_Titre = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercices);

        db = new DataBaseHelper(getApplicationContext());
        gl_historique = findViewById(R.id.gl_historique);

        viewDate();

        Intent intent = getIntent();

        Cursor cursor = db.viewData();
        compteur = intent.getIntExtra("position", 0);
        System.out.println(list_Titre.get(compteur));
        while (cursor.moveToNext())
        {
            lignes = gl_historique.getRowCount(); //Récupération du nombre de ligne (position des prochains widget

            if (cursor.getString(0).equalsIgnoreCase(list_Titre.get(compteur)))
            {
                System.out.println("ok");
                //Créations de nouveaux widget
                ////////////////////////////////////

                tv_muscle  = new TextView(getApplicationContext());
                if (!checkmuscle.equalsIgnoreCase(cursor.getString(2)))
                {
                    tv_muscle.setText(cursor.getString(2));
                    checkmuscle = cursor.getString(2);
                }
                tv_muscle.setTextSize(25);
                tv_muscle.setSingleLine(false);
                tv_muscle.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                tv_muscle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv_muscle.setGravity(Gravity.CENTER);
                tv_muscle.setWidth(1);

                tv_poids  = new TextView(getApplicationContext());
                tv_poids.setText(cursor.getString(3));
                tv_poids.setTextSize(25);
                tv_poids.setInputType(InputType.TYPE_CLASS_TEXT);
                tv_poids.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv_poids.setGravity(Gravity.CENTER);
                tv_poids.setWidth(1);

                tv_rep  = new TextView(getApplicationContext());
                tv_rep.setText(cursor.getString(4));
                tv_rep.setTextSize(25);
                tv_rep.setInputType(InputType.TYPE_CLASS_TEXT);
                tv_rep.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv_rep.setGravity(Gravity.CENTER);
                tv_rep.setWidth(1);

                //Création des parametres pour les widget
                ///////////////////////////////////////////////////////////////////////////
                GridLayout.LayoutParams paramsTVMUSCLE = new GridLayout.LayoutParams();
                GridLayout.LayoutParams paramsTVPoids = new GridLayout.LayoutParams();
                GridLayout.LayoutParams paramsTVREP = new GridLayout.LayoutParams();

                //Paramètres des TextView
                ///////////////////////////////////////////
                paramsTVMUSCLE.rowSpec = gl_historique.spec(gl_historique.getRowCount(),1,1);
                paramsTVMUSCLE.columnSpec = gl_historique.spec(0,1,1);

                paramsTVPoids.rowSpec = gl_historique.spec(gl_historique.getRowCount(),1,1);
                paramsTVPoids.columnSpec = gl_historique.spec(1,1,1);

                paramsTVREP.rowSpec = gl_historique.spec(gl_historique.getRowCount(),1,1);
                paramsTVREP.columnSpec = gl_historique.spec(2,1,1);


                gl_historique.addView(tv_muscle, paramsTVMUSCLE);
                gl_historique.addView(tv_poids, paramsTVPoids);
                gl_historique.addView(tv_rep, paramsTVREP);

            }
        }
        cursor.moveToFirst();

    }

    public void viewDate()
    {
        list_Titre.clear();
        System.out.println("ca clear");
        Cursor cursor = db.viewData();

        if (cursor.getCount() != 0 )
        {
            while(cursor.moveToNext())
            {
                String historique = cursor.getString(0);
                historique = historique + " - ";

                if (!check.equals(cursor.getString(0)))
                {
                    list_Titre.add(cursor.getString(0));
                    check = cursor.getString(0);

                }
            }
        }

    }
}
