package com.example.ezoassessment.data.models

import android.os.Parcelable

data class User(
    val id: Int,
    val avatar: String,
    val email: String,
    val first_name: String,
    val last_name: String
): java.io.Serializable