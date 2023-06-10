package com.dreamsphere.sharedshoplistk

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.provider.Settings.Global.getString
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun SpesaList(viewModel: MainViewModel) {


        val todoListState = viewModel.todoListFlow.collectAsState()
        val lazyListState = rememberLazyListState()
        val scope = rememberCoroutineScope()




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
                    items = todoListState.value,
                    key = { todoItem -> todoItem.id },
                    itemContent = { item ->
                        val currentItem by rememberUpdatedState(item)
                        val dismissState = rememberDismissState(
                            confirmStateChange = {
                                if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                                    viewModel.removeRecord(currentItem)
                                    true
                                } else false
                            }
                        )

                        if (dismissState.isDismissed(DismissDirection.EndToStart) ||
                            dismissState.isDismissed(DismissDirection.StartToEnd)){
                            viewModel.removeRecord(item)
                        }

                        SwipeToDismiss(
                            state = dismissState,
                            modifier = Modifier
                                .padding(vertical = 1.dp)
                                .animateItemPlacement(),
                            directions = setOf(
                                DismissDirection.StartToEnd,
                                DismissDirection.EndToStart
                            ),
                            dismissThresholds = { direction ->
                                FractionalThreshold(
                                    if (direction == DismissDirection.StartToEnd) 0.66f else 0.50f
                                )
                            },
                            background = {
                                SwipeBackground(dismissState)
                            },
                            dismissContent = {
                                ShopListItemRow(item, todoListState, viewModel)
                            }
                        )
                    }
                )
            }
        }


}



