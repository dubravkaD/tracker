package com.example.tracker.util.interfaces

import com.example.tracker.models.User

interface UserCallback {
    fun onDataReceived(u:User?)
}