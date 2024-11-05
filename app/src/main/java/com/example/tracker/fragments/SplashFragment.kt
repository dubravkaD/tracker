package com.example.tracker.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.tracker.R

class SplashFragment : BaseFragment() {

    override var bottomNavigationViewVisibility: Int
        get() = super.bottomNavigationViewVisibility
        set(value) {
            View.GONE
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        val handler = Handler(Looper.myLooper()!!)

        handler.postDelayed({
            Navigation.findNavController(view).navigate(R.id.loginFragment)
        },2500)

        return view
    }

}