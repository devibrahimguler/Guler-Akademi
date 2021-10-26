package com.muntazif.gulerakademi.view.kullanici

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.muntazif.gulerakademi.R
import com.muntazif.gulerakademi.databinding.FragmentKullaniciBinding
import com.muntazif.gulerakademi.viewmodel.kullanici.KullaniciFragmentViewModel


class KullaniciFragment : Fragment() {

    lateinit var binding : FragmentKullaniciBinding


    lateinit var viewModel : KullaniciFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kullanici, container, false)

        viewModel = ViewModelProviders.of(this).get(KullaniciFragmentViewModel::class.java)

        viewModel.getInitials()

        viewModel.getAdapter(binding, context)

        binding.egitimYukleKullaniciFr.setOnClickListener {
            val action = KullaniciFragmentDirections.actionKullaniciToEgitimPaylasActivity()
            Navigation.findNavController(it).navigate(action)
        }

        binding.profilDuzenleKullaniciFr.setOnClickListener {
            val action = KullaniciFragmentDirections.actionKullaniciToProfilDuzenleActivity()
            Navigation.findNavController(it).navigate(action)
        }

        binding.cikisYapKullaniciFr.setOnClickListener {
            viewModel.getLogout(activity as FragmentActivity)
        }

        binding.hesapSilKullaniciFr.setOnClickListener {
            viewModel.getLogout(activity as FragmentActivity)
        }

        binding.hakkindaKullaniciFr.setOnClickListener {
            val action = KullaniciFragmentDirections.actionKullaniciToHakkindaFragment()
            Navigation.findNavController(it).navigate(action)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.kullaniciBilgi(binding.isimKullaniciFr, binding.kimlikKullaniciFr, binding.resimKullaniciFr)
        viewModel.kullaniciEgitimVeriAL()

        observeLiveData()

    }

    private fun observeLiveData(){
        viewModel.yukleniyor.observe(viewLifecycleOwner, { yukleniyor->
            yukleniyor?.let {
                if (it){
                    binding.linear1KullaniciFr.visibility = View.GONE
                    binding.yukleniyorKullaniciFr.visibility = View.VISIBLE
                }else{
                    binding.linear1KullaniciFr.visibility = View.VISIBLE
                    binding.yukleniyorKullaniciFr.visibility = View.GONE
                }
            }
        })
    }

}