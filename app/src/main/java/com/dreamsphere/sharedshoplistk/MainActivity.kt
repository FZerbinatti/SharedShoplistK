package com.dreamsphere.sharedshoplistk

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dreamsphere.sharedshoplistk.repository.Room.IdDatabase
import com.dreamsphere.sharedshoplistk.repository.Room.IdItem
import com.dreamsphere.sharedshoplistk.repository.Room.IdRepository
import com.dreamsphere.sharedshoplistk.ui.theme.SharedShoplistKTheme
import com.dreamsphere.sharedshoplistk.view.PersonalizedAlertDialog
import com.dreamsphere.sharedshoplistk.view.ShoplistIdViewModelFactory
import com.dreamsphere.sharedshoplistk.view.SpesaList
import com.dreamsphere.sharedshoplistk.view.TopBox_ID_Spesa
import com.dreamsphere.sharedshoplistk.view.WindowCenterOffsetPositionProvider
import com.dreamsphere.sharedshoplistk.view.getRandomString
import com.dreamsphere.sharedshoplistk.viewmodel.MainViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private  val TAG = "MainActivity"

    lateinit var ViewModel: MainViewModel
    val context = this

    val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
        name = "shoplist_id"

    )




    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //room
            val shopListIdsRepository = IdRepository(IdDatabase(this))
            val factory = ShoplistIdViewModelFactory(shopListIdsRepository)
            ViewModel = ViewModelProvider(this,factory).get(MainViewModel::class.java)


            var spesaID = ""
            var showDialog = remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()
            val lazyListState = rememberLazyListState()
            val todoListState = ViewModel.shopListFlow.collectAsState()



            Log.d(TAG, "onCreate: 4")
            //var  numberOfIds = ViewModel.numberOfIds()
            ViewModel.allIdItems().observe(this, Observer {
                if (it.isEmpty()) {
                    spesaID = (getRandomString(10))
                    Log.d(TAG, "onCreate: id retrieved from database: 1: " +spesaID)
                    ViewModel.insert(IdItem(spesaID))

                }else{
                    spesaID= it.get(0).spesa_id.toString()
                    Log.d(TAG, "onCreate: id retrieved from database: 2: " +spesaID)
                }
            })

            // VIEW
            SharedShoplistKTheme() {
                Scaffold(
                    floatingActionButton = {
                        val context = LocalContext.current
                        FloatingActionButton(
                            backgroundColor = colorResource(id = R.color.button_color),
                            onClick = {
                                //apri alert dialog
                                showDialog.value = true
                            }) {
                            Icon(Icons.Default.Add, contentDescription = null)
                        }
                    },
                    content = {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            //MainView(viewModel, context)
                            Column() {

                                TopBox_ID_Spesa(spesaID)
                                SpesaList(ViewModel)

                                //popup per inserire nuovo item
                                if (showDialog.value==true) {
                                    Popup(
                                        popupPositionProvider = WindowCenterOffsetPositionProvider(),
                                        onDismissRequest = { showDialog.value = false },
                                        properties = PopupProperties(focusable = true)
                                    ) {
                                        PersonalizedAlertDialog({
                                            showDialog.value = false
                                            scope.launch {
                                                lazyListState.scrollToItem(todoListState.value.size)
                                            }
                                        }, ViewModel)
                                    }
                                }
                            }
                        }

                    }

                )
            }


        }
    }


}




//to do: add settings button where:
// - add bigger fontsize
// - add notification request for this list members



