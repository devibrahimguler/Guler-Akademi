package com.muntazif.gulerakademi.view.giris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.muntazif.gulerakademi.databinding.ActivityKayitBinding
import com.muntazif.gulerakademi.viewmodel.giris.KayitActivityViewModel

class KayitActivity : AppCompatActivity() {

    private lateinit var binding : ActivityKayitBinding

    private lateinit var viewModel : KayitActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKayitBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProviders.of(this).get(KayitActivityViewModel::class.java)

        binding.girisGitKayitAc.setOnClickListener {
            val intent = Intent(applicationContext, GirisActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.kayitOlKayitAc.setOnClickListener {

            viewModel.kullaniciBilgi(binding, this)

        }

    }
}