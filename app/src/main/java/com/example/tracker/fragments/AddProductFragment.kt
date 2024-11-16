package com.example.tracker.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tracker.models.Product
import com.example.tracker.models.User
import com.example.tracker.R
import com.example.tracker.util.enums.Category
import com.example.tracker.util.enums.Country
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddProductFragment : Fragment() {

    private lateinit var country: String
    private lateinit var category: String
    private lateinit var name : String
    private lateinit var manufacturer : String
    private lateinit var user : User

    private lateinit var editTextName : EditText
    private lateinit var editTextManufacturer : EditText
    private lateinit var editTextBarcode : EditText
    private lateinit var saveButton: Button

    private lateinit var spinnerCountry: Spinner
    private lateinit var spinnerCategory: Spinner
    private lateinit var adapterCountry: ArrayAdapter<String>
    private lateinit var adapterCategory: ArrayAdapter<String>

    private var barcode : Int = 0
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
        val view = inflater.inflate(R.layout.fragment_add_product, container, false)

        // SPINNERS
        spinnerCountry = view.findViewById(R.id.spinnerCountry)
        spinnerCategory= view.findViewById(R.id.spinnerCategory)

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

//        val uid = auth.currentUser?.uid
//        user = userRef.child(uid).get()

        //
        saveButton = view.findViewById(R.id.btnSave)
        editTextName = view.findViewById(R.id.editTextName)
        editTextManufacturer = view.findViewById(R.id.editTextManufacturer)
        editTextBarcode = view.findViewById(R.id.editTextBarcode)

        saveButton.setOnClickListener {
            name = editTextName.text.toString()
            manufacturer = editTextManufacturer.text.toString()
            val barText = editTextBarcode.text.toString()
//            Log.i("Saved values","name $name man $manufacturer bartext $barText country $country category $category")
            Log.i("Barcode length", Integer.parseInt(barText).toString())
//            save()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pickButton = view.findViewById<Button>(R.id.btnPick)

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

    // problem with barcode NullPointerException
    private fun save() {
        name = requireView().findViewById<EditText>(R.id.editTextName).text.toString()
        barcode = Integer.getInteger(requireView().findViewById<EditText>(R.id.editTextBarcode).text.toString())!! ?: 5
        manufacturer = requireView().findViewById<EditText>(R.id.editTextManufacturer).text.toString()

        Log.i("AddProductName", name)
        Log.i("AddProductBarcode", barcode.toString())
        Log.i("AddProductManufacturer", manufacturer)
        if (name.isNotEmpty() && manufacturer.isNotEmpty() && barcode!=0){

            val id = productRef.push().key!!

            uri?.let {
                storageRef.child(id).putFile(it).addOnSuccessListener { task ->
                    task.metadata!!.reference!!.downloadUrl.addOnSuccessListener { url ->
                        Toast.makeText(requireContext(), "Image stored successfully",Toast.LENGTH_SHORT).show()
                        val imageURL = url.toString()
                        val p = Product(id,user,name,manufacturer,country,barcode.toString(),category,imageURL)

                        productRef.child(id).setValue(p).addOnSuccessListener {
                            Toast.makeText(requireContext(), "Product successfully added",Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                            Toast.makeText(requireContext(), "Product unsuccessfully added",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


        } else {
            Toast.makeText(requireContext(),"Failed add product, $name $manufacturer $barcode", Toast.LENGTH_LONG).show()
        }
    }
}