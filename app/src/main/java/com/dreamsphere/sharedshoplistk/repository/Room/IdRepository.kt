package com.dreamsphere.sharedshoplistk.repository.Room

import com.dreamsphere.sharedshoplistk.view.getRandomString


class IdRepository(private val db: IdDatabase) {

    suspend fun insert(item: IdItem) = db.getShopListIdDao().insert(item)
    suspend fun delete(item: IdItem) = db.getShopListIdDao().delete(item)


    fun allIdItems() = db.getShopListIdDao().getAllIds()

    fun numberOfIds()= db.getShopListIdDao().countAllIds()

     //suspend fun getAnId() = db.getShopListIdDao().getAnId()

}