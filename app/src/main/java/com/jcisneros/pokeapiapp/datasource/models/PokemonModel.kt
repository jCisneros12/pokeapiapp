package com.jcisneros.pokeapiapp.datasource.models

import android.util.EventLog
import com.google.firebase.firestore.Exclude

data class PokemonModel(
        val name: String,
        val type: String,
        val pokedex: Int,
        val sprite: String
)