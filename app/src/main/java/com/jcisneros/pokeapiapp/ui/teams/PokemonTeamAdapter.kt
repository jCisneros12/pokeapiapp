package com.jcisneros.pokeapiapp.ui.teams

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jcisneros.pokeapiapp.R
import com.jcisneros.pokeapiapp.datasource.entities.RegionDetail
import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo

class PokemonTeamAdapter : RecyclerView.Adapter<PokemonTeamAdapter.ViewHolder>() {
    private var pokemons: MutableList<PokemonInfo> = ArrayList()
    lateinit var context: Context

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pokemons[position]
        holder.bind(item, context)
    }

    fun recyclerAdapter(pokemons: MutableList<PokemonInfo>, context: Context) {
        this.pokemons = pokemons
        this.context = context
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_pokemon_team, parent, false))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonName = view.findViewById(R.id.txt_name_pokemon_team) as TextView
        val pokemonType = view.findViewById(R.id.txt_type_pokemon_team) as TextView
        val pokemonPokedex = view.findViewById(R.id.txt_pokedex_pokemon_team) as TextView
        val pokemonImage = view.findViewById(R.id.pokemon_team_image) as ImageView
        val btnActionDelete = view.findViewById(R.id.btn_team_action) as ImageView
        //todo: more views here...

        fun bind(pokemons: PokemonInfo, context: Context) {
            pokemonName.text = pokemons.name
            pokemonType.text = pokemons.types[0].type.name
            pokemonPokedex.text = pokemons.order.toString()
            Glide.with(context).load(pokemons.sprites.sprite).into(pokemonImage)
            //TODO: add btn delete function here...
//            itemView.setOnClickListener(View.OnClickListener { Toast.makeText(context, superhero.superhero, Toast.LENGTH_SHORT).show() })
        }

    }

    override fun getItemCount(): Int {
        return pokemons.size
    }
}