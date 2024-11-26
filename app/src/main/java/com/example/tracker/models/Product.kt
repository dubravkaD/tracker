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
    constructor() : this(null, null, null, null, null, null, null, null) {}

    override fun toString(): String {
        return "Product(user=${user.toString()}, name=$name, manufacturer=$manufacturer, countryOfOrigin=$countryOfOrigin, id=$id, barcode=$barcode, category=$category, image=$image)"
    }

}