package com.example.tracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.tracker.adapters.ProductAdapter
import com.example.tracker.models.Product
import com.example.tracker.R

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
        val searchButton = view.findViewById<ImageButton>(R.id.ibSearch)

        searchButton.setOnClickListener {

        }
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