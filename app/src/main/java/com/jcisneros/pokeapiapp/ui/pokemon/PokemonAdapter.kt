package com.jcisneros.pokeapiapp.ui.pokemon

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jcisneros.pokeapiapp.R
import com.jcisneros.pokeapiapp.ui.teams.NewTeamViewModel
import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    private var pokemon: MutableList<PokemonInfo> = ArrayList()
    lateinit var context: Context

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pokemon[position]
        holder.bind(item, context)
    }

    fun pokemonAdapter(pokemon: MutableList<PokemonInfo>, context: Context) {
        this.pokemon = pokemon
        this.context = context
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_pokemon, parent, false), /*context*/)
    }

    class ViewHolder(view: View, /*context: Context*/) : RecyclerView.ViewHolder(view) {
        val pokemonName = view.findViewById(R.id.txt_name_pokemon) as TextView
        val pokemonType = view.findViewById<TextView>(R.id.txt_type_pokemon)
        val pokemonPokedex  = view.findViewById<TextView>(R.id.txt_pokedex_pokemon)
        val pokemonImage = view.findViewById<ImageView>(R.id.imageView)
        val btnAction = view.findViewById<ImageView>(R.id.btn_pokemon_action)
        //TODO: add more views here...

        fun bind(pokemon: PokemonInfo, context: Context) {
            pokemonName.text = pokemon.name
            pokemonType.text = pokemon.types[0].type.name
            pokemonPokedex.text = pokemon.order.toString()
            //render pokemon sprite with Glide
            Glide.with(context).load(pokemon.sprites.sprite).into(pokemonImage)
            btnAction.setOnClickListener { view: View ->
                val pokemonSelected: MutableList<PokemonInfo> = mutableListOf(pokemon)
                Toast.makeText(context, "Pokemon added", Toast.LENGTH_SHORT).show()
                View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return pokemon.size
    }
}