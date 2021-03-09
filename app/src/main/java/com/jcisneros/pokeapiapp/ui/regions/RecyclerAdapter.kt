package com.jcisneros.pokeapiapp.ui.regions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jcisneros.pokeapiapp.R
import com.jcisneros.pokeapiapp.datasource.entities.RegionDetail

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private var regions: MutableList<RegionDetail> = ArrayList()
    lateinit var context: Context

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = regions[position]
        holder.bind(item)

    }

    fun recyclerAdapter(regions: MutableList<RegionDetail>, context: Context) {
        this.regions = regions
        this.context = context
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_region, parent, false))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val regionName = view.findViewById(R.id.text_view_region) as TextView
        //more views here...

        fun bind(region: RegionDetail, /*context: Context*/) {
            regionName.text = region.name
//            itemView.setOnClickListener(View.OnClickListener { Toast.makeText(context, superhero.superhero, Toast.LENGTH_SHORT).show() })
        }

    }

    override fun getItemCount(): Int {
        return regions.size
    }
}