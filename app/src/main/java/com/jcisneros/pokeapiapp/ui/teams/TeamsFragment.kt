package com.jcisneros.pokeapiapp.ui.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jcisneros.pokeapiapp.R
import com.jcisneros.pokeapiapp.datasource.models.User
import com.jcisneros.pokeapiapp.datasource.models.UserTeam
import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo

class TeamsFragment : Fragment() {
    //view model if teams
    private lateinit var teamsViewModel: TeamsViewModel

    //declare recycler adapter
    lateinit var mRecyclerView : RecyclerView
    private lateinit var mAdapter : TeamsAdapter

    //references to components UI
    lateinit var fabAddNewTeam: FloatingActionButton

    //user email login
    lateinit var userEmailLogin: User

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        //init default user
        userEmailLogin = User("default@user.com", "1234asdf")

        //references to view model
        teamsViewModel =
                ViewModelProvider(this).get(TeamsViewModel::class.java)

        //get teams list
        teamsViewModel.getAllTeams(userEmailLogin)

        val root = inflater.inflate(R.layout.fragment_teams, container, false)

        //prepare recycler adapter
        mRecyclerView = root.findViewById(R.id.recycler_my_teams) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(root.context)
        mAdapter = TeamsAdapter()
        mRecyclerView.adapter = mAdapter

        //go to new team fragment
        fabAddNewTeam = root.findViewById(R.id.fab_add_new_team)
        fabAddNewTeam.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_navigation_dashboard_to_newTeamFragment)
        }

        //observer the list of teams and send to recycler adapter
        teamsViewModel.teamList.observe(viewLifecycleOwner, Observer { teams ->
            val pokemonList = teams as MutableList<UserTeam>
            mAdapter.recyclerAdapter(pokemonList, root.context)
        })

        return root
    }
}