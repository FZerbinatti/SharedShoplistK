package com.dreamsphere.sharedshoplistk

sealed class DataState {

    class Success(val data: MutableList<ShopListItem>): DataState()
    class Failure(val message: String): DataState()
    object Loading:DataState()
    object Empty: DataState()



}