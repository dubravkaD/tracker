package com.example.tracker.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.tracker.MainActivity
import com.example.tracker.MainActivity2
import com.example.tracker.R
import com.example.tracker.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SettingsFragment : Fragment() {

    private lateinit var user:User
    private lateinit var auth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        auth = FirebaseAuth.getInstance()

        val tvUsername = view.findViewById<TextView>(R.id.tvUsername)
        tvUsername.text = "Username"

        val cvLogut = view.findViewById<CardView>(R.id.cvLogout)

        cvLogut.setOnClickListener{
            this.activity?.finish()
            val intent = Intent(this.context, MainActivity2::class.java)
            startActivity(intent)
            Toast.makeText(view.context, "Log out", Toast.LENGTH_LONG).show()
        }

        return view
    }

    private fun getUser():User?{
        val userRef = FirebaseDatabase.getInstance().getReference("users")
        var user:User? = null
        userRef.child(auth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        return user
    }

    private fun logout(){
        auth.signOut()
        this.activity?.finish()
        val intent = Intent(this.context, MainActivity2::class.java)
        startActivity(intent)
    }

}