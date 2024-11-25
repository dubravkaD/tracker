package com.example.tracker.fragments

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.example.tracker.util.interfaces.FavoritesCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

    private var favorites = false

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
        tvUser.text = "Author " + product.user?.username ?: "No User"
        tvManufacturer.text = product.manufacturer
        tvOrigin.text = product.countryOfOrigin
        tvCategory.text = product.category
        tvBarcode.text = product.barcode

        auth = FirebaseAuth.getInstance()
        productRef = FirebaseDatabase.getInstance().getReference("products")
        storageRef = FirebaseStorage.getInstance().getReference("product_images")
        userRef = FirebaseDatabase.getInstance().getReference("users")
        favoritesRef = FirebaseDatabase.getInstance().getReference("favorites")

        if (product.id != null && product.image != null) {
            storageRef.child("/" + product.id).getBytes(10 * 1024 * 1024).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                ivProductImage.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Log.w("Firebase Storage", "Cannot retrieve image from storage", it)
            }
        }

        if (product.user?.uid == auth.currentUser?.uid) {
            ivAddFavorites.visibility = View.GONE
            tvUser.text = "This product was created by you"
        } else {
            product.id?.let { findInFavorites(it, object : FavoritesCallback {
                override fun onDataReceived(fav: Boolean) {
                    favorites = fav
                    updateImageFavorites(favorites)
                }
            }) }
        }

        // Favorites
        ivAddFavorites.setOnClickListener {
            favorites = !favorites
            if (favorites) {
                product.id?.let { id ->
                    favoritesRef.child(id).setValue(product).addOnSuccessListener {
                        Log.d("Add Favorites", "Data updated successfully")
                        updateImageFavorites(favorites)
                        Toast.makeText(
                            view.context,
                            "Successfully added to favorites",
                            Toast.LENGTH_LONG
                        ).show()
                    }.addOnFailureListener { e ->
                        Log.e("Add to favorites", "Error adding data to favorites ${e.message}")
                    }
                }
            } else {
                product.id?.let { productID ->
                    favoritesRef.child(productID).removeValue().addOnSuccessListener {
                        Toast.makeText(
                            view.context,
                            "Successfully removed from favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateImageFavorites(favorites)
                    }.addOnFailureListener { e ->
                        Toast.makeText(
                            view.context,
                            "Error removing from favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e(
                            "Remove from favorites",
                            "Error deleting product from favorites: ${e.message}"
                        )
                    }
                }
                updateImageFavorites(favorites)
            }
        }

        return view
    }

    private fun updateImageFavorites(isFavorites: Boolean) {
        if (isFavorites) {
            ivAddFavorites.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            ivAddFavorites.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }

    private fun findInFavorites(productID: String,callback:FavoritesCallback) {
        favoritesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (favoritesSnapshot in snapshot.children) {
                    if (
                        favoritesSnapshot.getValue(Product::class.java) != null &&
                        favoritesSnapshot.getValue(Product::class.java)?.id == productID
                    ) {
                        callback.onDataReceived(fav = true)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Find Favorites", "Failed to read value", error.toException())
            }

        })
    }
}