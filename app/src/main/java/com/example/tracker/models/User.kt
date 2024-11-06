package com.example.productorigintracker.models

class User {

    private lateinit var username:String
    private lateinit var email:String
    private lateinit var uid:String

    constructor(){}
    constructor(username:String, email:String, uid:String){
        this.username = username
        this.email = email
        this.uid = uid
    }
}