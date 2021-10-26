package com.muntazif.gulerakademi.viewmodel.kullanici

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.muntazif.gulerakademi.R
import com.muntazif.gulerakademi.databinding.ActivityEgitimPaylasBinding
import com.muntazif.gulerakademi.databinding.ActivityProfilDuzenleBinding
import com.muntazif.gulerakademi.model.Kullanici
import com.squareup.picasso.Picasso


class ProfilDuzenleActivityViewModel : ViewModel() {

    lateinit var auth: FirebaseAuth
    lateinit var firebaseUser : FirebaseUser
    lateinit var kullaniciResimStorage : StorageReference
    private var benimUrl : String = ""
    private var resimUri : Uri? = null
    private var resimBitmap : Bitmap? = null
    private var kontrol = ""

    fun setSelectImage(context : Activity) {
        kontrol = "resim"

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            context.startActivityForResult(galeriIntent,2)
        }
    }

    fun getUpdateControl(binding : ActivityProfilDuzenleBinding, context : Activity) {
        if (kontrol == "resim"){
            kullaniciResimBilgiGuncelle(binding, context)
        }else{
            KullaniciBilgiGuncelle(
                context,
                binding.isimProfilDuzenleAc,
                binding.kimlikProfilDuzenleAc,
                binding.sifreProfilDuzenleAc,
            )
        }
    }

    fun KullaniciBilgi(binding : ActivityProfilDuzenleBinding){
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val kullaniciRef = FirebaseFirestore.getInstance().collection("Kullanici").document(firebaseUser.uid)

        kullaniciRef.get().addOnSuccessListener { document ->
            val kullanici = document.toObject<Kullanici>()

            Picasso.get().load(kullanici!!.getResim()).placeholder(R.drawable.kullanici_resmi).into(binding.resimProfilDuzenleAc)
            binding.isimProfilDuzenleAc.setText(kullanici.getIsim())
            binding.kimlikProfilDuzenleAc.setText(kullanici.getKimlik())
            binding.sifreProfilDuzenleAc.setText(kullanici.getSifre())
        }
    }

    fun kullaniciResimBilgiGuncelle(
        binding : ActivityProfilDuzenleBinding,
        activity : Activity
    ){

        val isim = binding.isimProfilDuzenleAc.text.toString().trim()
        val kimlik = binding.kimlikProfilDuzenleAc.text.toString().trim()
        val sifre = binding.sifreProfilDuzenleAc.text.toString().trim()
        val resim = resimUri

        when {
            resim == null -> {
                Toast.makeText(activity, "Lütfen resim Giriniz", Toast.LENGTH_LONG).show()
            }
            TextUtils.isEmpty(isim) -> {
                binding.isimProfilDuzenleAc.error = "İsim Giriniz"
                return
            }
            TextUtils.isEmpty(kimlik) -> {
                binding.kimlikProfilDuzenleAc.error = "Sınıf Giriniz"
                return
            }
            TextUtils.isEmpty(sifre) -> {
                binding.sifreProfilDuzenleAc.error = "Sifre Giriniz"
                return
            }
            else -> {

                BilgiResim(activity, isim, kimlik, sifre, resim)

            }
        }
    }

    private fun BilgiResim(
        context : Activity,
        isim : String,
        kimlik : String,
        sifre : String,
        resimUri : Uri
    ){
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        kullaniciResimStorage = FirebaseStorage.getInstance().reference.child("Kullanici Resimleri")
        val fileRef = kullaniciResimStorage.child(firebaseUser.uid + ".jpg")

        val uploadTask : StorageTask<*>
        uploadTask = fileRef.putFile(resimUri)
        uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task->
            if (!task.isSuccessful){
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation fileRef.downloadUrl
        }).addOnCompleteListener(OnCompleteListener<Uri> { task ->
            if (task.isSuccessful){
                val downloadUrl = task.result
                benimUrl = downloadUrl.toString()

                val kullaniciRef = FirebaseFirestore.getInstance().collection("Kullanici")
                val kullaniciMap = HashMap<String, Any>()

                kullaniciMap["isim"] = isim
                kullaniciMap["kimlik"] = kimlik
                kullaniciMap["sifre"] = sifre
                kullaniciMap["resim"] = benimUrl

                kullaniciRef.document(firebaseUser.uid).update(kullaniciMap)

                context.finish()

            }
        })
    }

    fun KullaniciBilgiGuncelle(
        activity : Activity,
        isim_edit_text : EditText,
        kimlik_edit_text : EditText,
        sifre_edit_text : EditText
    ){

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        val isim = isim_edit_text.text.toString().trim()
        val kimlik = kimlik_edit_text.text.toString().trim()
        val sifre = sifre_edit_text.text.toString().trim()

        when {
            TextUtils.isEmpty(isim) -> {
                isim_edit_text.error = "İsim Giriniz"
                return
            }
            TextUtils.isEmpty(kimlik) -> {
                kimlik_edit_text.error = "Sınıf Giriniz"
                return
            }
            TextUtils.isEmpty(sifre) -> {
                sifre_edit_text.error = "Sifre Giriniz"
                return
            }
            else -> {
                    Bilgi(activity, isim, kimlik, sifre)
            }
        }

    }

    private fun Bilgi(
        activity : Activity,
        isim : String,
        kimlik : String,
        sifre : String
    ){

        val kullaniciRef = FirebaseFirestore.getInstance().collection("Kullanici")
        val kullaniciMap = HashMap<String, Any>()

        kullaniciMap["isim"] = isim
        kullaniciMap["kimlik"] = kimlik
        kullaniciMap["sifre"] = sifre

        kullaniciRef.document(firebaseUser.uid).update(kullaniciMap)

        activity.finish()
    }

    fun setActivityResultSetting(binding : ActivityProfilDuzenleBinding, context: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            resimUri = data.data
            if (resimUri != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(context.contentResolver, resimUri!!)
                    resimBitmap = ImageDecoder.decodeBitmap(source)
                    binding.resimProfilDuzenleAc.setImageBitmap(resimBitmap)
                }else {
                    resimBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, resimUri)
                    binding.resimProfilDuzenleAc.setImageBitmap(resimBitmap)
                }
            }
        }
    }

    fun setPhotoCrop(context: Activity) {
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            context.startActivityForResult(galeriIntent,2)
        }
    }

    fun setPermissionResult(context : Activity, requestCode: Int, grantResults: IntArray) {
        if(requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                context.startActivityForResult(galeriIntent,2)
            }
        }
    }

}