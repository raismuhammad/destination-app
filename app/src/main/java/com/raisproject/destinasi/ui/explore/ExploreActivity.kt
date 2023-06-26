package com.raisproject.destinasi.ui.explore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.raisproject.destinasi.databinding.ActivityExploreBinding
import com.raisproject.destinasi.ui.province.ProvinceListActivity
import java.util.Locale.Category

class ExploreActivity : AppCompatActivity() {

    lateinit var binding: ActivityExploreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExploreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(false)
        }

        val category = "category"

        binding.btnAgrowisata.setOnClickListener {
            val intent = Intent(this, ProvinceListActivity::class.java)
            intent.putExtra(category, "agrowisata")
            startActivity(intent)
        }

        binding.btnBudaya.setOnClickListener {
            val intent = Intent(this, ProvinceListActivity::class.java)
            intent.putExtra(category, "budaya")
            startActivity(intent)
        }

        binding.btnEdukasi.setOnClickListener {
            val intent = Intent(this, ProvinceListActivity::class.java)
            intent.putExtra(category, "edukasi")
            startActivity(intent)
        }

        binding.btnKuliner.setOnClickListener {
            val intent = Intent(this, ProvinceListActivity::class.java)
            intent.putExtra(category, "kuliner")
            startActivity(intent)
        }

        binding.btnReligi.setOnClickListener {
            val intent = Intent(this, ProvinceListActivity::class.java)
            intent.putExtra(category, "religi")
            startActivity(intent)
        }
        binding.btnPantai.setOnClickListener {
            val intent = Intent(this, ProvinceListActivity::class.java)
            intent.putExtra(category, "pantai")
            startActivity(intent)
        }

        binding.btnGunung.setOnClickListener {
            val intent = Intent(this, ProvinceListActivity::class.java)
            intent.putExtra(category, "gunung")
            startActivity(intent)
        }

        binding.btnSungai.setOnClickListener {
            val intent = Intent(this, ProvinceListActivity::class.java)
            intent.putExtra(category, "sungai")
            startActivity(intent)
        }

    }
}