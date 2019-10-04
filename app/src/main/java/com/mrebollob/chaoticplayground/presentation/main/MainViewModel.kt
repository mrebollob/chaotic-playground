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

package com.mrebollob.chaoticplayground.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrebollob.chaoticplayground.data.MarvelRepositoryImp
import com.mrebollob.chaoticplayground.domain.entity.MarvelComic
import com.mrebollob.chaoticplayground.domain.exception.PlayGroundException
import com.mrebollob.chaoticplayground.domain.interactor.SortComicsByPriceUseCase
import com.mrebollob.chaoticplayground.domain.interactor.SortType
import com.mrebollob.chaoticplayground.domain.interactor.switch
import com.mrebollob.chaoticplayground.presentation.platform.LoadingState
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repositoryImp: MarvelRepositoryImp,
    private val sortComicsByPriceUseCase: SortComicsByPriceUseCase
) : ViewModel() {

    private val _screenState = MutableLiveData<MainScreenState>()
    val screenState: LiveData<MainScreenState>
        get() = _screenState

    private var comics: List<MarvelComic> = emptyList()
    private var sortType = SortType.UNKNOWN

    init {
        _screenState.value = MainScreenState()

        viewModelScope.launch {
            repositoryImp.getComics().either(::handleError, ::handleComics)
        }
    }

    fun onSortClick() {
        sortType = sortType.switch()
        _screenState.value = _screenState.value?.copy(
            comics = sortComicsByPriceUseCase.sort(sortType, comics)
        )
    }

    private fun handleComics(comics: List<MarvelComic>) {
        this.comics = comics
        _screenState.value = _screenState.value?.copy(
            comics = comics,
            comicsState = LoadingState.Ready
        )
    }

    private fun handleError(exception: PlayGroundException) {
        _screenState.value = _screenState.value?.copy(
            comicsState = LoadingState.Error
        )
    }
}