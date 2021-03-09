package com.jcisneros.pokeapiapp.ui.regions

import android.util.Log
import androidx.lifecycle.*
import com.jcisneros.pokeapiapp.datasource.entities.Region
import com.jcisneros.pokeapiapp.datasource.entities.RegionDetail
import com.jcisneros.pokeapiapp.datasource.remotedatasource.PokeapiService
import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo
//import com.jcisneros.poketeamsapp.datasources.remotedatasource.PokeApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RegionsViewModel : ViewModel() {

     //get moshi instance
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

     //live data for regions info
     val regionList = MutableLiveData<MutableList<RegionDetail>>()

     //initialize call to API
     fun getRegionList(){
          val call = datasource.getRegionList()
          call.enqueue(object : Callback<Region>{
               override fun onResponse(call: Call<Region>, response: Response<Region>) {
                    response.body()?.results?.let { list ->
                         regionList.postValue(list as MutableList<RegionDetail>?)
                    }
               }
               override fun onFailure(call: Call<Region>, t: Throwable) {
                    call.cancel()
               }

          })
     }
}


//     // The internal MutableLiveData that stores the most recent response
//     // The external immutable LiveData for the response
//     val responseRegions: LiveData<MutableList<RegionDetail>>
//          get() = _responseRegions
//
//     //execute in second thread with corutine
//     private val _responseRegions = MutableLiveData<MutableList<RegionDetail>>().apply {
//          viewModelScope.launch {
//               try {
//                    value =  PokeApi.retrofitService.getProperties().results.toMutableList()
//               } catch (e: Exception) {
//                    Log.e("POKEAPI RESPONSE ERROR", e.toString())
//               }
//          }
//
//     }


