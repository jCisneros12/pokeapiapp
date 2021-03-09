package com.jcisneros.pokeapiapp.ui.pokemon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jcisneros.pokeapiapp.datasource.remotedatasource.PokeapiService
import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class PokemonListViewModel : ViewModel() {

    //pokemon of team
    var pokemonTeamList = ArrayList<PokemonInfo>()
    //pokemon of team LiveData
    var pokemonTeamListData = MutableLiveData<PokemonInfo>()

    //instance of moshi
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    //retrofit references
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    //datasource reference
    private val datasource: PokeapiService = retrofit.create(PokeapiService::class.java)

    //live data for pokemon info
    val pokemon = MutableLiveData<PokemonInfo>()

    //this method initialize resquest to API
    fun getPokemonByName(name: String){
        val call = datasource.getPokemonByName(name)

        call.enqueue(object : Callback<PokemonInfo>{
            override fun onResponse(call: Call<PokemonInfo>, response: Response<PokemonInfo>) {
                //set data on live data
                response.body().let { res ->
                    pokemon.postValue(res)
                }
            }
            override fun onFailure(call: Call<PokemonInfo>, t: Throwable) {
                //cancel call
                call.cancel()
            }
        })
    }
}