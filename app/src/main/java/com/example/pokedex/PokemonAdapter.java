package com.example.pokedex;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.CustomViewHolder> implements Filterable {

    Context context;

    private ArrayList<Pokemon> allPokemon;
    private ArrayList<Pokemon> filteredPokemon;


    public PokemonAdapter(Context context, ArrayList<Pokemon> pokemonList) {
        this.context = context;
        allPokemon = new ArrayList<>();
        allPokemon.addAll(pokemonList);
        filteredPokemon = new ArrayList<>();
        filteredPokemon.addAll(pokemonList);
        Log.d("XYZ", filteredPokemon.get(0).getName());
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pokemon_linear, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.CustomViewHolder holder, int position) {
        Pokemon pokemon = filteredPokemon.get(position);
        holder.bind(pokemon);
    }

    @Override
    public int getItemCount() {
        Log.d("XYZ", ((Integer) filteredPokemon.size()).toString());
        return filteredPokemon.size();
    }

    @Override
    public Filter getFilter() {
        return pokemonFilter;
    }

    private Filter pokemonFilter = new Filter() {

        ArrayList<Pokemon> filtered;

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            filtered = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filtered.addAll(allPokemon);
            } else {
                String filterString = charSequence.toString().toLowerCase().trim();
                for (Pokemon poke: allPokemon) {
                    if (poke.getName().toLowerCase().contains(filterString)) {
                        filtered.add(poke);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtered;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredPokemon.clear();
            filteredPokemon.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPokemon;
        private TextView tvName;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ivPokemon = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.tvName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Pokemon poke = filteredPokemon.get(position);
                        Intent intent = new Intent(context, ProfileActivity.class);
                        intent.putExtra("pokemon", Parcels.wrap(poke));
                        context.startActivity(intent);
                    }
                }
            });
        }

        public void bind(Pokemon pokemon) {
            tvName.setText(pokemon.getName());
            String url = "http://img.pokemondb.net/artwork/" + pokemon.getName().toLowerCase();
            url = url.concat(".jpg");
            if (Patterns.WEB_URL.matcher(url).matches()) {
                Glide.with(context)
                        .load(url)
                        .into(ivPokemon);
            } else {
                Glide.with(context)
                        .load(R.drawable.question)
                        .into(ivPokemon);
            }
            Log.d("XYZ", url);
        }
    }


}
