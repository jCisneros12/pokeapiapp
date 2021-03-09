package com.jcisneros.pokeapiapp.ui.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcisneros.pokeapiapp.datasource.firebasedatasource.FirebaseService
import com.jcisneros.pokeapiapp.datasource.models.User
import com.jcisneros.pokeapiapp.datasource.models.UserTeam
import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class TeamsViewModel : ViewModel() {

    //teams pokemon of user
    var teamsPokemonUser = MutableLiveData<UserTeam>()

    //data source references
    var datasource: FirebaseService

    //initialize references
    init {
        datasource = FirebaseService()
    }

    //get moshi instance
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    //method for get teams of user
    fun getAllTeams(user: User){
        viewModelScope.let {
            datasource.getTeams(user)
            //todo: corvert this data for dysplay teams in UI
//            teamsPokemonUser.value = datasource.getTeams(user) as MutableList<UserTeam>

        }
    }

}