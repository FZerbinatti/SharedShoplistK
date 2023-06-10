package com.dreamsphere.sharedshoplistk

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private var todoList = mutableStateListOf(
        ShopListItem(0, "My First Task"),
        ShopListItem(1, "My Second Task", true),
        ShopListItem(2, "My Third Task"),
        ShopListItem(3, "My Forth Task"),
        ShopListItem(4, "My Fifth Task"),
        ShopListItem(5, "My Sixth Task"),
        ShopListItem(6, "My Seventh Task"),
        ShopListItem(7, "My Eighth Task"),
        ShopListItem(8, "My Ninth Task"),
        ShopListItem(9, "My Tenth Task"),
        ShopListItem(10, "My Eleventh Task"),
        ShopListItem(11, "My Twelfth Task"),
        ShopListItem( 12,"My Thirteen Task"),
        ShopListItem( 13,"My Fourteen Task"),
        ShopListItem( 14,"My Fifteen Task"),
        ShopListItem( 15,"My Sixteen Task"),
        ShopListItem( 16,"My Seventeenth Task"),
        ShopListItem( 17,"My Eighteenth Task"),
        ShopListItem( 18,"My Nineteenth Task"),
        ShopListItem( 19,"My Twentieth Task"),
    )
    private val _todoListFlow = MutableStateFlow(todoList)

    val todoListFlow: StateFlow<List<ShopListItem>> get() = _todoListFlow

    fun setUrgent(index: Int, value: Boolean) {
        todoList[index] = todoList[index].copy(item_checked = value)
    }

    fun generateRandomTodo() {
        val numberOfTodo = (10..20).random()
        val mutableTodoList = mutableStateListOf<ShopListItem>()
        (0..numberOfTodo).forEach {
            val todoItem = ShopListItem( it,"Item $it}", Random.nextBoolean())
            mutableTodoList.add(todoItem)
        }
        todoList = mutableTodoList
        _todoListFlow.value = mutableTodoList
    }

    private fun randomWord(): String {
        val random = Random
        val sb = StringBuilder()
        for (i in 1..random.nextInt(10) + 5) {
            sb.append(('a' + random.nextInt(26)))
        }
        return sb.toString()
    }

    fun addRecord(titleText: String, urgency: Boolean) {
        todoList.add(ShopListItem(todoList.last().id +1,titleText ,false))
    }

    fun removeRecord(todoItem: ShopListItem) {
        val index = todoList.indexOf(todoItem)
        todoList.remove(todoList[index])
    }
}
