package com.jcisneros.pokeapiapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jcisneros.pokeapiapp.datasource.firebasedatasource.FirebaseService
import com.jcisneros.pokeapiapp.datasource.models.User
import com.jcisneros.pokeapiapp.ui.pokemon.PokemonListViewModel
import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo
import kotlinx.coroutines.launch

class LoginViewModel(): ViewModel() {

    //User login
     var userData = MutableLiveData< User>()
    //for user register
    var isRegister = MutableLiveData<Boolean>()

    //data source references
    var datasource: FirebaseService

    init {
        datasource = FirebaseService()
    }

    //login user
    fun loginUser(user: User){
        viewModelScope.let {
           userData.value = datasource.login(user)
        }
    }
    //login user
    fun registerUser(user: User){
        viewModelScope.launch {
            isRegister.value = datasource.register(user)
        }
    }

}
