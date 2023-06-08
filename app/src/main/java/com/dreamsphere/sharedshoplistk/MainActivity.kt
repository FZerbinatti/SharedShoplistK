package com.dreamsphere.sharedshoplistk

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.room.Room
import com.dreamsphere.sharedshoplistk.ui.theme.SharedShoplistKTheme





class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SharedShoplistKTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.main_page_background),
                    //color = colorResource(id = R.color.black)

                ) {
                    Column() {

                        TopBox_ID_Spesa()

                        SpesaList()
                    }
                }
            }
        }
    }
}



