package com.muntazif.gulerakademi.view.egitim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.muntazif.gulerakademi.databinding.ActivityEgitimPaylasBinding
import com.muntazif.gulerakademi.viewmodel.egitimler.EgitimPaylasActivityViewModel


class EgitimPaylasActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEgitimPaylasBinding

    private lateinit var viewModel : EgitimPaylasActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEgitimPaylasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProviders.of(this).get(EgitimPaylasActivityViewModel::class.java)

        binding.geriEgitimPaylasAc.setOnClickListener {
            finish()
        }

        binding.resimEgitimPaylasAc.setOnClickListener {
            viewModel.setPhotoCrop(this)
        }

        binding.paylasEgitimPaylasAc.setOnClickListener {
            viewModel.egitimPaylas(binding, this)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel.setPermissionResult(this, requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.setActivityResultSetting(binding, this, requestCode, resultCode, data)
    }



}