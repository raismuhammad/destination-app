package com.raisproject.destinasi.model

data class DestinationModel(
    val id_destination: String,
    val name_destination: String,
    val name_prov: String?,
    val name_category: String?,
    val address: String?,
    val pict_destination: String?
)
