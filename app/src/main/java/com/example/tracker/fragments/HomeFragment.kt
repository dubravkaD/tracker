package com.example.tracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracker.adapters.ProductAdapter
import com.example.tracker.models.Product
import com.example.tracker.R
import com.example.tracker.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class HomeFragment : Fragment() {

    private lateinit var searchButton: ImageButton
    private lateinit var searchEditText: EditText
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var productList: ArrayList<Product>
    
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
        productList = ArrayList<Product>()
        val user = User("d", "d@d", "cjghc463ufb458")
        val product1 = Product("1", user, "product1", "m", "Denmark", "45163ngu", "Food")
        productList.add(product1)
        productList.add(Product("1", user, "product1", "m", "Denmark", "45163ngu", "Food"))
        productList.add(Product("1",user,"product1","m","Denmark","45163ngu","Food"))
        productList.add(Product("1",user,"aproduct1","m","Denmark","45163ngu","Food"))
        productList.add(Product("1",user,"bproduct1","m","Denmark","45163ngu","Food"))
        productList.add(Product("1",user,"cproduct1","m","Denmark","45163ngu","Food"))
        productList.add(Product("1",user,"aproduct1","m","Denmark","45163ngu","Food"))
        productList.add(Product("1",user,"bproduct1","m","Denmark","45163ngu","Food"))
        productList.add(Product("1",user,"cproduct1","m","Denmark","45163ngu","Food"))
        productList.add(Product("1",user,"product1","m","Denmark","45163ngu","Food"))

        // RecyclerView
        adapter = ProductAdapter(view.context,view, productList)
        recyclerView = view.findViewById(R.id.rvProducts)
        recyclerView.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        // Search
        searchEditText = view.findViewById(R.id.searchEditText)
        searchEditText.text.clear()
        searchButton = view.findViewById(R.id.ibSearch)

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            adapter.filter(query)
        }

        return view
    }

}