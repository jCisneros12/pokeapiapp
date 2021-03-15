package com.jcisneros.pokeapiapp.ui.teams

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jcisneros.pokeapiapp.R
import com.jcisneros.pokeapiapp.datasource.entities.RegionDetail
import com.jcisneros.pokeapiapp.datasource.models.UserTeam

class TeamsAdapter : RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {
    private var teams: MutableList<UserTeam> = ArrayList()
    lateinit var context: Context

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = teams[position]
        holder.bind(item)

    }

    fun recyclerAdapter(teams: MutableList<UserTeam>, context: Context) {
        this.teams = teams
        this.context = context
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_team, parent, false))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teamName = view.findViewById(R.id.text_view_my_team) as TextView
        val btnActionTeam = view.findViewById<ImageView>(R.id.btn_action_look_team)

        //more views here...

        fun bind(teams: UserTeam, /*context: Context*/) {
            teamName.text = teams.teamName
            btnActionTeam.setOnClickListener{ view ->
                val idTeam = bundleOf("idTeam" to teams.teamToken)
                view.findNavController().navigate(R.id.action_navigation_dashboard_to_newTeamFragment, idTeam)
            }
//            itemView.setOnClickListener(View.OnClickListener { Toast.makeText(context, superhero.superhero, Toast.LENGTH_SHORT).show() })
        }

    }

    override fun getItemCount(): Int {
        return teams.size
    }
}