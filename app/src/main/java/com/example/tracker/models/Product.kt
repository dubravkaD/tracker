package com.example.productorigintracker.models

import com.example.tracker.util.enums.Category

class Product {

    private lateinit var id: String
    private lateinit var name: String
    private lateinit var manufacturer: String
    private lateinit var countryOfOrigin: String
    private lateinit var barcode: String
    private lateinit var category: String

    constructor(){}
    constructor(
        id: String,
        name: String,
        manufacturer: String,
        countryOfOrigin: String,
        barcode: String,
        category: String
    ) {
        this.id = id
        this.name = name
        this.manufacturer = manufacturer
        this.countryOfOrigin = countryOfOrigin
        this.barcode = barcode
        this.category = category
    }

}