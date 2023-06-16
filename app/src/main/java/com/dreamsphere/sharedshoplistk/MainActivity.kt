package com.dreamsphere.sharedshoplistk

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
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
import com.dreamsphere.sharedshoplistk.ui.theme.SharedShoplistKTheme
import com.dreamsphere.sharedshoplistk.view.PersonalizedAlertDialog
import com.dreamsphere.sharedshoplistk.view.SpesaList
import com.dreamsphere.sharedshoplistk.view.TopBox_ID_Spesa
import com.dreamsphere.sharedshoplistk.view.WindowCenterOffsetPositionProvider
import com.dreamsphere.sharedshoplistk.view.getRandomString
import com.dreamsphere.sharedshoplistk.viewmodel.MainViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val viewModel = MainViewModel()
    val context = this

    val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
        name = "shoplist_id"

    )




    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //getting shared Preference Spesa ID
            //val defaultValue = resources.getString(R.string.SHOPLIST_ID)
            val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return@setContent

            val sharedPreferenceItem = sharedPref.getString(context.getString(R.string.SHOPLIST_ID), "0")
            var spesaID = ""
            var showDialog = remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()
            val lazyListState = rememberLazyListState()
            val todoListState = viewModel.shopListFlow.collectAsState()

            // metti caricamenti per ID e per list
            // inizlizza il caricemnto/ricerca ID e poi popola i campi



            if (sharedPreferenceItem.equals("0")) {
                spesaID = (getRandomString(10))
                //val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return@setContent
                with(sharedPref.edit()) {
                    putString(getString(com.dreamsphere.sharedshoplistk.R.string.SHOPLIST_ID), spesaID)
                    apply()
                }
            } else {
                spesaID = sharedPreferenceItem.orEmpty()
            }

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

                                TopBox_ID_Spesa(viewModel.shoplistID)
                                SpesaList(viewModel)

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
                                        }, viewModel)
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



