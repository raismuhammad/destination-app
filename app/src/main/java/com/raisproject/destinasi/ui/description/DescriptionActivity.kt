package com.raisproject.destinasi.ui.description

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.raisproject.destinasi.databinding.ActivityDescriptionBinding
import com.raisproject.destinasi.util.capitalizeWords


class DescriptionActivity : AppCompatActivity() {

    lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(false)
        }


        supportActionBar?.apply {
            title = ""

            // show back button on toolbar
            // on back button press, it will navigate to parent activity
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val idDestination = intent.getStringExtra("idDestination")

        val db = Firebase.firestore
        val docRef = db.collection("destination").document(idDestination.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val image = document.getString("pict_destination")
                    val nameCategory = document.getString("name_category")!!.capitalizeWords()
                    val nameDestination = document.getString("name_destination")!!.capitalizeWords()
                    val description = document.getString("description")!!.capitalize()
                    val openHours = document.getString("opening_hours")
                    val closeHours = document.getString("closing_hours")
                    val facility = document.getString("facility")!!.capitalizeWords()
                    val address = document.getString("address")!!.capitalizeWords()
                    val numberPhone = document.getString("number_phone")
                    val website = document.getString("website")
                    val facebook = document.getString("description")
                    val instagram = document.getString("description")
                    val twitter = document.getString("description")

                    Glide.with(this).load(image).into(binding.ivPictDestination)
                    binding.tvNameCategory.setText(nameCategory)
                    binding.tvNameDestination.setText(nameDestination)
                    binding.tvDescription.setText(description)
                    binding.tvOpeningHours.setText(openHours)
                    binding.tvClosingHours.setText(closeHours)
                    binding.tvFacility.setText(facility)
                    binding.tvAddress.setText(address)
                    binding.btnPhoneNumber.setOnClickListener {
                        if (numberPhone != null) {
                            val intentDial = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + numberPhone))
                            startActivity(intentDial)
                        } else {
                            Toast.makeText(this, "Nomor telpon tidak tersedia", Toast.LENGTH_SHORT).show()
                        }
                    }
                    binding.btnWebsite.setOnClickListener {
                        if (website != null) {
                            val browserIntent =
                                Intent(Intent.ACTION_VIEW, Uri.parse(website))
                            startActivity(browserIntent)
                        } else {
                            Toast.makeText(this, "Website tidak tersedia", Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

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