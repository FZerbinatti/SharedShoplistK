package com.dreamsphere.sharedshoplistk.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dreamsphere.sharedshoplistk.repository.Room.IdRepository
import com.dreamsphere.sharedshoplistk.viewmodel.MainViewModel


class ShoplistIdViewModelFactory(private val repository: IdRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}