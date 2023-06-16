package com.dreamsphere.sharedshoplistk.viewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import com.dreamsphere.sharedshoplistk.models.ShopListItem
import com.dreamsphere.sharedshoplistk.repository.DataState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    val firebase = com.dreamsphere.sharedshoplistk.repository.Firebase()
    val shoplistID = String()
    private var shopList = mutableStateListOf<ShopListItem>()
    private val SHOPLIST_ID = stringPreferencesKey("shoplist_id")

    //return to Room



    private var shopList1 = mutableStateListOf(
        ShopListItem(0, "My First Task", false),
        ShopListItem(1, "My Second Task", true),
        ShopListItem(2, "My Third Task", true),
        //https://www.youtube.com/watch?v=J4QywOWZdoo
    )




    private val _shopListFlow = MutableStateFlow(shopList)






    val shopListFlow: StateFlow<List<ShopListItem>> get() = _shopListFlow

    fun setUrgent(index: Int, value: Boolean) {
        shopList[index] = shopList[index].copy(item_checked = value)
    }

    public fun addRecord(titleText: String, checked: Boolean) {
        if(shopList.isEmpty()){
            shopList.add(ShopListItem(1,titleText ,checked))
            firebase.addValueFirebase(ShopListItem(1,titleText ,checked))
        }else{
            shopList.add(ShopListItem(shopList.last().id +1,titleText ,checked))
            firebase.addValueFirebase(ShopListItem(shopList.last().id +1,titleText ,checked))


        }

        // add record on firebase
        //firebase_shoplists_ids.
    }

    fun removeRecord(todoItem: ShopListItem) {
        val index = shopList.indexOf(todoItem)
        shopList.remove(shopList[index])
    }


}
