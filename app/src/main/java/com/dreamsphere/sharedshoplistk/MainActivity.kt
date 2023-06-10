package com.dreamsphere.sharedshoplistk

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dreamsphere.sharedshoplistk.ui.theme.SharedShoplistKTheme
import kotlinx.coroutines.android.awaitFrame


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //val defaultValue = resources.getString(R.string.SHOPLIST_ID)
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val highScore = sharedPref.getString(getString(R.string.SHOPLIST_ID), "0")
        var spesaID = ""


        Log.d("TAG", "onCreate: ???????????????????????????? " + highScore)

        //val editor = sharedPref.getString(R.string.SHOPLIST_ID)

        if (highScore.equals("0")) {
            spesaID = (getRandomString(10))
            val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref.edit()) {
                putString(getString(R.string.SHOPLIST_ID), spesaID)
                apply()
            }
        } else {
            spesaID = highScore.orEmpty()
        }
        setContent {
            SharedShoplistKTheme {
                var showDialog = remember { mutableStateOf(false) }
                val shopList = remember{ mutableStateListOf<ShopListItem>() }




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
                            color = colorResource(id = R.color.main_page_background),

                            ) {
                            Column() {
                                TopBox_ID_Spesa(spesaID)
                                spesaList(shopList)
                            }
                        }
                    }
                )

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


                                TextField(modifier =Modifier.focusRequester(focusRequester),

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
                                    shopList.add(ShopListItem(shoplist_item.value.replaceFirstChar { it.uppercase() },false))
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
        }
    }



    fun makeToast(ctx: Context, msg: String) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show()
    }

    @Composable
    fun spesaList(shopList: SnapshotStateList<ShopListItem>) {


        val robotoFont = FontFamily(
            Font(R.font.robotthin, FontWeight.Thin),
            Font(R.font.robotoregular, FontWeight.Normal),
            Font(R.font.robotobold, FontWeight.Bold),
            )

        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 10.dp)

                .fillMaxWidth()
                .fillMaxHeight()
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(15.dp)
                )
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)



        ) {
            LazyColumn(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(10.dp),
                state = LazyListState()
            ) {

                items(
                    items = shopList,

                ) {
                    Spacer(modifier = Modifier.padding(vertical = 5.dp))
                    Text(
                        it.item_name,
                        textAlign = TextAlign.Start,
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 22.sp,
                        color = colorResource(id = R.color.texts_color),
                    )
                    Spacer(modifier = Modifier.padding(vertical = 5.dp))

                    Divider()
                }
            }
        }

    }


}


//to do: add settings button where:
// - add bigger fontsize
// - add notification request for this list members



