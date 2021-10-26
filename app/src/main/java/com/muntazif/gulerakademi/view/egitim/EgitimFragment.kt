package com.muntazif.gulerakademi.view.egitim

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.muntazif.gulerakademi.R
import com.muntazif.gulerakademi.databinding.FragmentEgitimBinding
import com.muntazif.gulerakademi.viewmodel.egitimler.EgitimFragmentViewModel


class EgitimFragment : Fragment() {

    private lateinit var binding : FragmentEgitimBinding
    private lateinit var viewModel : EgitimFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_egitim, container, false)
        val view = binding.root

        viewModel = ViewModelProviders.of(this).get(EgitimFragmentViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getEgitimler(binding, context)

    }

}