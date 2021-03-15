package com.jcisneros.pokeapiapp.datasource.models

import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo

data class Team(
    //team name
    val name: String,
    //team unique token
    val token: String
)