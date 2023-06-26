package com.raisproject.destinasi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raisproject.destinasi.R
import com.raisproject.destinasi.model.DestinationModel
import com.raisproject.destinasi.model.ProvinceModel
import com.raisproject.destinasi.ui.description.DescriptionActivity
import com.raisproject.destinasi.ui.destinationlist.DestinationListActivity
import com.raisproject.destinasi.util.capitalizeWords

class DestinationAdapter(private var destinationList: ArrayList<DestinationModel>) :
    RecyclerView.Adapter<DestinationAdapter.DestinationHolder> (){

    class DestinationHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvNameDestination: TextView = view.findViewById(R.id.tv_name_destination)
        val tvAddress: TextView = view.findViewById(R.id.tv_address)
        val pictDestination: ImageView = view.findViewById(R.id.iv_pict_destination)
        val category: TextView = view.findViewById(R.id.tv_name_category)
    }

    fun setFilteredList(destinationList: ArrayList<DestinationModel>) {
        this.destinationList = destinationList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.destination_item, parent, false)
        return  DestinationHolder(itemView)
    }

    override fun getItemCount(): Int {
        return destinationList.size
    }

    override fun onBindViewHolder(holder: DestinationHolder, position: Int) {
        val currentItem = destinationList[position]

        holder.tvNameDestination.text = currentItem.name_destination!!.capitalizeWords()
        holder.tvAddress.text = currentItem.address
        holder.category.text = currentItem.name_category!!.capitalizeWords()


        Glide.with(holder.itemView.context)
            .load(currentItem.pict_destination)
            .into(holder.pictDestination)

        holder.itemView.setOnClickListener {
            val idDestination = currentItem.id_destination
            val provName = currentItem.name_prov
            val nameCategory = currentItem.name_category

            val intent = Intent(it.context, DescriptionActivity::class.java)
            intent.putExtra("idDestination" ,idDestination)
            it.context.startActivity(intent)
        }
    }
}