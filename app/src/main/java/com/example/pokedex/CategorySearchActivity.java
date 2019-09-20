package com.example.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CategorySearchActivity extends AppCompatActivity {

    String type1;
    String type2;
    CharSequence query;
    ArrayList<Pokemon> filtered;
    ArrayList<Pokemon> pokemon;
    int atk;
    int def;
    int health;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_search);


        query = getIntent().getCharSequenceExtra("query");
        Log.d("XYZ", query.toString());
        Bundle bundle = getIntent().getExtras();
        pokemon = (ArrayList) bundle.getSerializable("list");
        filtered = new ArrayList<Pokemon>();

        String[] types = {"", "Water", "Fire", "Grass", "Fairy", "Ice", "Psychic", "Dark", "Bug", "Steel", "Ghost", "Rock", "Flying", "Dragon", "Normal", "Ground"};

        Spinner spType1 = findViewById(R.id.spType1);
        Spinner spType2 = findViewById(R.id.spType2);
        EditText etAtk = findViewById(R.id.editText4);
        EditText etDef = findViewById(R.id.editText5);
        EditText etHealth = findViewById(R.id.editText6);
        Button goBtn = findViewById(R.id.btnGo);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, types);
        spType1.setAdapter(typeAdapter);
        spType2.setAdapter(typeAdapter);

        spType1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type1 = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spType2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type2 = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        if (etAtk.getText().toString() != null && !etAtk.getText().toString().equals("")) {
            atk = Integer.parseInt(etAtk.getText().toString());
        } else {
            atk = 0;
        }

        if (etDef.getText().toString() != null && !etDef.getText().toString().equals("")) {
            def = Integer.parseInt(etDef.getText().toString());
        } else {
            def = 0;
        }

        if (etHealth.getText().toString() != null && !etHealth.getText().toString().equals("")) {
            health = Integer.parseInt(etHealth.getText().toString());
        } else {
            health = 0;
        }

        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter();
                Intent intent = new Intent(CategorySearchActivity.this, MainActivity.class);
                intent.putExtra("key", "category");
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", filtered);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void filter() {
        if (query == null || query.length() == 0) {
            filtered.addAll(pokemon);
        } else {
            String filterString = query.toString().toLowerCase().trim();
            for (Pokemon poke: pokemon) {
                if (poke.getName().toLowerCase().contains(filterString)) {
                    filtered.add(poke);
                }
            }
        }

        ArrayList<Pokemon> oldFiltered = new ArrayList<>(filtered);

        for (Pokemon poke: oldFiltered) {
            if (!type1.equals("") && !poke.getType().contains(type1)) {
                filtered.remove(poke);
                continue;
            }
            if (!type2.equals("") && !poke.getType().contains(type2)) {
                filtered.remove(poke);
                continue;
            }
            if ((atk != 0) && Integer.parseInt(poke.getAttack()) < atk) {
                filtered.remove(poke);
                continue;
            }
            if ((def != 0) && Integer.parseInt(poke.getDefense()) < def) {
                filtered.remove(poke);
                continue;
            }
            if ((health != 0) && Integer.parseInt(poke.getHp()) < health) {
                filtered.remove(poke);
                continue;
            }
        }
    }
}
