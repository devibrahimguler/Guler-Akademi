package com.muntazif.gulerakademi.view.hakkinda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.muntazif.gulerakademi.R
import com.muntazif.gulerakademi.databinding.FragmentHakkindaBinding

class HakkindaFragment : Fragment() {

    private lateinit var binding : FragmentHakkindaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hakkinda, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.geriHakkindaFr.setOnClickListener {
            val action = HakkindaFragmentDirections.actionHakkindaFragmentToKullanici()
            Navigation.findNavController(it).navigate(action)
        }

    }

}