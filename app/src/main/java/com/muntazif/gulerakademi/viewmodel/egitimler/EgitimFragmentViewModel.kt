package com.muntazif.gulerakademi.viewmodel.egitimler

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.muntazif.gulerakademi.adapter.EgitimFragmentAdapter
import com.muntazif.gulerakademi.databinding.FragmentEgitimBinding
import com.muntazif.gulerakademi.model.Egitimler
import android.content.Context

class EgitimFragmentViewModel : ViewModel() {

    private var adapter : EgitimFragmentAdapter? = null
    private var egitimList : MutableList<Egitimler>? = null


    fun getEgitimler(binding : FragmentEgitimBinding, context : Context?) {
        var recyclerView : RecyclerView? = null
        recyclerView = binding.recyclerViewEgitimFr
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager

        egitimList = ArrayList()
        adapter = context?.let { EgitimFragmentAdapter(it, egitimList as ArrayList<Egitimler>) }
        recyclerView.adapter = adapter

        val egitimDatabe = FirebaseFirestore.getInstance().collection("Egitimler")

        egitimDatabe.get().addOnSuccessListener { document ->
            egitimList!!.clear()
            val egitimler = document.toObjects<Egitimler>()
            for (egitim in egitimler) {
                egitimList!!.add(egitim)
            }
            adapter!!.notifyDataSetChanged()
        }
    }
}