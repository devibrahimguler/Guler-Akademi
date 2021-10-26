package com.muntazif.gulerakademi.viewmodel.kullanici

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.muntazif.gulerakademi.R
import com.muntazif.gulerakademi.adapter.KullaniciFragmentAdapter
import com.muntazif.gulerakademi.databinding.FragmentKullaniciBinding
import com.muntazif.gulerakademi.model.Egitimler
import com.muntazif.gulerakademi.model.Kullanici
import com.muntazif.gulerakademi.view.giris.GirisActivity
import com.squareup.picasso.Picasso

class KullaniciFragmentViewModel : ViewModel() {

    val yukleniyor = MutableLiveData<Boolean>()
    private lateinit var firebaseUser: FirebaseUser
    private var egitimlerimList : List<String>? = null
    private var adapter : KullaniciFragmentAdapter? = null
    private var egitimList : ArrayList<Egitimler>? = null
    private lateinit var auth : FirebaseAuth

    fun kullaniciEgitimVeriAL(){

        egitimlerimList = ArrayList()

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val egitimDatabase = FirebaseFirestore.getInstance()
            .collection("Kayit")
            .document(firebaseUser.uid)
            .collection("Egitimlerim")

        egitimDatabase.get().addOnSuccessListener { document ->
            (egitimlerimList as ArrayList).clear()
            val ids = document
            for (id in ids) {
                (egitimlerimList as ArrayList).add(id!!["bool"].toString())
            }
            kayitliEgitimOku(egitimList!!,adapter!!)
        }
    }

    private fun kayitliEgitimOku(
            egitimList : ArrayList<Egitimler>,
            adapter : KullaniciFragmentAdapter
    ) {
        val egimRef = FirebaseFirestore.getInstance()
                .collection("Egitimler")

        egimRef.get().addOnSuccessListener { document ->
            if (!document.isEmpty){
                (egitimList as ArrayList<Egitimler>).clear()
                val egitimler = document.toObjects<Egitimler>()
                for (egitim in egitimler){
                    for (key in egitimlerimList!!){
                        if (egitim.getEgitimId() == key){
                            (egitimList as ArrayList<Egitimler>).add(egitim)
                        }
                    }
                }
                adapter!!.notifyDataSetChanged()
            }
        }
    }

    fun kullaniciBilgi(
        isim_edit_text : TextView,
        kimlik_edit_text : TextView,
        resim_image_view : ImageView
    ){

        yukleniyor.value = true

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        val kullaniciDatabase = FirebaseFirestore.getInstance().collection("Kullanici").document(firebaseUser.uid)
        kullaniciDatabase.get().addOnSuccessListener { document ->
            if (document != null) {
                val kullanici = document.toObject<Kullanici>()
                Picasso.get().load(kullanici!!.getResim()).placeholder(R.drawable.kullanici_resmi).into(resim_image_view)
                isim_edit_text.setText(kullanici.getIsim().toString())
                kimlik_edit_text.setText(kullanici.getKimlik().toString())

                yukleniyor.value = false
            } else {
                yukleniyor.value = false
            }
        }
    }

    fun getLogout(context: FragmentActivity) {
        auth.signOut()
        val intent = Intent(context, GirisActivity::class.java)
        context.startActivity(intent)
        context.finish()
    }

    fun getAdapter(binding : FragmentKullaniciBinding, context: Context?) {
        var recyclerView : RecyclerView? = null
        recyclerView = binding.recyclerViewKullaniciFr
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager

        egitimList = ArrayList()
        adapter = context?.let { KullaniciFragmentAdapter(it, egitimList as ArrayList<Egitimler>) }
        recyclerView.adapter = adapter
    }

    fun getInitials() {
        auth = FirebaseAuth.getInstance()
    }

}