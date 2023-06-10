package com.dreamsphere.sharedshoplistk

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dreamsphere.sharedshoplistk.ui.theme.SharedShoplistKTheme
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun MainView(viewModel: MainViewModel, context: Context){





    //Log.d("TAG", "onCreate: ???????????????????????????? " + sharedPreferenceItem)

    //val editor = sharedPref.getString(R.string.SHOPLIST_ID)



            var showDialog = remember { mutableStateOf(false) }
            val shopList = remember{ mutableStateListOf<ShopListItem>() }
            val todoListState = viewModel.todoListFlow.collectAsState()
            val lazyListState = rememberLazyListState()
            val scope = rememberCoroutineScope()





            if (showDialog.value){
                var shoplist_item = remember { mutableStateOf("") }
                val context = LocalContext.current
                val focusRequester = remember { FocusRequester() }

                LaunchedEffect(focusRequester) {
                    awaitFrame()
                    focusRequester.requestFocus()
                }

                AlertDialog(

                    onDismissRequest = { showDialog.value = false },

                    title = { Text(text = "") },

                    text = {
                        Column(Modifier.fillMaxWidth()) {


                            TextField(modifier = Modifier.focusRequester(focusRequester),

                                value = shoplist_item.value,
                                onValueChange = { shoplist_item.value = it },
                                textStyle = TextStyle.Default.copy(fontSize = 20.sp)
                            )
                        }
                    },
                    //on confirm, se il textbox non è vuoto, compialo nella casella
                    confirmButton = {
                        Button(onClick = {

                            if (!shoplist_item.value.isEmpty()) {
                                var spesa_ID = shoplist_item.value
                                Log.d("TAG", "onCreate: ==================================== "+shoplist_item.value)
                                // prendi la value e aggiungila alla lista + metti la prima lettera maiuscola
                                //shopList.add(ShopListItem(shoplist_item.value.replaceFirstChar { it.uppercase() },false))
                                /*AddRecord({
                                    scope.launch {
                                        lazyListState.scrollToItem(todoListState.value.size)
                                    }
                                }, viewModel)*/
                                showDialog.value = false
                            } else {
                                Toast.makeText(context, "campo vuoto", Toast.LENGTH_LONG).show()
                            }
                        }) {
                            Text(text = "Conferma")
                        }
                    },
                    //on dismiss cancella l'alert
                    dismissButton = {
                        Button(colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.button_color)), onClick = { showDialog.value = false }) {
                            Text(text = "Annulla")

                        }
                    }
                )
            }







}