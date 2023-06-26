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
import com.raisproject.destinasi.model.ProvinceModel
import com.raisproject.destinasi.ui.destinationlist.DestinationListActivity
import com.raisproject.destinasi.ui.explore.ExploreActivity
import com.raisproject.destinasi.ui.province.ProvinceListActivity
import com.raisproject.destinasi.util.capitalizeWords

class ProvinceAdapter(private var provinceList: ArrayList<ProvinceModel>) :
    RecyclerView.Adapter<ProvinceAdapter.ProvinceHolder>() {

    class ProvinceHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameProv: TextView = view.findViewById(R.id.tv_name_province)
        val pictProv: ImageView = view.findViewById(R.id.iv_pict_province)

    }

    fun setFilteredList(provinceList: ArrayList<ProvinceModel>) {
        this.provinceList = provinceList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvinceHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.province_item, parent, false)
        return ProvinceHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProvinceHolder, position: Int) {
        val currentItem = provinceList[position]

        holder.nameProv.text = currentItem.name_prov!!.capitalizeWords()
        Glide.with(holder.itemView.context)
            .load(currentItem.pict_prov)
            .into(holder.pictProv)

        holder.itemView.setOnClickListener {
            val provName =currentItem.name_prov
            val idProv = currentItem.id_prov
            val nameCategory = currentItem.name_category

            val intent = Intent(it.context, DestinationListActivity::class.java)
            intent.putExtra("provName" ,provName)
            intent.putExtra("id_prov", idProv)
            intent.putExtra("categoryName", nameCategory)
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return provinceList.size
    }
}