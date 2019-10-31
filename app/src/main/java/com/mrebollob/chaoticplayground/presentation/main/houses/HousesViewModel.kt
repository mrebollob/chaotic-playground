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
package com.mrebollob.chaoticplayground.presentation.main.houses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrebollob.chaoticplayground.domain.entity.House
import com.mrebollob.chaoticplayground.domain.exception.PlayGroundException
import com.mrebollob.chaoticplayground.domain.repository.HouseRepository
import com.mrebollob.chaoticplayground.presentation.platform.LoadingState
import kotlinx.coroutines.launch
import javax.inject.Inject

class HousesViewModel @Inject constructor(
    private val repository: HouseRepository
) : ViewModel() {

    private val _screenState = MutableLiveData<HousesScreenState>()
    val screenState: LiveData<HousesScreenState>
        get() = _screenState

    init {
        _screenState.value = HousesScreenState()
        refreshHouses()
    }

    private fun refreshHouses() {
        viewModelScope.launch {
            repository.getHouses().either(::handleError, ::handleHouses)
        }
    }

    private fun handleHouses(houses: List<House>) {
        _screenState.value = _screenState.value?.copy(
            houses = houses,
            housesState = LoadingState.Ready
        )
    }

    private fun handleError(exception: PlayGroundException) {
        _screenState.value = _screenState.value?.copy(
            housesState = LoadingState.Error
        )
    }
}