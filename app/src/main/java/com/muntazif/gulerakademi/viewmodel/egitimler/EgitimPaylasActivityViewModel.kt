package com.muntazif.gulerakademi.viewmodel.egitimler

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.muntazif.gulerakademi.databinding.ActivityEgitimPaylasBinding
import java.util.*
import kotlin.collections.HashMap

class EgitimPaylasActivityViewModel : ViewModel() {

    private lateinit var egitimStorage : StorageReference
    private lateinit var firebaseUser : FirebaseUser
    private var resimUri : Uri? = null
    private var resimBitmap : Bitmap? = null
    private var benimUrl = ""

    fun setPermissionResult(context : Activity, requestCode: Int, grantResults: IntArray) {
        if(requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                context.startActivityForResult(galeriIntent,2)
            }
        }
    }

    fun setActivityResultSetting(binding : ActivityEgitimPaylasBinding, context: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            resimUri = data.data
            if (resimUri != null) {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source = ImageDecoder.createSource(context.contentResolver, resimUri!!)
                    resimBitmap = ImageDecoder.decodeBitmap(source)
                    binding.resimEgitimPaylasAc.setImageBitmap(resimBitmap)
                }else {
                    resimBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, resimUri)
                    binding.resimEgitimPaylasAc.setImageBitmap(resimBitmap)
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

    fun egitimPaylas(binding : ActivityEgitimPaylasBinding, context: Activity) {

        egitimStorage = FirebaseStorage.getInstance().reference.child("Egitimler")
        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        val isim = binding.isimEgitimPaylasAc.text.toString()
        val aciklama = binding.aciklamaEgitimPaylasAc.text.toString()
        val resim = resimUri

        when{

            resim == null -> {
                Toast.makeText(context, "Please select image first.", Toast.LENGTH_LONG).show()
            }
            TextUtils.isEmpty(isim)-> {
                Toast.makeText(context, "Please write description.", Toast.LENGTH_LONG).show()
            }
            TextUtils.isEmpty(aciklama)-> {
                Toast.makeText(context, "Please write description.", Toast.LENGTH_LONG).show()
            }

            else -> {

                val progressDialog = ProgressDialog(context)
                progressDialog.setTitle("Adding New Post")
                progressDialog.setMessage("Please wait, we are adding your picture post...")
                progressDialog.show()

                val fileRef = egitimStorage.child(System.currentTimeMillis().toString() + ".jpg")

                val uploadTask : StorageTask<*>
                uploadTask = fileRef!!.putFile(resimUri!!)
                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task->
                    if (!task.isSuccessful){
                        task.exception?.let {
                            throw it
                            progressDialog.dismiss()
                        }
                    }
                    return@Continuation fileRef.downloadUrl
                }).addOnCompleteListener(OnCompleteListener<Uri> { task ->
                    if (task.isSuccessful){
                        val downloadUrl = task.result
                        benimUrl = downloadUrl.toString()

                        val egitimDatabase = FirebaseFirestore.getInstance().collection("Egitimler")
                        val postId = UUID.randomUUID().toString()

                        val egitimMap = HashMap<String, Any>()
                        egitimMap["egitimid"] = postId
                        egitimMap["egitimisim"] = binding.isimEgitimPaylasAc.text.toString().toLowerCase()
                        egitimMap["egitimaciklama"] = binding.aciklamaEgitimPaylasAc.text.toString().toLowerCase()
                        egitimMap["egitimresim"] = benimUrl

                        egitimDatabase.document(postId).set(egitimMap).addOnSuccessListener {
                            Toast.makeText(context,"Egitim Yüklendi.", Toast.LENGTH_LONG).show()

                            context.finish()
                        }

                    }else{

                        progressDialog.dismiss()
                    }
                })
            }
        }
    }

}