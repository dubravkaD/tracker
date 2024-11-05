package com.example.tracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.tracker.R

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val btn = view.findViewById<Button>(R.id.btnMain)
        val ed = view.findViewById<EditText>(R.id.edText)

//        btn.setOnClickListener {
//            val data = ed.text.toString()
//            val action = MainFragmentDirections.actionMainFragmentToProductDetailsFragment(data)
//            Navigation.findNavController(view).navigate(action)
//        }

        return view
    }


}