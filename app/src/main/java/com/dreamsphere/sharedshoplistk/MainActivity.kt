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
import com.dreamsphere.sharedshoplistk.ui.theme.SharedShoplistKTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val viewModel = MainViewModel()
    val context = this



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





            if (sharedPreferenceItem.equals("0")) {
                spesaID = (getRandomString(10))
                val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return@setContent
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

                                TopBox_ID_Spesa(spesaID)
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



