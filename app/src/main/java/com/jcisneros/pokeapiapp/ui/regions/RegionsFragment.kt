package com.jcisneros.pokeapiapp.ui.regions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jcisneros.pokeapiapp.R
import com.jcisneros.pokeapiapp.ui.login.LoginActivity
import com.jcisneros.pokeapiapp.ui.pokemon.PokemonActivity

class RegionsFragment : Fragment() {

    //declare recycler adapter and ViewModel
    lateinit var mRecyclerView : RecyclerView
    private lateinit var mAdapter : RecyclerAdapter
    private lateinit var regionsViewModel: RegionsViewModel

    lateinit var fabFormLogin: FloatingActionButton

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        //init reference to ViewModel
        regionsViewModel =
                ViewModelProvider(this).get(RegionsViewModel::class.java)

        //inflate layout
        val root = inflater.inflate(R.layout.fragment_regions, container, false)

        fabFormLogin = root.findViewById(R.id.fab_login_form)
        fabFormLogin.setOnClickListener{
            val intent = Intent(root.context, LoginActivity::class.java)
            startActivity(intent)
        }
        //prepare recycler adapter
        mRecyclerView = root.findViewById(R.id.recycler_region) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(root.context)
        mAdapter = RecyclerAdapter()
        mRecyclerView.adapter = mAdapter

        //get list of regions from ViewModel
        regionsViewModel.getRegionList()
        //we observe changes in the list and set data to recycler
        regionsViewModel.regionList.observe(viewLifecycleOwner, Observer { regionList ->
            mAdapter.recyclerAdapter(regionList, root.context)
        })

        return root
    }

}


//        //observe to list of regions LiveData in the ViewModel
//        regionsViewModel.responseRegions.observe(viewLifecycleOwner,  Observer{
//            mAdapter.recyclerAdapter(it, root.context)
//        })
