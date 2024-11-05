package com.example.tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class SettingsFragment : Fragment() {

    private lateinit var username:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val tvUsername = view.findViewById<TextView>(R.id.tvUsername)
        tvUsername.text = "User name"

        val cvLogut = view.findViewById<CardView>(R.id.cvLogout)

        cvLogut.setOnClickListener{
            Toast.makeText(view.context,"Log out",Toast.LENGTH_LONG).show()
        }

        return view
    }

}