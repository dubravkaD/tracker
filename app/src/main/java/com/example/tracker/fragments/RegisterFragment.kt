package com.example.tracker.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

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
                register(email,pass,username)
            } else {
                Toast.makeText(view.context,"Password does not match confirmed",Toast.LENGTH_LONG).show()
            }
        }

        login.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            Navigation.findNavController(view).navigate(action)
        }
        return view
    }

    private fun register(email:String,pass:String,username:String){
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener { task->
            if (task.isSuccessful){
                Log.i("Auth successful","values")

                userRef.child(auth.currentUser?.uid!!).setValue(User(username,email,auth.currentUser?.uid!!))

                val user = Firebase.auth.currentUser
                val updatedProfile = userProfileChangeRequest { displayName = username }
                user!!.updateProfile(updatedProfile)

                Toast.makeText(requireContext(),"Register",Toast.LENGTH_LONG).show()
                this.activity?.finish()
                val intent = Intent(this.context, MainActivity::class.java)
                startActivity(intent)
            } else {
                Log.e("task error","SignIn",task.exception)
            }
        }.addOnFailureListener {
            Log.e("firebase auth", it.message.toString())
            Toast.makeText(requireContext(),"Failed Register", Toast.LENGTH_LONG).show()
        }
    }
}