package com.dreamsphere.sharedshoplistk.repository

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.dreamsphere.sharedshoplistk.R
import com.dreamsphere.sharedshoplistk.models.FirebaseShoplistItem
import com.dreamsphere.sharedshoplistk.models.ShopListItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Firebase : Application(){


    private val TAG = "Firebase"


    val database = Firebase.database("https://sharedshoplist-17901-default-rtdb.europe-west1.firebasedatabase.app/")

    //val sharedPref = getSharedPreferences(getString(R.string.SHOPLIST_ID), Context.MODE_PRIVATE)
    //val sharedPref = context.getPreferences(Context.MODE_PRIVATE)
    //val sharedPreferenceItem = sharedPref.getString(context.getString(R.string.SHOPLIST_ID), "0")

    val sharedPref = "AAA000"

    val firebase_shoplists_ids = database.getReference("shoplists_ids").child(sharedPref.toString())



    private fun fetchDataFromFirebase() {

        // firebase
        val response: MutableState<DataState> = mutableStateOf(DataState.Empty)

        val tempList = mutableListOf<ShopListItem>()
        response.value = DataState.Loading
        firebase_shoplists_ids
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children){
                        val item = DataSnap.getValue(ShopListItem::class.java)
                        if (item!=null){tempList.add(item)}
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value= DataState.Success(tempList)
                }
            })

    }

    fun addValueFirebase(shopListItem: ShopListItem){
        Log.d(TAG, "addValueFirebase: sharedPref: "+sharedPref)
        Log.d(TAG, "addValueFirebase: sharedPref.toString: "+sharedPref.toString())
        firebase_shoplists_ids.child(shopListItem.id.toString()).setValue(shopListItem)



    }


}