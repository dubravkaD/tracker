package com.example.tracker.fragments

import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
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
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()

        val login = view.findViewById<Button>(R.id.btnLogin)
        val register = view.findViewById<TextView>(R.id.tvRegister)

        val edE = view.findViewById<EditText>(R.id.editTextTextEmailAddress)
        val edP = view.findViewById<EditText>(R.id.edtPassword)


        login.setOnClickListener {
            val email = edE.text.toString()
            val pass = edP.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()){
                login(email, pass)
            } else {
                Toast.makeText(view.context,"Failed Log in, $email $pass", Toast.LENGTH_LONG).show()
            }
        }

        register.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            Navigation.findNavController(view).navigate(action)
        }
        return view
    }

    private fun login(email:String,pass:String){
        auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener {
            Toast.makeText(requireContext(),"Login",Toast.LENGTH_LONG).show()
            this.activity?.finish()
            val intent = Intent(this.context, MainActivity::class.java)
            startActivity(intent)
        }.addOnFailureListener {
            Toast.makeText(requireContext(),"Failed Login", Toast.LENGTH_LONG).show()
        }
    }

}