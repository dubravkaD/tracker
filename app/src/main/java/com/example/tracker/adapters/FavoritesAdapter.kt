package com.example.tracker.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.tracker.R
import com.example.tracker.adapters.ProductAdapter.ProductViewHolder
import com.example.tracker.fragments.FavoritesFragmentDirections
import com.example.tracker.fragments.HomeFragmentDirections
import com.example.tracker.models.Product
import com.google.firebase.storage.FirebaseStorage

class FavoritesAdapter(val context: Context, val view:View, private val favorites: ArrayList<Product>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var storageRef = FirebaseStorage.getInstance().getReference("product_images")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false)
        return FavoritesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val current = favorites[position]
        val viewHolder = holder as FavoritesViewHolder

        if(current.image == null){
            viewHolder.image.setImageResource(R.drawable.baseline_map_24)
        } else {
            storageRef.child("/"+current.id).getBytes(10*1024*1024).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
                viewHolder.image.setImageBitmap(bitmap)
            }.addOnFailureListener {
                viewHolder.image.setImageResource(R.drawable.baseline_map_24)
                Log.w("Firebase Storage","Cannot retrieve image from storage",it)
            }
        }
        viewHolder.name.text = current.name
        viewHolder.barcode.text = current.barcode

        // Issue with navigation
//        viewHolder.card.setOnClickListener {
//            val action = FavoritesFragmentDirections.actionFavoritesFragmentToProductDetailsFragment(current)
//            Navigation.findNavController(view).navigate(action)
//        }
    }

    class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.ivFavoritesImage)
        val name = itemView.findViewById<TextView>(R.id.tvFavoritesName)
        val barcode = itemView.findViewById<TextView>(R.id.tvFavoritesBarcode)
        val card = itemView.findViewById<CardView>(R.id.cvFavoritesItem)
    }
}