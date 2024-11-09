package com.example.tracker.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String? = null,
    var email: String? = null,
    var uid: String? = null
) : Parcelable {


//    constructor() : this() {}
//    constructor(username:String, email:String, uid:String) : this() {
//        this.username = username
//        this.email = email
//        this.uid = uid
//    }
}