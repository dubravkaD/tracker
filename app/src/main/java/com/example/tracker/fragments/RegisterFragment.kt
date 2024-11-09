package com.example.tracker.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.tracker.MainActivity
import com.example.tracker.R
import com.example.tracker.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        auth = FirebaseAuth.getInstance()
        userRef = FirebaseDatabase.getInstance().getReference("users")

        val login = view.findViewById<TextView>(R.id.tvLogin)
        val register = view.findViewById<Button>(R.id.btnRegister)

        register.setOnClickListener {
            val username = view.findViewById<EditText>(R.id.edtUsernameR).text.toString()
            val email = view.findViewById<EditText>(R.id.editTextTextEmailAddressR).text.toString()
            val pass = view.findViewById<EditText>(R.id.edtPasswordR).text.toString()
            val confirmPass = view.findViewById<EditText>(R.id.edtCPassword).text.toString()
            if(pass == confirmPass){
                Toast.makeText(view.context,"Registered",Toast.LENGTH_LONG).show()
            }
        }

        login.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            Navigation.findNavController(view).navigate(action)
        }
        return view
    }

    private fun register(email:String,pass:String,username:String){
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
            if (it.isSuccessful){
                userRef.child(auth.currentUser?.uid!!).setValue(User(username,email,auth.currentUser?.uid!!))
                Toast.makeText(requireContext(),"Register",Toast.LENGTH_LONG).show()
                this.activity?.finish()
                val intent = Intent(this.context, MainActivity::class.java)
                startActivity(intent)
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(),"Failed Register", Toast.LENGTH_LONG).show()
        }
    }
}