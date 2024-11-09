package com.example.tracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tracker.models.Product
import com.example.tracker.R

class ProductAdapter(val context: Context, var productList: ArrayList<Product>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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

        viewHolder.productName.text = current.name
        viewHolder.productImage.text = current.image
        viewHolder.manufacturer.text = current.manufacturer
        viewHolder.countryOfOrigin.text = current.countryOfOrigin
        viewHolder.barcode.text = current.barcode
        viewHolder.category.text = current.category
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val productImage = itemView.findViewById<TextView>(R.id.ivImage)
        val productName = itemView.findViewById<TextView>(R.id.tvName)
        val manufacturer = itemView.findViewById<TextView>(R.id.tvManufacturer)
        val countryOfOrigin = itemView.findViewById<TextView>(R.id.tvOrigin)
        val barcode = itemView.findViewById<TextView>(R.id.tvBarcode)
        val category = itemView.findViewById<TextView>(R.id.tvCategory)
    }

    fun filter(name:String){
        productList = if (name.isEmpty()){
            productList
        } else ({
            productList.filter{ it.name?.contains(name,ignoreCase = true) == true }

        }) as ArrayList<Product>

        notifyDataSetChanged()
    }
}