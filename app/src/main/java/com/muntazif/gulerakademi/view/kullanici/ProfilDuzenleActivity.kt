package com.muntazif.gulerakademi.view.kullanici

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.muntazif.gulerakademi.databinding.ActivityProfilDuzenleBinding
import com.muntazif.gulerakademi.viewmodel.kullanici.ProfilDuzenleActivityViewModel


class ProfilDuzenleActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfilDuzenleBinding

    private lateinit var viewModel : ProfilDuzenleActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilDuzenleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProviders.of(this).get(ProfilDuzenleActivityViewModel::class.java)

        binding.geriProfilDuzenleAc.setOnClickListener {
            finish()
        }

        binding.resimProfilDuzenleAc.setOnClickListener {
            viewModel.setSelectImage(this)
        }

        binding.duzenleProfilDuzenleAc.setOnClickListener {
            viewModel.getUpdateControl(binding, this)
        }

        viewModel.KullaniciBilgi(binding)
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