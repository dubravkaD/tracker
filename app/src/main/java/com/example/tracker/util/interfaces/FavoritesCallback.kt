package com.example.tracker.util.interfaces

import com.example.tracker.models.User

interface FavoritesCallback {
    fun onDataReceived(fav: Boolean)
}