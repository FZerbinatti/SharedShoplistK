package com.dreamsphere.sharedshoplistk

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

suspend fun getDataFromFireStore(): ShopListItem{

    /*val database = Firebase.database("https://sharedshoplist-17901-default-rtdb.europe-west1.firebasedatabase.app/")
    val myRef = database.getReference("shoplist_ids")*/
    return ShopListItem(0, "",false)
}