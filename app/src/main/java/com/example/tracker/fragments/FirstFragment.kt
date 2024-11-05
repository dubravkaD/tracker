package com.example.tracker.fragments

import androidx.fragment.app.Fragment
import com.example.tracker.MainActivity2

abstract class FirstFragment : Fragment(){

    override fun onResume() {
        super.onResume()
        if (activity is MainActivity2) {
            val  mainActivity = activity as MainActivity2
            mainActivity.closeActivity()
        }
    }
}