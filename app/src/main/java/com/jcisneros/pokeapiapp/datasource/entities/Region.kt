package com.jcisneros.pokeapiapp.datasource.entities

data class Region (
//    val count: String,
    val results: List<RegionDetail>
)

data class RegionDetail(
    val name: String
)
