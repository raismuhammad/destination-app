package com.raisproject.destinasi.util

fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")