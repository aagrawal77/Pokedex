package com.example.pokedex;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    String jsonString;
    ArrayList<Pokemon> pokemon;
    ArrayList filtered;
    RecyclerView recyclerView;
    PokemonAdapter adapter;
    SearchView searchView;
    Button btnSearch;
    Button btnGo;
    boolean category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        category = false;
        pokemon = new ArrayList<Pokemon>();
        searchView = findViewById(R.id.searchView);
        btnSearch = findViewById(R.id.btnSearch);
        btnGo = findViewById(R.id.btnFinal);
        jsonString = loadJSONFromAsset(getApplicationContext());
        JSONObject jObject = null;
        try {
            jObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            Log.d("XYZ", "oops");
            e.printStackTrace();
        }

        Iterator<String> keys = jObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Pokemon newPoke = new Pokemon();
            JSONObject jPoke = null;
            try {
                jPoke = jObject.getJSONObject(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jPoke != null) {
                newPoke.setName(key);
                try {
                    newPoke.setId(jPoke.getString("#"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    newPoke.setAttack(jPoke.getString("Attack"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    newPoke.setDefense(jPoke.getString("Defense"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    newPoke.setSpattack(jPoke.getString("Sp. Atk"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    newPoke.setSpdef(jPoke.getString("Sp. Def"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    newPoke.setHp(jPoke.getString("HP"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    newPoke.setSpecies(jPoke.getString("Species"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    newPoke.setSpeed(jPoke.getString("Speed"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    newPoke.setTotal(jPoke.getString("Total"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    newPoke.setType(jPoke.getJSONArray("Type"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pokemon.add(newPoke);
            }
        }

        setSVListener();
        setBtnListener();
        setBtnGoListener();

        if (getIntent() != null && getIntent().getStringExtra("key") != null) {
            filtered = (ArrayList) getIntent().getExtras().getSerializable("list");
            category = true;
            setUpRV(filtered);
        } else {
            setUpRV(pokemon);
        }

    }

    private void setUpRV(ArrayList list) {
        recyclerView = findViewById(R.id.rvPokemon);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PokemonAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    private void setSVListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("key", "main");
                intent.putExtra("query", searchView.getQuery());
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", pokemon);
                intent.putExtras(bundle);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void setBtnListener() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategorySearchActivity.class);
                intent.putExtra("query", searchView.getQuery());
                intent.putExtra("key", "main");
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", pokemon);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setBtnGoListener() {
        Log.d("XYZ", "good");
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("query", searchView.getQuery());
                intent.putExtra("key", "main");
                Bundle bundle = new Bundle();
                if (category) {
                    bundle.putSerializable("list", filtered);
                } else {
                    bundle.putSerializable("list", pokemon);
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("pokeData.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
