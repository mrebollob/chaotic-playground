/*
 *   Copyright (C) 2019 mrebollob.
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.mrebollob.chaoticplayground.presentation.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrebollob.chaoticplayground.data.auth.SessionManager
import com.mrebollob.chaoticplayground.domain.entity.User
import com.mrebollob.chaoticplayground.domain.exception.PlayGroundException
import com.mrebollob.chaoticplayground.domain.repository.HouseRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val repository: HouseRepository
) : ViewModel() {

    private val _screenState = MutableLiveData<ProfileScreenState>()
    val screenState: LiveData<ProfileScreenState>
        get() = _screenState


    init {
        _screenState.value = ProfileScreenState.Loading
        viewModelScope.launch {
            sessionManager.getUser().either(::handleError, ::handleComics)
        }
    }

    private fun handleComics(user: User) {
        Timber.d("User: $user")
        _screenState.value = ProfileScreenState.Ready(user)
    }

    private fun handleError(exception: PlayGroundException) {
        _screenState.value = ProfileScreenState.Error
    }

    fun onSignOutClick() {
        viewModelScope.launch {
            repository.clearData()
            sessionManager.signOut()
            sessionManager.getUser().either(::handleError, ::handleComics)
        }
    }
}