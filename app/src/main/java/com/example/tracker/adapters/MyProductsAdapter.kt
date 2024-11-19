package com.example.tracker.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tracker.R
import com.example.tracker.models.Product
import com.google.firebase.storage.FirebaseStorage

class MyProductsAdapter(
    val context: Context,
    val view: View,
    private var productList: ArrayList<Product>,
    private val productSelected: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var storageRef = FirebaseStorage.getInstance().getReference("product_images")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val current = productList[position]
        val viewHolder = holder as ProductViewHolder

        if (current.image == null) {
            viewHolder.productImage.setImageResource(R.drawable.baseline_map_24)
        } else {
            storageRef.child("/" + current.id).getBytes(10 * 1024 * 1024).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                viewHolder.productImage.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Log.w("Firebase Storage", "Cannot retrieve image from storage", it)
            }
        }
        viewHolder.productName.text = current.name
        viewHolder.manufacturer.text = current.manufacturer
        viewHolder.countryOfOrigin.text = current.countryOfOrigin
        viewHolder.barcode.text = current.barcode
        viewHolder.category.text = current.category

    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage = itemView.findViewById<ImageView>(R.id.ivImage)
        val productName = itemView.findViewById<TextView>(R.id.tvName)
        val manufacturer = itemView.findViewById<TextView>(R.id.tvManufacturer)
        val countryOfOrigin = itemView.findViewById<TextView>(R.id.tvOrigin)
        val barcode = itemView.findViewById<TextView>(R.id.tvBarcode)
        val category = itemView.findViewById<TextView>(R.id.tvCategory)

//        val cardView = itemView.findViewById<CardView>(R.id.cvProductItem)

        init {
            itemView.setOnLongClickListener {
                showPopupMenu(itemView.context,it,productList[adapterPosition])
                true
            }
        }

        private fun showPopupMenu(context: Context, view: View, product: Product) {

            val popupMenu = PopupMenu(context, view)

            MenuInflater(context).inflate(R.menu.options_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.updateProduct -> {
                        productSelected(product)
//                        Toast.makeText(itemView.context, "Update", Toast.LENGTH_LONG).show()
                        true
                    }

                    R.id.deleteProduct -> {
                        showDialog()
//                        Toast.makeText(itemView.context,"Delete",Toast.LENGTH_LONG).show()
                        true
                    }

                    else -> false
                }
            }

            popupMenu.show()
        }

        private fun showDialog() {
            val builder = AlertDialog.Builder(itemView.context)

            builder.setTitle("Delete product")
            builder.setMessage("Do you want to delete this product")

            builder.setPositiveButton("OK") { dialog, which ->
                Toast.makeText(itemView.context, "OK clicked", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                Toast.makeText(itemView.context, "Cancel clicked", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

    }

}