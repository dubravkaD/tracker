package com.example.tracker.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracker.R
import com.example.tracker.adapters.MyProductsAdapter
import com.example.tracker.models.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MyProductsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyProductsAdapter
    private lateinit var mProductList: ArrayList<Product>

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
        val view = inflater.inflate(R.layout.fragment_my_products, container, false)

        // Firebase
        auth = FirebaseAuth.getInstance()
        productRef = FirebaseDatabase.getInstance().getReference("products")
        storageRef = FirebaseStorage.getInstance().getReference("product_images")
        userRef = FirebaseDatabase.getInstance().getReference("users")

        // My Product List
        mProductList = ArrayList<Product>()

        // RecyclerView
        adapter = MyProductsAdapter(view.context,view, mProductList) { product ->
//            Log.i("MyProducts", "Selected product $product") // working
            Navigation.findNavController(view).navigate(MyProductsFragmentDirections.actionMyProductsFragmentToUpdateFragment(product))
        }
        recyclerView = view.findViewById(R.id.rvMyProducts)
        recyclerView.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        // Products from Firebase
        productRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mProductList.clear()
                for (productSnapshot in snapshot.children){
                    val prod = productSnapshot.getValue(Product::class.java)
                    if(prod!=null && prod.user?.uid==auth.currentUser?.uid){
                        mProductList.add(prod)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Products form firebase","Failed to read value",error.toException())
            }

        })

        return view
    }

}