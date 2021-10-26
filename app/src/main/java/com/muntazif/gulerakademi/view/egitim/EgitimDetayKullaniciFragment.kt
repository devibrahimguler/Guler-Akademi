package com.muntazif.gulerakademi.view.egitim

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.muntazif.gulerakademi.R
import com.muntazif.gulerakademi.databinding.FragmentEgitimDetayKullaniciBinding
import com.muntazif.gulerakademi.viewmodel.egitimler.EgitimDetayKullaniciFragmentViewModel

class EgitimDetayKullaniciFragment : Fragment() {

    private lateinit var binding : FragmentEgitimDetayKullaniciBinding

    private lateinit var viewModel : EgitimDetayKullaniciFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_egitim_detay_kullanici, container, false)
        val view = binding.root

        viewModel = ViewModelProviders.of(this).get(EgitimDetayKullaniciFragmentViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.geriEgitimDetayKullaniciFr.setOnClickListener {
            val action = EgitimDetayKullaniciFragmentDirections.actionEgitimDetayKullaniciFragmentToKullanici()
            Navigation.findNavController(it).navigate(action)
        }

        viewModel.getArguments(arguments)

        viewModel.getEgitimler(binding)
    }

}