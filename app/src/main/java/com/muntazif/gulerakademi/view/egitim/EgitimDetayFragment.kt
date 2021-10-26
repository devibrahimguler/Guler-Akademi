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
import com.muntazif.gulerakademi.databinding.FragmentEgitimDetayBinding
import com.muntazif.gulerakademi.viewmodel.egitimler.EgitimDetayFragmentViewModel

class EgitimDetayFragment : Fragment() {

    private lateinit var binding : FragmentEgitimDetayBinding

    private lateinit var viewModel : EgitimDetayFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_egitim_detay, container, false)
        val view = binding.root

        viewModel = ViewModelProviders.of(this).get(EgitimDetayFragmentViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getArguments(arguments)

        viewModel.getEgitimler(binding)

        binding.geriEgitimDetayFr.setOnClickListener {
            val action = EgitimDetayFragmentDirections.actionEgitimDetayFragmentToEgitimler()
            Navigation.findNavController(it).navigate(action)
        }
    }


}