package com.jcisneros.poketeamsapp.datasources.entities

import com.squareup.moshi.Json

data class PokemonInfo (
//    val result: Json
    val name: String,
    val order: Int,
    val types: List<Types>,
    val sprites: Sprites
)

data class Types(
    val type: Type,
)

data class Type(
    val name: String,
)

data class Sprites(
    //front_default
    @Json(name = "front_default") val sprite: String
)