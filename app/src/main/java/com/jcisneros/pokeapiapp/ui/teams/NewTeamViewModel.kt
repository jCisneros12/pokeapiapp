package com.jcisneros.pokeapiapp.ui.teams

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcisneros.pokeapiapp.datasource.entities.RegionDetail
import com.jcisneros.pokeapiapp.datasource.firebasedatasource.FirebaseService
import com.jcisneros.pokeapiapp.datasource.models.PokemonModel
import com.jcisneros.pokeapiapp.datasource.models.User
import com.jcisneros.pokeapiapp.datasource.models.UserTeam
import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo

class NewTeamViewModel : ViewModel() {

    //
    var isSuccess = MutableLiveData<Boolean>()
    //
    lateinit var userData: MutableLiveData<User>
    //team
    lateinit var newTeam: UserTeam
    //pokemon list of the team
    private var _pokemonListTeam = MutableLiveData<List<PokemonModel>>()
    val pokemonListTeam: LiveData<List<PokemonModel>>
        get() = _pokemonListTeam


    //data source references
    var datasource: FirebaseService

    init {
        datasource = FirebaseService()
    }

    fun getPokemon(idTeam: String, user: User){
        viewModelScope.let {
            getPokemonList(idTeam, user)
        }
    }

    //save new team pokemon
    fun saveNewTeam(user: User, team: UserTeam){
        viewModelScope.let {
//            isSuccess.value =
                    datasource.saveNewTeam(user, team)
        }
    }

    fun getPokemonList(idTeam: String, user: User){
        datasource.getPokemonTeamById(idTeam, user).addSnapshotListener { snapshot, e ->
            if(e != null ){
                Log.e("GET POKEMON OF TEAMS", e.toString())
            }
            if(snapshot!=null){
                val pokemonsOfTeam = ArrayList<PokemonModel>()
                val documents = snapshot.documents
                documents.forEach{ document ->
                    val pokemon = PokemonModel(
                            document.get("name").toString(),
                            document.get("type").toString(),
                            Integer.parseInt(document.get("pokedex").toString()),
                            document.get("sprite").toString()

                    )
                    pokemonsOfTeam.add(pokemon)
                    _pokemonListTeam.value = pokemonsOfTeam
                }
            }
        }
    }

}





