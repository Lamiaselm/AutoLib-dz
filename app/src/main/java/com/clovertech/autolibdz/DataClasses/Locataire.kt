package com.clovertech.autolibdz.DataClasses

data class Locataire (
    val idTenant:Int,
    val idUser:Int,
    val refPermit:String,
    val profilePicture:String,
    val permitPicture:String,
    val selfie:String,
    val accountState:String,
    val stateMessage:String,
    val validationDate:String,
    val subCard:Int,
    val points:Int
)