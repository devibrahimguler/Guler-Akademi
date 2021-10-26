package com.muntazif.gulerakademi.view.kullanici

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.muntazif.gulerakademi.R

class KullaniciActivity : AppCompatActivity()  {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kullanici)

        auth = FirebaseAuth.getInstance()

        val navView : BottomNavigationView = findViewById(R.id.nav_view_kullanici_ac)
        val navController = findNavController(R.id.nav_host_fragment_kullanici_ac)
        navView.setupWithNavController(navController)

    }
}