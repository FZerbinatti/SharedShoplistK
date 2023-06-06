package com.dreamsphere.sharedshoplistk

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBox_ID_Spesa(){

    val context = LocalContext.current

    val robotoFont = FontFamily(
        Font(R.font.robotthin, FontWeight.Thin),
        Font(R.font.robotoregular, FontWeight.Normal),
        Font(R.font.robotobold, FontWeight.Bold),)

    val spesa_ID = remember { mutableStateOf("Y751DR9ZOA") }

    Row(verticalAlignment = Alignment.CenterVertically) {

        Row(
            Modifier
                .padding(10.dp)

                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(15.dp)
                )
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)

        ) {

            Box() {


                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "ID Spesa:",
                        fontFamily = robotoFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,

                        color = colorResource(id = R.color.texts_color),
                        modifier = Modifier
                            .padding(10.dp)
                    )
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            //.fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.White),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            spesa_ID.value,
                            fontFamily = robotoFont,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = colorResource(id = R.color.texts_color),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 15.dp, ),

                            )

                        Button(
                            onClick = {

                                val clipboardManager =context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clipData = ClipData.newPlainText("ID_Spesa", spesa_ID.value)
                                clipboardManager.setPrimaryClip(clipData)
                                Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_LONG).show()
                            },
                            Modifier.height(50.dp),
                            border = BorderStroke(1.dp, Color.Gray),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Gray,
                                backgroundColor = Color.White
                            )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_copy),
                                contentDescription = null
                            )
                        }


                    }




                }

            }

        }
        Button(
            onClick = { },
            Modifier.width(40.dp).height(60.dp).padding(0.dp,0.dp,10.dp,0.dp),
            border = BorderStroke(1.dp, Color.Gray),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Gray,
                backgroundColor = Color.White)) {
            Image(
                painter = painterResource(id = R.drawable.ic_copy),
                contentDescription = null,
                contentScale = ContentScale.None,

                )
        }


    }




}