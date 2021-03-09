package com.jcisneros.pokeapiapp.datasource.models

import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo

data class UserTeam (
    val teamName: String,
    val teamToken: String,
    val pokemonTeam: List<PokemonInfo>
)