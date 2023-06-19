package com.dreamsphere.sharedshoplistk.repository.Firebase

import com.dreamsphere.sharedshoplistk.models.ShopListItem

sealed class DataState {

    class Success(val data: MutableList<ShopListItem>): DataState()
    class Failure(val message: String): DataState()
    object Loading: DataState()
    object Empty: DataState()



}