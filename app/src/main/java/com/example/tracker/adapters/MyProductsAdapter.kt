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
import com.google.firebase.database.FirebaseDatabase
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
                        true
                    }

                    R.id.deleteProduct -> {
                        showDialog(product)
                        true
                    }

                    else -> false
                }
            }

            popupMenu.show()
        }

        private fun showDialog(product: Product) {
            val builder = AlertDialog.Builder(itemView.context)

            builder.setTitle("Delete product")
            builder.setMessage("Do you want to delete this product")

            builder.setPositiveButton("OK") { dialog, which ->
                deleteProduct(product)
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        private fun deleteProduct(product: Product){
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                productList.removeAt(position)
                notifyItemRemoved(position)

                deleteFromDatabase(product)
                deleteFromStorage(product)
            } else {
                Log.w("Adapter position", "position is not valid $position")
            }
        }

        private fun deleteFromDatabase(product: Product){
            val productRef = FirebaseDatabase.getInstance().getReference("products")
            product.id?.let { productRef.child(it).removeValue().addOnSuccessListener {
                Toast.makeText(context, "Product deleted successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { e->
                Toast.makeText(context, "Error deleting product", Toast.LENGTH_SHORT).show()
                Log.e("Delete", "Error deleting product: ${e.message}")
            } }
        }

        private fun deleteFromStorage(product: Product){
            storageRef.child("/"+product.id).delete().addOnSuccessListener {
                Log.i("Delete Product Image","Success")
            }.addOnFailureListener { e ->
                Log.e("Delete", "Error deleting image: ${e.message}")
            }
        }
    }

}