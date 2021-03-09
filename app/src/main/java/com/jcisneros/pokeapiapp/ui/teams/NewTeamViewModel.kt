package com.jcisneros.pokeapiapp.ui.teams

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcisneros.pokeapiapp.datasource.entities.RegionDetail
import com.jcisneros.pokeapiapp.datasource.firebasedatasource.FirebaseService
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

    //data source references
    var datasource: FirebaseService

    init {
        datasource = FirebaseService()
    }

    //save new team pokemon
    fun saveNewTeam(user: User, team: UserTeam){
        viewModelScope.let {
            isSuccess.value = datasource.saveNewTeam(user, team)
        }
    }

}





