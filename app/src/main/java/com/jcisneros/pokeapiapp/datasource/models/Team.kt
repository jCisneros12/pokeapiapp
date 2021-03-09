package com.jcisneros.pokeapiapp.datasource.models

import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo

data class Team(
    val name: String,
    val team: List<PokemonInfo>
)