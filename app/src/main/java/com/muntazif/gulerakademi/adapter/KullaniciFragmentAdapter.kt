package com.muntazif.gulerakademi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.muntazif.gulerakademi.R
import com.muntazif.gulerakademi.databinding.KullaniciRecyclerRowBinding
import com.muntazif.gulerakademi.model.Egitimler
import com.muntazif.gulerakademi.view.kullanici.KullaniciFragmentDirections

class KullaniciFragmentAdapter(
    private val context : Context,
    private val egitimlerList : ArrayList<Egitimler>)
    : RecyclerView.Adapter<KullaniciFragmentAdapter.KullaniciViewHolder>() {

    class KullaniciViewHolder(val view : KullaniciRecyclerRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KullaniciViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<KullaniciRecyclerRowBinding>(inflater, R.layout.kullanici_recycler_row, parent, false)
        return KullaniciViewHolder(view)
    }

    override fun onBindViewHolder(holder: KullaniciViewHolder, position: Int) {
        holder.view.kullanici = egitimlerList[position]

        holder.itemView.setOnClickListener {
            val uuid = holder.view.idKullaniciRecyclerRow.text.toString()
            uuid?.let { uuid ->
                val action = KullaniciFragmentDirections.actionKullaniciToEgitimDetayKullaniciFragment(uuid)
                Navigation.findNavController(it).navigate(action)
            }

        }
    }

    override fun getItemCount(): Int {
        return egitimlerList.size
    }

}