package com.example.tracker.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.tracker.R
import com.example.tracker.models.Product

class ProductDetailsFragment : Fragment() {

    private lateinit var ivProductImage: ImageView
    private lateinit var tvName : TextView
    private lateinit var tvUser : TextView
    private lateinit var tvManufacturer : TextView
    private lateinit var tvOrigin : TextView
    private lateinit var tvCategory : TextView
    private lateinit var tvBarcode : TextView
    private lateinit var product: Product

    val args : ProductDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_details, container, false)

        ivProductImage = view.findViewById(R.id.imageViewProductImage)
        tvName = view.findViewById(R.id.textViewName)
        tvUser = view.findViewById(R.id.textViewUser)
        tvManufacturer = view.findViewById(R.id.textViewManufacturer)
        tvOrigin = view.findViewById(R.id.textViewCountry)
        tvCategory = view.findViewById(R.id.textViewCategory)
        tvBarcode = view.findViewById(R.id.textViewBarcode)

        product = args.product

        ivProductImage.setImageURI(Uri.parse(product.image))
        tvName.text = product.name
        tvUser.text = product.user?.username ?: "No User"
        tvManufacturer.text = product.manufacturer
        tvOrigin.text = product.countryOfOrigin
        tvCategory.text = product.category
        tvBarcode.text = product.barcode
//        val btn = view.findViewById<Button>(R.id.btnPD)
//
//        val edt = view.findViewById<EditText>(R.id.edtPD)
//
//        val txv = view.findViewById<TextView>(R.id.txvpd)

//        txv.text = args.productFromMain

//        btn.setOnClickListener {
//            val action = ProductDetailsFragmentDirections.actionProductDetailsFragmentToMainFragment()
//            Navigation.findNavController(view).navigate(action)
//        }
        return view
    }
}