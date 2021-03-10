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


    //array
    val arrayTeam = ArrayList<UserTeam>()

    //teams pokemon of user
    var teamsPokemonUserList = MutableLiveData<List<UserTeam>>()

    //teams pokemon of user
    var teamsPokemonUser = MutableLiveData<UserTeam>()

    //data source references
    var datasource: FirebaseService

    //initialize references
    init {
        datasource = FirebaseService()
    }


    //method for get teams of user
    fun getAllTeams(user: User){
        viewModelScope.let {
            datasource.getAllTeams(user)
            teamsPokemonUserList.postValue(arrayTeam)

        }
    }

    //firestore db instance
    private val db = FirebaseFirestore.getInstance()

    fun getAllTeamsFirebase(user: User) {
        db.collection("users").document(user.emal)
            .collection("teams")
            .get().addOnSuccessListener { res->
                for (document in res){
                    //get pokemons of team
                    val listOfPokemon = listOf<PokemonInfo>()
                    //var teamsPokemonUser = MutableLiveData<UserTeam>()
                        val userTeam = UserTeam(
                            document.data.get("teamName").toString(),
                            document.data.get("teamToken").toString(),
                            listOfPokemon
                        )
                    if(res.size()!=arrayTeam.size){
                        arrayTeam.add(userTeam)
                    }

                }
            }

    }

}

