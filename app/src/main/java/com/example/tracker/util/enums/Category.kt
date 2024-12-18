package com.example.tracker.util.enums

enum class Category(val displayName: String) {
    ELECTRONICS("Electronics"),
    CLOTHING("Clothing"),
    FOOD("Food"),
    DRINKS("Drinks"),
    FURNITURE("Furniture"),
    TOYS("Toys"),
    BOOKS("Books"),
    HEALTH("Health"),
    BEAUTY("Beauty"),
    SPORTS("Sports"),
    AUTOMOTIVE("Automotive"),
    STATIONERY("Stationery"),
    PET_SUPPLIES("Pet Supplies"),
    HOME_APPLIANCES("Home Appliances"),
    GARDEN("Garden"),
    JEWELRY("Jewelry"),
    SHOES("Shoes"),
    ACCESSORIES("Accessories"),
    FITNESS("Fitness"),
    SOFTWARE("Software"),
    MUSIC("Music"),
    MOVIES("Movies"),
    VIDEO_GAMES("Video Games"),
    ART_SUPPLIES("Art Supplies"),
    CRAFTS("Crafts"),
    KITCHENWARE("Kitchenware"),
    OFFICE_SUPPLIES("Office Supplies"),
    BABY_PRODUCTS("Baby Products"),
    PERSONAL_CARE("Personal Care"),
    HOUSEHOLD_CLEANING("Household Cleaning"),
    TOOLS("Tools"),
    TRAVEL_GEAR("Travel Gear"),
    OUTDOOR("Outdoor"),
    FURNITURE_KIDS("Kids Furniture"),
    PET_CARE("Pet Care"),
    GROCERIES("Groceries"),
    FASHION("Fashion"),
    LUXURY_GOODS("Luxury Goods"),
    ELECTRONIC_ACCESSORIES("Electronic Accessories"),
    GIFTS("Gifts"),
    SUPPLEMENTS("Supplements"),
    DIY_PRODUCTS("DIY Products"),
    VINTAGE("Vintage"),
    COLLECTIBLES("Collectibles");

    override fun toString(): String {
        return displayName
    }
}