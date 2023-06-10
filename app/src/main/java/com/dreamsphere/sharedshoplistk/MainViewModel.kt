package com.dreamsphere.sharedshoplistk

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private var shopList = mutableStateListOf<ShopListItem>()
    private val _shopListFlow = MutableStateFlow(shopList)

    val shopListFlow: StateFlow<List<ShopListItem>> get() = _shopListFlow

    fun setUrgent(index: Int, value: Boolean) {
        shopList[index] = shopList[index].copy(item_checked = value)
    }





    fun addRecord(titleText: String, urgency: Boolean) {
        if(shopList.isEmpty()){
            shopList.add(ShopListItem(1,titleText ,false))
        }else{
            shopList.add(ShopListItem(shopList.last().id +1,titleText ,false))

        }
    }

    fun removeRecord(todoItem: ShopListItem) {
        val index = shopList.indexOf(todoItem)
        shopList.remove(shopList[index])
    }
}
