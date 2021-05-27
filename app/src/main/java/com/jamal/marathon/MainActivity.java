package com.jamal.marathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        Button buttonList = (Button) findViewById(R.id.buttonList);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCondidat();
            }
        });
        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openViewData();
            }
        });
    }
    public void addCondidat() {

        EditText EditText_nom = (EditText) findViewById(R.id.editTextTextPersonNom);
        EditText EditText_prenom = (EditText) findViewById(R.id.editTextTextPersonPrenom);
        EditText EditText_email = (EditText) findViewById(R.id.editTextTextPersonEmail);

        String nom = EditText_nom.getText().toString();
        String prenom = EditText_prenom.getText().toString();
        String email = EditText_email.getText().toString();


        TextView textView_nom = (TextView) findViewById(R.id.textViewTextPersonNomMessage);
        TextView textView_Prenom = (TextView) findViewById(R.id.textViewTextPersonPrenomMessage);
        TextView textView_email = (TextView) findViewById(R.id.textViewTextPersonEmailMessage);


        if (matchRegex("^[A-Za-z ]{3,}$", nom)) {
            textView_nom.setVisibility(View.INVISIBLE);
            if (matchRegex("^[A-Za-z ]{3,}$", prenom)) {
                textView_Prenom.setVisibility(View.INVISIBLE);
                if (matchRegex("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", email)) {
                    textView_email.setVisibility(View.INVISIBLE);
                    Database database = new Database(this);
                    if (database.insertCondidat(new Condidat(nom, prenom, email))){
                        Toast.makeText(this, "ajouter terminer avec succés", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "erreur d'ajouter", Toast.LENGTH_SHORT).show();
                    }
                    EditText_nom.setText("");
                    EditText_prenom.setText("");
                    EditText_email.setText("");
                } else {
                    textView_email.setVisibility(View.VISIBLE);
                }
            } else {
                textView_Prenom.setVisibility(View.VISIBLE);
            }
        } else {
            textView_nom.setVisibility(View.VISIBLE);
        }
    }

    public void openViewData(){
        Intent intent = new Intent(this, ViewData.class);
        startActivity(intent);
    }

    public boolean matchRegex(String regex, String text){
        Pattern VALID_Match_ADDRESS_REGEX = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_Match_ADDRESS_REGEX.matcher(text);
        return matcher.find();
    }
}