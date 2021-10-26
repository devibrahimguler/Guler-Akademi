package com.muntazif.gulerakademi.viewmodel.giris

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.muntazif.gulerakademi.databinding.ActivityGirisBinding
import com.muntazif.gulerakademi.view.kullanici.KullaniciActivity

class GirisActivityViewModel : ViewModel() {

    private lateinit var auth : FirebaseAuth
    private lateinit var mProgressBar : ProgressDialog

    fun getLogin(binding : ActivityGirisBinding, context: Activity) {
        mProgressBar.setMessage("Lütfen Bekleyin..")
        mProgressBar.show()

        val email = binding.emailGirisAc.text.toString().trim()
        val password = binding.sifreGirisAc.text.toString().trim()
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener ( OnCompleteListener { task ->

                if (task.isSuccessful) {

                    mProgressBar.dismiss()

                    val guncelKullanici = auth.currentUser?.email.toString()
                    Toast.makeText(context, "Hoşgeldin: ${guncelKullanici}", Toast.LENGTH_LONG).show()
                    val intent = Intent(context, KullaniciActivity::class.java)
                    context.startActivity(intent)
                    context.finish()
                }
                mProgressBar.dismiss()
            }).addOnFailureListener { exception ->
                Toast.makeText(context,exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }

    fun getCurrentUser(context: Activity) {
        val guncelKullanici = auth.currentUser
        if (guncelKullanici != null){
            val intent = Intent(context, KullaniciActivity::class.java)
            context.startActivity(intent)
            context.finish()
        }
    }

    fun getInitials(context: Activity){
        auth = FirebaseAuth.getInstance()
        mProgressBar = ProgressDialog(context)
    }
}