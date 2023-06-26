package com.raisproject.destinasi.ui.destinationlist

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.raisproject.destinasi.adapter.DestinationAdapter
import com.raisproject.destinasi.databinding.ActivityDestinationListBinding
import com.raisproject.destinasi.model.DestinationModel
import com.raisproject.destinasi.model.ProvinceModel
import java.util.Locale

class DestinationListActivity : AppCompatActivity() {

    lateinit var binding: ActivityDestinationListBinding
    var destinationList: ArrayList<DestinationModel> = ArrayList()
    lateinit var destinationAdapter: DestinationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDestinationListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(false)
        }

        val titleNameProv = intent.getStringExtra("provName")
        val titleProvince = "wisata $titleNameProv"
        val titleProv = titleProvince.split(" ").toMutableList()
        var title = ""
        for(word in titleProv){
            title += word.capitalize() +" "
        }
        title = title.trim()
        binding.toolbarTitle.setText(title)

        destinationAdapter = DestinationAdapter(destinationList)

        val idProv = intent.getStringExtra("id_Prov")
        val nameProv = intent.getStringExtra("provName")
        val nameCategory = intent.getStringExtra("categoryName")

        val db = Firebase.firestore
        db.collection("destination")
            .where(Filter.and(
                Filter.equalTo("name_category", nameCategory),
                Filter.equalTo("name_prov", nameProv)
            ))
            .get()
            .addOnSuccessListener { documents ->
                destinationList.clear()

               for (document in documents) {
                    destinationList.add(
                      (DestinationModel(
                          document.id as String,
                          document.data.get("name_destination") as String,
                          document.data.get("name_prov") as String,
                          document.data.get("name_category") as String,
                          document.data.get("address") as String,
                          document.data.get("pict_destination") as String
                       ))
                   )
                }
                binding.rvDestination.apply {
                    layoutManager = GridLayoutManager(context, 2)
                    adapter = destinationAdapter
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }

        binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filterList(newText)
                }
                return true
            }
        })
    }

    fun filterList(query: String) {
        if (query != null) {
            val filteredList = ArrayList<DestinationModel>()
            for (i in destinationList) {
                if (i.name_destination.lowercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                binding.rvDestination.visibility = View.GONE
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            } else {
                binding.rvDestination.visibility = View.VISIBLE
                destinationAdapter.setFilteredList(filteredList)
            }
        }
    }

}