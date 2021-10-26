package com.muntazif.gulerakademi.model

class Kullanici(
    private var id : String = "",
    private var isim : String = "",
    private var kimlik : String = "",
    private var email : String = "",
    private var sifre : String = "",
    private var resim : String = "",
) {
    fun getId() : String{
        return id
    }

    fun setId(id: String){
        this.id = id
    }

    fun getIsim() : String{
        return isim
    }

    fun setIsim(isim: String){
        this.isim = isim
    }

    fun getKimlik() : String{
        return kimlik
    }

    fun setKimlik(kimlik: String){
        this.kimlik = kimlik
    }

    fun getEmail() : String{
        return email
    }

    fun setEmail(email: String){
        this.email = email
    }

    fun getSifre() : String{
        return sifre
    }

    fun setSifre(sifre: String){
        this.sifre = sifre
    }

    fun getResim() : String{
        return resim
    }

    fun setResim(resim: String){
        this.resim = resim
    }
}