package com.example.tracker.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var id: String? = null,
    var user: User? = null,
    var name: String? = null,
    var manufacturer: String? = null,
    var countryOfOrigin: String? = null,
    var barcode: String? = null,
    var category: String? = null,
    var image: String? = null
) : Parcelable {


//    constructor() : this() {}
//    constructor(
//        id: String,
//        name: String,
//        manufacturer: String,
//        countryOfOrigin: String,
//        barcode: String,
//        category: String
//    ) : this() {
//        this.id = id
//        this.name = name
//        this.manufacturer = manufacturer
//        this.countryOfOrigin = countryOfOrigin
//        this.barcode = barcode
//        this.category = category
//    }

//    constructor(
//        id: String,
//        user: User,
//        name: String,
//        manufacturer: String,
//        countryOfOrigin: String,
//        barcode: String,
//        category: String,
//        image: String
//    ) : this() {
//        this.id = id
//        this.user = user
//        this.name = name
//        this.manufacturer = manufacturer
//        this.countryOfOrigin = countryOfOrigin
//        this.barcode = barcode
//        this.category = category
//        this.image = image
//    }


}