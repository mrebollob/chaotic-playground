package com.mrebollob.chaoticplayground.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrebollob.chaoticplayground.data.MarvelRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MarvelRepository) : ViewModel() {

    private val _snackBar = MutableLiveData<String>()
    val snackbar: LiveData<String>
        get() = _snackBar

    fun init() {
        viewModelScope.launch {
            val retrivedTodo = repository.getTodo(1)
            _snackBar.value = retrivedTodo.title
        }
    }
}