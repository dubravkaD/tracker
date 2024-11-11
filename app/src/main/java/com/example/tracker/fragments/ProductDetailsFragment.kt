package com.example.tracker.fragments

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.tracker.R
import com.example.tracker.models.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProductDetailsFragment : Fragment() {

    private lateinit var ivProductImage: ImageView
    private lateinit var ivAddFavorites: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvUser: TextView
    private lateinit var tvManufacturer: TextView
    private lateinit var tvOrigin: TextView
    private lateinit var tvCategory: TextView
    private lateinit var tvBarcode: TextView
    private lateinit var product: Product

    private lateinit var productRef: DatabaseReference
    private lateinit var userRef: DatabaseReference
    private lateinit var storageRef: StorageReference
    private lateinit var favoritesRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

//    private var favorites = false

    val args: ProductDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bar = (requireActivity() as AppCompatActivity).supportActionBar
        bar?.show()
        bar?.title = "Product Details"
        bar?.setDisplayHomeAsUpEnabled(false)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_details, container, false)

        product = args.product

        ivProductImage = view.findViewById(R.id.imageViewProductImage)
        ivAddFavorites = view.findViewById(R.id.ivAddFavorites)
        tvName = view.findViewById(R.id.textViewName)
        tvUser = view.findViewById(R.id.textViewUser)
        tvManufacturer = view.findViewById(R.id.textViewManufacturer)
        tvOrigin = view.findViewById(R.id.textViewCountry)
        tvCategory = view.findViewById(R.id.textViewCategory)
        tvBarcode = view.findViewById(R.id.textViewBarcode)

        if (product.image == null) {
            ivProductImage.setImageResource(R.drawable.baseline_map_24)
        } else {
            ivProductImage.setImageURI(Uri.parse(product.image))
        }

        tvName.text = product.name
        tvUser.text = product.user?.username ?: "No User"
        tvManufacturer.text = product.manufacturer
        tvOrigin.text = product.countryOfOrigin
        tvCategory.text = product.category
        tvBarcode.text = product.barcode

        auth = FirebaseAuth.getInstance()
        productRef = FirebaseDatabase.getInstance().getReference("products")
        storageRef = FirebaseStorage.getInstance().getReference("product_images")
        userRef = FirebaseDatabase.getInstance().getReference("users")
        favoritesRef = FirebaseDatabase.getInstance().getReference("favorites")

        // Favorites
//        ivAddFavorites.setOnClickListener {
//            favorites = !favorites
//            updateImageFavorites(favorites)
//        }
//        if (auth.currentUser?.uid != product.user?.uid){
//            ivAddFavorites.setOnClickListener {
//                favoritesRef.child(auth.currentUser?.uid!!).setValue(product).addOnSuccessListener {
//                    ivAddFavorites.setImageResource(R.drawable.baseline_favorite_24)
//                    Toast.makeText(requireContext(), "Product successfully added to favorites", Toast.LENGTH_SHORT).show()
//                }.addOnFailureListener {
//                    Toast.makeText(requireContext(), "Product unsuccessfully added to favorites", Toast.LENGTH_SHORT).show()
//                }
//            }
//        } else {
//            ivAddFavorites.visibility = View.GONE
//        }

        return view
    }

    private fun updateImageFavorites(isFavorites: Boolean) {
        if (isFavorites) {
            ivAddFavorites.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            ivAddFavorites.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }

}