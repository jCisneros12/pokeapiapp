package com.jcisneros.pokeapiapp.ui.teams

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.jcisneros.pokeapiapp.datasource.firebasedatasource.FirebaseService
import com.jcisneros.pokeapiapp.datasource.models.Team
import com.jcisneros.pokeapiapp.datasource.models.User
import com.jcisneros.pokeapiapp.datasource.models.UserTeam
import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class TeamsViewModel : ViewModel() {

    //teams pokemon of user
    private var _teamList = MutableLiveData<List<UserTeam>>()

    //public list of pokemon teams
    val teamList: LiveData<List<UserTeam>>
        get() = _teamList

    //data source references
    var datasource: FirebaseService

    //initialize references
    init {
        datasource = FirebaseService()
    }

    //method for get teams of user
    fun getAllTeams(user: User){
        viewModelScope.let {
            getTeams(user)
        }
    }

    //get teams from data source method selected (in this case Firestore)
    private fun getTeams(user: User){
        datasource.getAllTeams(user).addOnSuccessListener { res->
            val arrayAllTeams = ArrayList<UserTeam>()
            for (document in res){
                //get pokemons of team
                val listOfPokemon = listOf<PokemonInfo>()
                //var teamsPokemonUser = MutableLiveData<UserTeam>()
                val userTeam = UserTeam(
                        document.data.get("name").toString(),
                        document.id,
                        listOfPokemon
                )
                arrayAllTeams.add(userTeam)
            }
            _teamList.value = arrayAllTeams

        }
    }
}

