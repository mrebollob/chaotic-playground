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

package com.mrebollob.chaoticplayground.presentation.main.flats

import com.mrebollob.chaoticplayground.domain.entity.MarvelComic
import com.mrebollob.chaoticplayground.presentation.platform.LoadingState

data class MainScreenState(
    val comics: List<MarvelComic> = emptyList(),
    val comicsState: LoadingState = LoadingState.Loading
)