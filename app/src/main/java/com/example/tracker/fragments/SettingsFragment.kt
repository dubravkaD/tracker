package com.example.tracker.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.tracker.MainActivity
import com.example.tracker.MainActivity2
import com.example.tracker.R
import com.example.tracker.models.User
import com.example.tracker.util.interfaces.UserCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SettingsFragment : Fragment() {

    private lateinit var tvUsername: TextView
    private lateinit var cvLogout: CardView
    private lateinit var cvMyProducts: CardView

    private var user:User?=null

    private var auth:FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        tvUsername = view.findViewById(R.id.tvUsername)
        cvLogout = view.findViewById(R.id.cvLogout)
        cvMyProducts = view.findViewById(R.id.cvMyProducts)

        setUsername(auth.currentUser?.displayName!!)

        cvMyProducts.setOnClickListener {
            Navigation.findNavController(view).navigate(SettingsFragmentDirections.actionSettingsFragmentToMyProductsFragment())
        }

        cvLogout.setOnClickListener{
            logout()
            Toast.makeText(view.context, "Logout", Toast.LENGTH_LONG).show()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUser(object : UserCallback {
            override fun onDataReceived(u: User?) {
                user = u
                Log.i("userCallback",user.toString()) // working
            }
        })
        Log.i("getUser",user.toString()) // null
    }

    private fun setUsername(username:String){
        tvUsername.text=username
    }

    private fun getUser(callback: UserCallback){
        val userRef = FirebaseDatabase.getInstance().getReference("users")
        userRef.child(auth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val u = snapshot.getValue(User::class.java)!!
                callback.onDataReceived(u)
                Log.i("snapshot",u.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("getUser","Failed to read value",error.toException())
            }

        })
    }

    private fun logout(){
        auth.signOut()
        this.activity?.finish()
        val intent = Intent(this.context, MainActivity2::class.java)
        startActivity(intent)
    }

}