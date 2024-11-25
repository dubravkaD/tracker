package com.example.tracker.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.tracker.models.Product
import com.example.tracker.R
import com.example.tracker.fragments.HomeFragmentDirections
import com.google.firebase.storage.FirebaseStorage

class ProductAdapter(val context: Context, val view: View, var productList: ArrayList<Product>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var storageRef = FirebaseStorage.getInstance().getReference("product_images")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val current = productList[position]
        val viewHolder = holder as ProductViewHolder

        if(current.image == null){
            viewHolder.productImage.setImageResource(R.drawable.baseline_map_24)
        } else {
            storageRef.child("/"+current.id).getBytes(10*1024*1024).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
                viewHolder.productImage.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Log.w("Firebase Storage","Cannot retrieve image from storage",it)
            }
        }
        viewHolder.productName.text = current.name
        viewHolder.manufacturer.text = current.manufacturer
        viewHolder.countryOfOrigin.text = current.countryOfOrigin
        viewHolder.barcode.text = current.barcode
        viewHolder.category.text = current.category

        viewHolder.cardView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(current)
            Navigation.findNavController(view).navigate(action)
        }
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val productImage = itemView.findViewById<ImageView>(R.id.ivImage)
        val productName = itemView.findViewById<TextView>(R.id.tvName)
        val manufacturer = itemView.findViewById<TextView>(R.id.tvManufacturer)
        val countryOfOrigin = itemView.findViewById<TextView>(R.id.tvOrigin)
        val barcode = itemView.findViewById<TextView>(R.id.tvBarcode)
        val category = itemView.findViewById<TextView>(R.id.tvCategory)

        val cardView = itemView.findViewById<CardView>(R.id.cvProductItem)
    }

    fun filter(name:String){
        if(name.isNotEmpty()){
            productList = productList.filter { it.name?.contains(name, ignoreCase = true) == true || it.barcode?.contains(name, ignoreCase = true) == true } as ArrayList<Product>
            notifyDataSetChanged()
        } else {
            notifyDataSetChanged()
        }

    }
}