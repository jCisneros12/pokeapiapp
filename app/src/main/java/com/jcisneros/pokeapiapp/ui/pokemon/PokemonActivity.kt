package com.jcisneros.pokeapiapp.ui.pokemon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jcisneros.pokeapiapp.R
import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo
import java.util.*

class PokemonActivity : AppCompatActivity() {

    //declare recycler adapter and ViewModel
    private lateinit var viewModel: PokemonListViewModel
    lateinit var mRecyclerView : RecyclerView
    private lateinit var mAdapter : PokemonAdapter

    //declare widget componets
    lateinit var buttonSearch: Button
    lateinit var editTxtPokemon: EditText
    lateinit var fabAddToTeam: FloatingActionButton

    //pokemon selected
    lateinit var pokemonSelected: PokemonInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        //init componets ui
        buttonSearch = findViewById(R.id.btn_search_pokemon)
        editTxtPokemon = findViewById(R.id.edit_text_pokemon_search)
        fabAddToTeam = findViewById(R.id.fab_add_pokemon)

        //init reference to ViewModel
        viewModel = ViewModelProvider(this).get(PokemonListViewModel::class.java)

        //prepare recycler adapter
        mRecyclerView = findViewById(R.id.recycler_list_pokemon) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = PokemonAdapter()
        mRecyclerView.adapter = mAdapter

        //we observe changes in the list and set data to recycler
        viewModel.pokemon.observe(this, Observer { pokemon ->
            val pokemonData: MutableList<PokemonInfo> = mutableListOf(pokemon)
            mAdapter.pokemonAdapter(pokemonData, this)
            pokemonSelected = pokemon

        })

        lateinit var pokemonName: String
        fabAddToTeam.visibility = GONE
        //search pokemon by name
        buttonSearch.setOnClickListener{
            pokemonName = editTxtPokemon.text.trim().toString().toLowerCase(Locale.ROOT)
            //get list of regions from ViewModel
            viewModel.getPokemonByName(pokemonName)

            fabAddToTeam.visibility = VISIBLE
            if(pokemonName.isEmpty()){
                Toast.makeText(this, "Provide a pokemon name", Toast.LENGTH_LONG).show()
            }else{
                //get list of regions from ViewModel
                viewModel.getPokemonByName(pokemonName)
            }
        }

        fabAddToTeam.setOnClickListener{
            pokemonName = editTxtPokemon.text.trim().toString().toLowerCase(Locale.ROOT)
            // Put the String to pass back into an Intent and close this activity
            val intent = Intent()
            //pokemon data selected
            intent.putExtra("pokemonName", pokemonName)
            intent.putExtra("pokemonType", pokemonSelected.types[0].type.name)
            intent.putExtra("pokemonPokedex", pokemonSelected.order.toString())
            intent.putExtra("pokemonImage", pokemonSelected.sprites.sprite)
            //ser result
            setResult(1, intent)
            finish()
        }

    }
}