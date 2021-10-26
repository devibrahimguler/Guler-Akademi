package com.muntazif.gulerakademi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.muntazif.gulerakademi.R
import com.muntazif.gulerakademi.databinding.EgitimReyclerRowBinding
import com.muntazif.gulerakademi.model.Egitimler
import com.muntazif.gulerakademi.view.egitim.EgitimFragmentDirections

class EgitimFragmentAdapter(
    private val context : Context,
    private val egitimlerList : ArrayList<Egitimler>)
    : RecyclerView.Adapter<EgitimFragmentAdapter.EgitimViewHolder>(){

    lateinit var firebaseUser : FirebaseUser

    class EgitimViewHolder(val view : EgitimReyclerRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EgitimViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<EgitimReyclerRowBinding>(inflater, R.layout.egitim_reycler_row, parent, false)
        return EgitimViewHolder(view)
    }

    override fun onBindViewHolder(holder: EgitimViewHolder, position: Int) {
        val egitim = egitimlerList[position]
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        holder.view.egitimler = egitimlerList[position]

        egitimKontrol(egitim.getEgitimId(), holder.view.kayitEgitimRecyclerRow)

        holder.itemView.setOnClickListener {
            val uuid = holder.view.idEgitimRecyclerRow.text.toString()

            uuid?.let { uuid ->
                val action = EgitimFragmentDirections.actionEgitimlerToEgitimDetayFragment(uuid)
                Navigation.findNavController(it).navigate(action)
            }

        }

        holder.view.kayitEgitimRecyclerRow.setOnClickListener {

            val boolMap = HashMap<String,String>()
            boolMap["bool"] = egitim.getEgitimId().toString()

            if (holder.view.kayitEgitimRecyclerRow.tag == "Kaydol"){
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

    override fun getItemCount(): Int {
        return egitimlerList.size
    }

    private fun egitimKontrol(egitimId : String, kaydol : Button){

        val egitimRef = FirebaseFirestore.getInstance()
            .collection("Kayit")
            .document(firebaseUser.uid)
            .collection("Egitimlerim")
            .document(egitimId)

        egitimRef.get().addOnSuccessListener { document ->
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