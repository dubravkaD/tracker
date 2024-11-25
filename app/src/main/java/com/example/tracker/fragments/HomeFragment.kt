package com.example.tracker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.EditText
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracker.adapters.ProductAdapter
import com.example.tracker.models.Product
import com.example.tracker.R
import com.example.tracker.models.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class HomeFragment : Fragment() {

    private lateinit var searchButton: ImageButton
    private lateinit var searchEditText: EditText
    private lateinit var searchView: SearchView
    private lateinit var fabAdd: FloatingActionButton
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    
    private lateinit var productRef: DatabaseReference
    private lateinit var storageRef: StorageReference
    private lateinit var userRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Firebase
        auth = FirebaseAuth.getInstance()
        productRef = FirebaseDatabase.getInstance().getReference("products")
        storageRef = FirebaseStorage.getInstance().getReference("product_images")
        userRef = FirebaseDatabase.getInstance().getReference("users")

        // Product List
        val list = ArrayList<Product>()

        // RecyclerView
        adapter = ProductAdapter(view.context,view, list)
        recyclerView = view.findViewById(R.id.rvProducts)
        recyclerView.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter


        // Products from firebase
        productRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (productSnapshot in snapshot.children){
                    val prod = productSnapshot.getValue(Product::class.java)
                    list.add(prod!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("getUser","Failed to read value",error.toException())
            }

        })

        // Search
        searchView = view.findViewById(R.id.sv)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                return true
            }

        })
//        searchEditText = view.findViewById(R.id.searchEditText)
//        searchEditText.text.clear()
//        searchButton = view.findViewById(R.id.ibSearch)
//
//        searchButton.setOnClickListener {
//            val query = searchEditText.text.toString()
//            adapter.filter(query)
//        }

        // Floating Action Button
        fabAdd = view.findViewById(R.id.fabAdd)

        fabAdd.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.addProductFragment)
        }

        return view
    }

}