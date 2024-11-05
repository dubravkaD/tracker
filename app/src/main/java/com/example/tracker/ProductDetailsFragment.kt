package com.example.tracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs

class ProductDetailsFragment : Fragment() {

//    val args : ProductDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_details, container, false)

        val btn = view.findViewById<Button>(R.id.btnPD)

        val edt = view.findViewById<EditText>(R.id.edtPD)

        val txv = view.findViewById<TextView>(R.id.txvpd)

//        txv.text = args.productFromMain

//        btn.setOnClickListener {
//            val action = ProductDetailsFragmentDirections.actionProductDetailsFragmentToMainFragment()
//            Navigation.findNavController(view).navigate(action)
//        }
        return view
    }
}