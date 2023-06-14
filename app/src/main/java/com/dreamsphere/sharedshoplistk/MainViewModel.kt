package com.dreamsphere.sharedshoplistk

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private var shopList1 = mutableStateListOf<ShopListItem>()
    val database = Firebase.database("https://sharedshoplist-17901-default-rtdb.europe-west1.firebasedatabase.app/")
    val firebase_shoplists_ids = database.getReference("shoplists_ids")
    private var shopList = mutableStateListOf(
        ShopListItem(0, "My First Task", false),
        ShopListItem(1, "My Second Task", true),
        ShopListItem(2, "My Third Task", true),
        //https://www.youtube.com/watch?v=J4QywOWZdoo
    )




    private val _shopListFlow = MutableStateFlow(shopList)


    // firebase
    val response: MutableState<DataState> = mutableStateOf(DataState.Empty)

    init {

        //fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {



        val tempList = mutableListOf<ShopListItem>()
        response.value = DataState.Loading
        firebase_shoplists_ids
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (DataSnap in snapshot.children){
                        val item = DataSnap.getValue(ShopListItem::class.java)
                        if (item!=null){tempList.add(item)}
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.value=DataState.Success(tempList)
                }
            })

    }

    val shopListFlow: StateFlow<List<ShopListItem>> get() = _shopListFlow

    fun setUrgent(index: Int, value: Boolean) {
        shopList[index] = shopList[index].copy(item_checked = value)
    }





    public fun addRecord(titleText: String, checked: Boolean) {
        if(shopList.isEmpty()){
            shopList.add(ShopListItem(1,titleText ,checked))
        }else{
            shopList.add(ShopListItem(shopList.last().id +1,titleText ,checked))

        }

        // add record on firebase
        //firebase_shoplists_ids.
    }

    fun removeRecord(todoItem: ShopListItem) {
        val index = shopList.indexOf(todoItem)
        shopList.remove(shopList[index])
    }
}
