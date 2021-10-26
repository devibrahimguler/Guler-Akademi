package com.muntazif.gulerakademi.model

class Egitimler(
    private var egitimid : String = "",
    private var egitimisim : String = "",
    private var egitimaciklama : String = "",
    private var egitimresim : String = ""
) {
    fun getEgitimId() : String{
        return egitimid
    }

    fun setEgitimId(egitimid: String){
        this.egitimid = egitimid
    }

    fun getEgitimIsim() : String{
        return egitimisim
    }

    fun setEgitimIsim(egitimisim: String){
        this.egitimisim = egitimisim
    }

    fun getEgitimAciklama() : String{
        return egitimaciklama
    }

    fun setEgitimAciklama(egitimaciklama: String){
        this.egitimaciklama = egitimaciklama
    }

    fun getEgitimResim() : String{
        return egitimresim
    }

    fun setEgitimResim(egitimresim: String){
        this.egitimresim = egitimresim
    }
}