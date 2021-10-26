 package com.muntazif.gulerakademi.view.giris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.muntazif.gulerakademi.databinding.ActivityGirisBinding
import com.muntazif.gulerakademi.viewmodel.giris.GirisActivityViewModel

 class GirisActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGirisBinding

    private lateinit var viewModel : GirisActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGirisBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProviders.of(this).get(GirisActivityViewModel::class.java)

        viewModel.getInitials(this)

        viewModel.getCurrentUser(this)

        binding.kayitGitGirisAc.setOnClickListener {
            val intent = Intent(this, KayitActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.girisYapGirisAc.setOnClickListener {
            viewModel.getLogin(binding,this)
        }
    }
}