package com.dreamsphere.sharedshoplistk

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SpesaList() {

    val context = LocalContext.current
    val languages = listOf(
        "C++", "C", "C#", "Java", "Kotlin", "Dart", "Python", "Javascript", "SpringBoot",
        "XML", "Dart", "Node JS", "Typescript", "Dot Net", "GoLang", "MongoDb",
    )

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
            modifier = Modifier.padding(10.dp)
        ) {

            items(languages) {
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Text(
                    it,
                    textAlign = TextAlign.Start,
                    fontFamily = robotoFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 24.sp,
                    color = colorResource(id = R.color.texts_color),
                )
                Spacer(modifier = Modifier.padding(vertical = 5.dp))

                Divider()
            }
        }
    }


}