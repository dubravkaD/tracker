package com.example.tracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productorigintracker.adapters.ProductAdapter
import com.example.productorigintracker.models.Product
import com.example.productorigintracker.models.User
import com.example.tracker.R
import com.example.tracker.util.enums.Category
import com.example.tracker.util.enums.Country

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: ArrayList<Product>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

//        recyclerView = view.findViewById(R.id.rvProduct)
        val searchEditText = view.findViewById<EditText>(R.id.searchEditText).text.toString()

//        val user = User("d","d@d","1654crt")
//        val product = Product("1", user,"product 1","m1",Country.SERBIA.toString(),"5616fyt",Category.FOOD.toString(),"drawable/baseline_home_24.xml")
//        productList.add(product)
//
//        productAdapter = ProductAdapter(this.requireContext(),productList)
//        recyclerView.layoutManager = LinearLayoutManager(this.context)
//        recyclerView.adapter = productAdapter
//
//        searchEditText.addTextChangedListener { text ->
//            productAdapter.filter(text.toString())
//        }
        return view
    }

}