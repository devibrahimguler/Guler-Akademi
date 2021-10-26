package com.muntazif.gulerakademi.viewmodel.egitimler

import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.muntazif.gulerakademi.databinding.FragmentEgitimDetayBinding
import com.muntazif.gulerakademi.model.Egitimler
import com.muntazif.gulerakademi.view.egitim.EgitimDetayFragmentArgs

class EgitimDetayFragmentViewModel : ViewModel(){

    private var egitimId = ""
    private lateinit var firebaseUser : FirebaseUser
    lateinit var database : FirebaseFirestore

    fun getArguments(arguments : Bundle?) {
        arguments?.let {
            egitimId = EgitimDetayFragmentArgs.fromBundle(it).egitimId
        }
    }

    fun getEgitimler(binding : FragmentEgitimDetayBinding) {
        database = FirebaseFirestore.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        database.collection("Egitimler").get().addOnSuccessListener { document ->
            val egitimler = document.toObjects<Egitimler>()
            for (egitim in egitimler) {
                if (egitim.getEgitimId() == egitimId){
                    binding.egitimDetay = egitim

                    egitimKontrol(egitim.getEgitimId(),binding.kaydolEgitimDetayFr)

                    binding.kaydolEgitimDetayFr.setOnClickListener {

                        val boolMap = HashMap<String,String>()
                        boolMap["bool"] = egitim.getEgitimId().toString()

                        if (binding.kaydolEgitimDetayFr.tag == "Kaydol"){
                            FirebaseFirestore.getInstance()
                                .collection("Kayit")
                                .document(firebaseUser.uid)
                                .collection("Egitimlerim")
                                .document(egitim.getEgitimId())
                                .set(boolMap)

                        }else{
                            FirebaseFirestore.getInstance()
                                .collection("Kayit")
                                .document(firebaseUser.uid)
                                .collection("Egitimlerim")
                                .document(egitim.getEgitimId())
                                .delete()

                        }
                    }
                }
            }
        }
    }

    private fun egitimKontrol(egitimId : String, kaydol : Button){

        database.collection("Kayit")
            .document(firebaseUser.uid)
            .collection("Egitimlerim")
            .document(egitimId).get().addOnSuccessListener { document ->
                if (document.exists()){
                    kaydol.setText("KAYITLI")
                    kaydol.tag = "Kayitli"
                }else{
                    kaydol.setText("KAYDOL")
                    kaydol.tag = "Kaydol"
                }
            }

    }

}