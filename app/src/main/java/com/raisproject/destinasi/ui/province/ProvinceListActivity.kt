package com.raisproject.destinasi.ui.province

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.raisproject.destinasi.adapter.ProvinceAdapter
import com.raisproject.destinasi.databinding.ActivityProvinceListBinding
import com.raisproject.destinasi.model.ProvinceModel
import java.util.Locale

class ProvinceListActivity : AppCompatActivity() {

    lateinit var binding: ActivityProvinceListBinding
    val provinceList: ArrayList<ProvinceModel> = ArrayList()
    lateinit var provAdapter: ProvinceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProvinceListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(false)
        }

        val categoryValue: String = intent.getStringExtra("category").toString()

        val titleCategory = "Temukan wisata $categoryValue terbaik"
        binding.tvTitleCategory.text = titleCategory

        provAdapter = ProvinceAdapter(provinceList)

        val db = Firebase.firestore
                db.collection("province")
                    .orderBy("name_prov", Query.Direction.ASCENDING)
                    .get()
                    .addOnSuccessListener { documents ->
                        provinceList.clear()

                        for (document in documents) {
                            provinceList.add((ProvinceModel(
                                document.id as String,
                                document.data.get("name_prov") as String,
                                categoryValue,
                                document.data.get("pict_prov") as String
                            )))
                        }
                        binding.rvProvince.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter = provAdapter
                        }
                    }

                    .addOnFailureListener { exception ->
                        Log.w(TAG, "Error getting documents: ", exception)
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
            val filteredList = ArrayList<ProvinceModel>()
            for (i in provinceList) {
                if (i.name_prov.lowercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                binding.rvProvince.visibility = View.GONE
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            } else {
                binding.rvProvince.visibility = View.VISIBLE
                provAdapter.setFilteredList(filteredList)
            }
        }
    }

    // action bar back button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}