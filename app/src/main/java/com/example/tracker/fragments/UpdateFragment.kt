package com.example.tracker.fragments

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.example.tracker.R
import com.example.tracker.models.Product
import com.example.tracker.util.enums.Category
import com.example.tracker.util.enums.Country
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UpdateFragment : Fragment() {

    val args: UpdateFragmentArgs by navArgs()

    private lateinit var editTextName : EditText
    private lateinit var editTextManufacturer : EditText
    private lateinit var editTextBarcode : EditText
    private lateinit var updateButton: Button
    private lateinit var imageViewProduct: ImageView

    private lateinit var country: String
    private lateinit var category: String
    private lateinit var name : String
    private lateinit var manufacturer : String
    private lateinit var barcode: String
    private lateinit var product: Product

    private lateinit var spinnerCountry: Spinner
    private lateinit var spinnerCategory: Spinner
    private lateinit var adapterCountry: ArrayAdapter<String>
    private lateinit var adapterCategory: ArrayAdapter<String>

    val countryList = enumValues<Country>().map { it.displayName }
    val categoryList = enumValues<Category>().map { it.displayName }

    private lateinit var productRef : DatabaseReference
    private lateinit var userRef : DatabaseReference
    private lateinit var storageRef : StorageReference
    private lateinit var auth: FirebaseAuth

    private var uri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        product = args.product

        // SPINNERS
        spinnerCountry = view.findViewById(R.id.updateSpinnerCountry)
        spinnerCategory= view.findViewById(R.id.updateSpinnerCategory)

        adapterCountry = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, countryList)
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        adapterCategory = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, categoryList)
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerCountry.adapter = adapterCountry
        spinnerCategory.adapter = adapterCategory

        spinnerCategory.setSelection(0)
        spinnerCountry.setSelection(0)

        spinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCountry = countryList[position]
                country = selectedCountry
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                spinnerCountry.setSelection(0)
                country = countryList[0]
            }

        }

        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCategory = categoryList[position]
                category = selectedCategory
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                spinnerCategory.setSelection(0)
                category = categoryList[0]
            }

        }

        // FIREBASE
        productRef = FirebaseDatabase.getInstance().getReference("products")
        storageRef = FirebaseStorage.getInstance().getReference("product_images")
        userRef = FirebaseDatabase.getInstance().getReference("users")
        auth = FirebaseAuth.getInstance()

        updateButton = view.findViewById(R.id.updateBtnUpdate)
        editTextName = view.findViewById(R.id.updateEdtName)
        editTextManufacturer = view.findViewById(R.id.updateEdtManufacturer)
        editTextBarcode = view.findViewById(R.id.updateEdtBarcode)
        imageViewProduct = view.findViewById(R.id.updateIvPick)

        if(product.id != null && product.image!=null){
            storageRef.child("/"+product.id).getBytes(10*1024*1024).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
                imageViewProduct.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Log.w("Firebase Storage","Cannot retrieve image from storage",it)
            }
        }

        editTextName.text.clear()
        editTextName.setText(product.name)
        editTextManufacturer.text.clear()
        editTextManufacturer.setText(product.manufacturer)
        editTextBarcode.text.clear()
        editTextBarcode.setText(product.barcode)

        spinnerCountry.setSelection(countryList.indexOf(product.countryOfOrigin))
        spinnerCategory.setSelection(categoryList.indexOf(product.category))

        updateButton.setOnClickListener {

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pickButton = view.findViewById<Button>(R.id.updateBtnPick)

        // For API >= 30
        val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { selectedURI ->
            selectedURI?.let {
                val imageView = view.findViewById<ImageView>(R.id.imageViewPick)
                imageView.setImageURI(it)
                uri = it
            }
        }
        pickButton.setOnClickListener {
            pickImage.launch("image/*")
            Log.i("imagePicker ",uri.toString())
        }
    }

    private fun update(){}
}