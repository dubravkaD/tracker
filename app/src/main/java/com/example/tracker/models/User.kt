package com.example.tracker.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String? = null,
    var email: String? = null,
    var uid: String? = null
) : Parcelable {
    
    constructor() : this(null,null,null) {}
    override fun toString(): String {
        return "User(username=$username, email=$email, uid=$uid)"
    }
}