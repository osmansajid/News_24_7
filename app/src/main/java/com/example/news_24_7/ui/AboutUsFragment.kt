package com.example.news_24_7.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.news_24_7.R
import com.example.news_24_7.databinding.FragmentAboutUsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AboutUsFragment: Fragment(R.layout.fragment_about_us) {

    private var _binding: FragmentAboutUsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAboutUsBinding.bind(view)
        //hiding bottom bar for details fragment
        val navBar: BottomNavigationView = requireActivity().findViewById(R.id.bottom_bar)
        navBar.isVisible = false

        binding.apply {
            textViewMail.setOnClickListener {
                val mailIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "message/rfc822"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("osmansazid13@gmail.com"))
                }
                startActivity(mailIntent)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}