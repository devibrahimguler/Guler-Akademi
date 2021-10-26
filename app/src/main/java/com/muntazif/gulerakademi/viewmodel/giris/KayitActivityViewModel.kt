package com.muntazif.gulerakademi.viewmodel.giris

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.muntazif.gulerakademi.databinding.ActivityKayitBinding
import com.muntazif.gulerakademi.view.kullanici.KullaniciActivity

class KayitActivityViewModel : ViewModel() {

    private lateinit var database : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    fun kullaniciBilgi(binding : ActivityKayitBinding, context: Activity){

        auth = FirebaseAuth.getInstance()

        val isim = binding.isimKayitAc.text.toString().trim()
        val kimlik = binding.kimlikKayitAc.text.toString().trim()
        val email = binding.emailKayitAc.text.toString().trim()
        val sifre = binding.sifreKayitAc.text.toString().trim()

        when {
            TextUtils.isEmpty(isim) -> {
                binding.isimKayitAc.error = "İsim Giriniz"
                return
            }
            TextUtils.isEmpty(kimlik) -> {
                binding.kimlikKayitAc.error = "Sınıf Giriniz"
                return
            }
            TextUtils.isEmpty(email) -> {
                binding.emailKayitAc.error = "Email Giriniz"
                return
            }
            TextUtils.isEmpty(sifre) -> {
                binding.sifreKayitAc.error = "Şifre Giriniz"
                return
            }
            else -> kullaniciYap(context,isim, kimlik, email, sifre)
        }
    }

    private fun kullaniciYap(context: Activity,isim: String, kimlik: String, email: String, sifre: String) {


        auth.createUserWithEmailAndPassword(email,sifre).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val currentUser =  auth.currentUser
                val uid = currentUser!!.uid

                val kullaniciMap = HashMap<String,String>()
                kullaniciMap["id"] = uid
                kullaniciMap["isim"] = isim
                kullaniciMap["kimlik"] = kimlik
                kullaniciMap["email"] = email
                kullaniciMap["sifre"] = sifre
                kullaniciMap["resim"] = "https://firebasestorage.googleapis.com/v0/b/guler-akademi-ea258.appspot.com/o/profile-user.png?alt=media&token=c27c2690-a4b9-4551-9212-e1acd6893085"

                database = FirebaseFirestore.getInstance()
                database.collection("Kullanici").document(uid).set(kullaniciMap).addOnSuccessListener {
                    val intent = Intent(context, KullaniciActivity::class.java)
                    context.startActivity(intent)
                    context.finish()
                }
            }else{
                Toast.makeText(context,"Authentication failed", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }
}