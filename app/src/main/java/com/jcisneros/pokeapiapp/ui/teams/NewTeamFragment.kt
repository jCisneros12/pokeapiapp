package com.jcisneros.pokeapiapp.ui.teams

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jcisneros.pokeapiapp.R
import com.jcisneros.pokeapiapp.datasource.entities.RegionDetail
import com.jcisneros.pokeapiapp.datasource.models.User
import com.jcisneros.pokeapiapp.datasource.models.UserTeam
import com.jcisneros.pokeapiapp.ui.pokemon.PokemonActivity
import com.jcisneros.pokeapiapp.ui.pokemon.PokemonListViewModel
import com.jcisneros.pokeapiapp.ui.regions.RecyclerAdapter
import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo
import com.jcisneros.poketeamsapp.datasources.entities.Sprites
import com.jcisneros.poketeamsapp.datasources.entities.Type
import com.jcisneros.poketeamsapp.datasources.entities.Types
import kotlinx.coroutines.channels.ticker
import kotlin.random.Random

class NewTeamFragment : Fragment() {

    private val SECOND_ACTIVITY_REQUEST_CODE = 0

    //declare recycler adapter and ViewModel
    lateinit var mRecyclerView : RecyclerView
    private lateinit var mAdapter : PokemonTeamAdapter
    private lateinit var viewModelNewTeam: NewTeamViewModel
    private lateinit var viewModelPokemon: PokemonListViewModel

    //references to components UI
    lateinit var btnAddPokemonToTeam: Button
    lateinit var btnSaveTeam: Button
    lateinit var btnDeleteTeam: Button
    lateinit var editTxtTeamName: EditText
    //user email login
    lateinit var userEmailLogin: User

    var pokemonsSelected = ArrayList<PokemonInfo>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_new_team, container, false)
        //init components from UI
        btnAddPokemonToTeam = root.findViewById(R.id.add_pokemon_to_team)
        btnSaveTeam = root.findViewById(R.id.btn_save_team)
        btnDeleteTeam = root.findViewById(R.id.btn_delete_team)
        editTxtTeamName = root.findViewById(R.id.edit_text_name_team)

        //init default user
        userEmailLogin = User("default@user.com", "1234asdf")
        //init viewmodels
        viewModelPokemon = ViewModelProvider(this).get(PokemonListViewModel::class.java)
        viewModelNewTeam = ViewModelProvider(this).get(NewTeamViewModel::class.java)

        //binding adapter with recycler
        mRecyclerView = root.findViewById(R.id.recycler_pokemon_team) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(root.context)
        mAdapter = PokemonTeamAdapter()
        mRecyclerView.adapter = mAdapter

        btnAddPokemonToTeam.setOnClickListener{ view: View ->
            // Start the pokemon search Activity
            val intent = Intent(root.context, PokemonActivity::class.java)
            startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE)
        }

        viewModelPokemon.pokemonTeamListData.observe(viewLifecycleOwner, Observer { pokemonTeam ->
            val listToAdapter: MutableList<PokemonInfo> = pokemonsSelected
            mAdapter.recyclerAdapter(listToAdapter, root.context)
            //dont show save button if team < 3 and > 6
            if(pokemonsSelected.size<3){
                btnSaveTeam.visibility = GONE
            }else{
                btnSaveTeam.visibility = VISIBLE
            }
        })

        btnSaveTeam.setOnClickListener{
            if(editTxtTeamName.text.isEmpty()){
                Toast.makeText(root.context, "Provide a team name", Toast.LENGTH_LONG).show()
            }else{
                //try to save new team
                viewModelNewTeam.saveNewTeam(
                    userEmailLogin,
                    UserTeam(
                        editTxtTeamName.text.toString(),
                        randomToken(),
                        pokemonsSelected
                    )
                )
            }
        }

        //observe data form view model
        viewModelNewTeam.isSuccess.observe(viewLifecycleOwner, Observer { success ->
//            if (success){
                Toast.makeText(root.context, "Team Saved Success!", Toast.LENGTH_LONG).show()
                btnSaveTeam.visibility = GONE
//            }
        })

        return root
    }

    // This method is called when the second activity finishes
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            viewModelPokemon = ViewModelProvider(this).get(PokemonListViewModel::class.java)
            if (resultCode == 1) {
                // Get String data from Intent
                val returnPokemon = data!!.getStringExtra("pokemonName").toString()
                val pokemonType = data?.getStringExtra("pokemonType").toString()
                val pokemonPokedex = data?.getStringExtra("pokemonPokedex").toString()
                val pokemonImage = data?.getStringExtra("pokemonImage").toString()
                val types: List<Types> = listOf(Types(Type(pokemonType)))
                val pokemonAdd = PokemonInfo(returnPokemon, pokemonPokedex.toInt(), types,Sprites(pokemonImage))

                if(pokemonsSelected.size<6){
                    pokemonsSelected.add(pokemonAdd)
                }
                viewModelPokemon.pokemonTeamList.add(pokemonAdd)
                viewModelPokemon.pokemonTeamListData.postValue(pokemonAdd)

            }
        }
    }

    //generate random token
    private fun randomToken(): String{
        var randomToken: String
        var randomDigit = 0
        for (i in 1..3){
            randomDigit =+ Random.nextInt(1, 9)
        }
        randomToken = "Team-"+ randomDigit.toString()
        return randomToken
    }


}