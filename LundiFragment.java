package com.pockettrainer;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LundiFragment extends Fragment {
    View view;
    GridLayout gl_sceance;
    int nombreWidget = 5;
    int checkExercice = 0;
    int clickTerminer = 0;


    //Variable Ajout d'exercice
    ////////////////////////////
    Button bt_addExercice, bt_addSousExercice, bt_terminerSceance;
    EditText et_muscle, et_poids, et_rep, et_nomSceance;

    //Variable fin exercice
    String stringPoids, stringRep;
    int compteur;
    int index = 3;


    //Variable Date
    ////////////////////////////
    TextView tv_date;
    Calendar calendar;

    String date;

    //Variable Base de donnée
    ////////////////////////////
    boolean dateIdentique;
    String nomSceance;
    ArrayList<String> list_Date;
    String check = "";
    DataBaseHelper db;
    String muscle;
    int poids, rep;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.lundi_fragment, container, false);
        gl_sceance = view.findViewById(R.id.gl_sceance);


        //Instancier la date actuel
        calendar = Calendar.getInstance();
        tv_date = view.findViewById(R.id.tv_date);
        date = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        tv_date.setText(date);


        // Ajout d'un exercice
        ////////////////////////////////////////
        bt_addExercice = view.findViewById(R.id.bt_addExercice);
        bt_addExercice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AjoutExercice();
            }
        });
        //////////////////////////////////////////

        //Ajout de sous exercice
        ////////////////////////////
        bt_addSousExercice = new Button(getContext());
        bt_addSousExercice.setText("+");
        bt_addSousExercice.setTextSize(25);

        bt_addSousExercice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AjoutSousExercice();
            }
        });
        ////////////////////////////////////////////

        //Terminer la sceance
        /////////////////////////////////
        list_Date = new ArrayList<>();
        et_nomSceance = view.findViewById(R.id.et_nomSceance);
        bt_terminerSceance = view.findViewById(R.id.bt_terminerSceance);

        bt_terminerSceance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TerminerSceance();
            }
        });


        return view;
    }


    //Ajout d'un exercice
    public void AjoutExercice() {
        int lignes = gl_sceance.getRowCount(); //Récupération du nombre de ligne (position des prochains widget)
        nombreWidget = nombreWidget + 3;

        //Créations de nouveaux widget ou suppression
        ////////////////////////////////////
        Context context = getContext();
        //Créations des widget
        et_muscle = new EditText(context);
        et_muscle.setHint("Dévellopé couché");
        et_muscle.setSingleLine(true);
        et_muscle.setInputType(InputType.TYPE_CLASS_TEXT);
        et_muscle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        et_muscle.setGravity(Gravity.CENTER);
        et_muscle.setWidth(1);


        et_poids = new EditText(context);
        et_poids.setHint("150");
        et_poids.setSingleLine(true);
        et_poids.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_poids.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        et_poids.setGravity(Gravity.CENTER);
        et_poids.setWidth(1);

        et_rep = new EditText(context);
        et_rep.setHint("15");
        et_rep.setSingleLine(true);
        et_rep.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_rep.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        et_rep.setGravity(Gravity.CENTER);
        et_rep.setWidth(1);


        gl_sceance.removeView(bt_addExercice);
        gl_sceance.removeView(bt_addSousExercice);
        /////////////////////////////////////////


        //Création des parametres pour les widget
        ///////////////////////////////////////////////////////////////////////////

        GridLayout.LayoutParams paramsMuscle = new GridLayout.LayoutParams();
        GridLayout.LayoutParams paramsPoids = new GridLayout.LayoutParams();
        GridLayout.LayoutParams paramsRep = new GridLayout.LayoutParams();
        GridLayout.LayoutParams paramsSousExercice = new GridLayout.LayoutParams();
        GridLayout.LayoutParams paramsExercice = new GridLayout.LayoutParams();
        /////////////////////////////////////////////////////////////////////////////


        //Paramètres des EditsText
        ///////////////////////////////////////////
        paramsMuscle.rowSpec = gl_sceance.spec(lignes, 1);
        paramsMuscle.columnSpec = gl_sceance.spec(0, 1, 1);

        paramsPoids.rowSpec = gl_sceance.spec(lignes, 1);
        paramsPoids.columnSpec = gl_sceance.spec(1, 1, 1);

        paramsRep.rowSpec = gl_sceance.spec(lignes, 1);
        paramsRep.columnSpec = gl_sceance.spec(2, 1, 1);


        //Paramètres des boutons
        ////////////////////////////////////////////////

        paramsSousExercice.rowSpec = gl_sceance.spec(lignes + 1, 1);
        paramsSousExercice.columnSpec = gl_sceance.spec(1, 2, 1);
        paramsExercice.rowSpec = gl_sceance.spec(lignes + 2, 1);
        paramsExercice.columnSpec = gl_sceance.spec(0, 3, 1);

        gl_sceance.addView(et_muscle, paramsMuscle);
        gl_sceance.addView(et_poids, paramsPoids);
        gl_sceance.addView(et_rep, paramsRep);
        gl_sceance.addView(bt_addSousExercice, paramsSousExercice);
        gl_sceance.addView(bt_addExercice, paramsExercice);


    }

    public void AjoutSousExercice() {
        int lignes = gl_sceance.getRowCount(); //Récupération du nombre de ligne (position des prochains widget)
        nombreWidget = nombreWidget + 2;


        //Créations de nouveaux widget ou suppression
        ////////////////////////////////////
        Context context = getContext();
        et_poids = new EditText(context);
        et_poids.setHint("150");
        et_poids.setSingleLine(true);
        et_poids.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_poids.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        et_poids.setGravity(Gravity.CENTER);
        et_poids.setWidth(1);

        et_rep = new EditText(context);
        et_rep.setHint("15");
        et_rep.setSingleLine(true);
        et_rep.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_rep.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        et_rep.setGravity(Gravity.CENTER);
        et_rep.setWidth(1);

        gl_sceance.removeView(bt_addSousExercice);
        gl_sceance.removeView(bt_addExercice);
        /////////////////////////////////////////

        //Création des parametres pour les widget
        ///////////////////////////////////////////////////////////////////////////

        GridLayout.LayoutParams paramsPoids = new GridLayout.LayoutParams();
        GridLayout.LayoutParams paramsRep = new GridLayout.LayoutParams();
        GridLayout.LayoutParams paramsSousExercice = new GridLayout.LayoutParams();
        GridLayout.LayoutParams paramsExercice = new GridLayout.LayoutParams();
        /////////////////////////////////////////////////////////////////////////////

        //Paramètres des EditsText
        ///////////////////////////////////////////

        paramsPoids.rowSpec = gl_sceance.spec(lignes, 1);
        paramsPoids.columnSpec = gl_sceance.spec(1, 1, 1);

        paramsRep.rowSpec = gl_sceance.spec(lignes, 1);
        paramsRep.columnSpec = gl_sceance.spec(2, 1, 1);

        //Paramètres des boutons
        ////////////////////////////////////////////////

        paramsSousExercice.rowSpec = gl_sceance.spec(lignes + 1, 1);
        paramsSousExercice.columnSpec = gl_sceance.spec(1, 2, 1);
        paramsExercice.rowSpec = gl_sceance.spec(lignes + 2, 1);
        paramsExercice.columnSpec = gl_sceance.spec(0, 3, 1);


        gl_sceance.addView(et_poids, paramsPoids);
        gl_sceance.addView(et_rep, paramsRep);
        gl_sceance.addView(bt_addSousExercice, paramsSousExercice);
        gl_sceance.addView(bt_addExercice, paramsExercice);


    }

    public void TerminerSceance() {
        dateIdentique = false;
        calendar = Calendar.getInstance();
        calendar.add(calendar.DATE, 1);
        date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        if (clickTerminer <= 0)
        {
            VerifierDate();
        }
        if (dateIdentique == false)
        {
            clickTerminer++;
            nomSceance = et_nomSceance.getText().toString();

            if (!nomSceance.equals(""))
            {

                if (gl_sceance.getRowCount() != 2)
                {
                    compteur = nombreWidget - 5;

                    while (compteur > 0)
                    {
                        Object childMuscle = gl_sceance.getChildAt(index);

                        if (childMuscle instanceof  EditText)
                        {
                            if (((EditText) childMuscle).getInputType() == InputType.TYPE_CLASS_TEXT)
                            {
                               muscle =((EditText) childMuscle).getText().toString();

                               if (!muscle.equals(""))
                               {
                                   index++;
                                   Object childPoids = gl_sceance.getChildAt(index);

                                   if (childPoids instanceof EditText)
                                   {
                                       if (((EditText) childPoids).getInputType() == InputType.TYPE_CLASS_NUMBER)
                                       {
                                           stringPoids = ((EditText) childPoids).getText().toString();
                                           if (!stringPoids.equals(""))
                                           {
                                               poids = Integer.parseInt(stringPoids);

                                               index++;
                                               Object childRep = gl_sceance.getChildAt(index);

                                               if (childRep instanceof EditText)
                                               {
                                                   if (((EditText) childRep).getInputType() == InputType.TYPE_CLASS_NUMBER)
                                                   {
                                                       stringRep = ((EditText) childRep).getText().toString();
                                                       if (!stringRep.equals(""))
                                                       {
                                                           rep = Integer.parseInt(stringRep);
                                                           db.insertData(date,nomSceance,muscle,poids,rep);
                                                           checkExercice = checkExercice + 3;
                                                           index++;
                                                       }
                                                       else
                                                       {
                                                           Toast.makeText(getContext(), "Veuillez oompléter les champs vides", Toast.LENGTH_SHORT).show();
                                                       }
                                                   }
                                               }
                                           }
                                           else
                                           {
                                               Toast.makeText(getContext(), "Veuillez oompléter les champs vides", Toast.LENGTH_SHORT).show();
                                           }

                                       }

                                   }
                               }
                               else
                               {
                                   Toast.makeText(getContext(), "Veuillez oompléter les champs vides", Toast.LENGTH_SHORT).show();

                               }

                            }
                            else
                            {
                                Object childPoids = gl_sceance.getChildAt(index);
                                if (childPoids instanceof EditText)
                                {
                                    if (((EditText) childPoids).getInputType() == InputType.TYPE_CLASS_NUMBER)
                                    {
                                        stringPoids = ((EditText) childPoids).getText().toString();

                                        if(!stringPoids.equals(""))
                                        {
                                            poids = Integer.parseInt(stringPoids);
                                            index++;
                                            Object childRep = gl_sceance.getChildAt(index);
                                            if (childRep instanceof  EditText)
                                            {
                                                if(((EditText) childRep).getInputType() == InputType.TYPE_CLASS_NUMBER)
                                                {
                                                    stringRep = ((EditText) childRep).getText().toString();

                                                    if(!stringRep.equals(""))
                                                    {
                                                        rep = Integer.parseInt(stringRep);
                                                        db.insertData(date,nomSceance,muscle,poids,rep);
                                                        checkExercice = checkExercice + 2;
                                                        index++;
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(getContext(), "Veuillez oompléter les champs vides", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            }

                                        }
                                        else
                                        {
                                            Toast.makeText(getContext(), "Veuillez oompléter les champs vides", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }

                        }

                        compteur--;

                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Vous devez enregistrez un exercice", Toast.LENGTH_SHORT).show();

                }

            }
            else
            {
                Toast.makeText(getContext(), "Vous devez complétez l'enregistrement de la scéance", Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            Toast.makeText(getContext(), "Vous ne pouvez pas enregistrer un entrainement le même jour", Toast.LENGTH_SHORT).show();

        }

        if (checkExercice == nombreWidget)
        {
            Toast.makeText(getContext(), "Scéance enregistrée", Toast.LENGTH_SHORT).show();

        }
    }

    public void VerifierDate()
    {
        db = new DataBaseHelper(getContext());
        Cursor cursor = db.viewData();

        if (cursor.getCount() != 0)
        {
           while(cursor.moveToNext())
           {
               if (date.equals(cursor.getString(0)))
               {
                   dateIdentique = true;
               }
           }

        }






    }

}






