package com.jcisneros.pokeapiapp.ui.pokemon

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jcisneros.pokeapiapp.R
import com.jcisneros.pokeapiapp.ui.teams.NewTeamViewModel
import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo
import com.jcisneros.poketeamsapp.datasources.entities.Sprites
import com.jcisneros.poketeamsapp.datasources.entities.Type
import com.jcisneros.poketeamsapp.datasources.entities.Types

class PokemonListFragment : Fragment() {

    //declare recycler adapter and ViewModel
    private lateinit var viewModel: PokemonListViewModel
    lateinit var mRecyclerView : RecyclerView
    private lateinit var mAdapter : PokemonAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //inflate layout
        val root = inflater.inflate(R.layout.fragment_pokemon_list, container, false)

        //init reference to ViewModel
        viewModel = ViewModelProvider(this).get(PokemonListViewModel::class.java)

        //prepare recycler adapter
        mRecyclerView = root.findViewById(R.id.recycler_list_pokemon) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(root.context)
        mAdapter = PokemonAdapter()
        mRecyclerView.adapter = mAdapter

        //get list of regions from ViewModel
        viewModel.getPokemonByName("pikachu") //todo: set name from searc input view
        //we observe changes in the list and set data to recycler
        viewModel.pokemon.observe(viewLifecycleOwner, Observer { pokemon ->
            val pokemonData: MutableList<PokemonInfo> = mutableListOf(pokemon)
        })

        return root
    }
}