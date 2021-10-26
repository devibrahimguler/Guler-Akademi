package com.muntazif.gulerakademi.viewmodel.egitimler

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.muntazif.gulerakademi.databinding.FragmentEgitimDetayKullaniciBinding
import com.muntazif.gulerakademi.model.Egitimler
import com.muntazif.gulerakademi.view.egitim.EgitimDetayKullaniciFragmentArgs

class EgitimDetayKullaniciFragmentViewModel : ViewModel() {

    private var egitimId = ""
    lateinit var database : FirebaseFirestore

    fun getArguments(arguments : Bundle?) {
        arguments?.let {
            egitimId = EgitimDetayKullaniciFragmentArgs.fromBundle(it).egitimId
        }
    }

    fun getEgitimler(binding : FragmentEgitimDetayKullaniciBinding) {
        database = FirebaseFirestore.getInstance()

        database.collection("Egitimler").get().addOnSuccessListener { document ->
            val egitimler = document.toObjects<Egitimler>()
            for (egitim in egitimler) {
                if (egitim.getEgitimId() == egitimId){
                    binding.egitimDetayKullanici = egitim
                }
            }
        }
    }
}