package com.jcisneros.pokeapiapp.datasource.remotedatasource

import com.jcisneros.pokeapiapp.datasource.entities.Region
import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeapiService {
    //for get a pokemon by name
    @GET("pokemon/{name}")
    fun getPokemonByName(@Path("name") name: String): Call<PokemonInfo>

    //for get total list of regions
    @GET("region")
    fun getRegionList(): Call<Region>

    //TODO: add more end points here...
}