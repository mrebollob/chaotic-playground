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
package com.mrebollob.chaoticplayground.presentation.form

import com.mrebollob.chaoticplayground.domain.entity.Requirement
import com.mrebollob.chaoticplayground.presentation.platform.LoadingState
import com.mrebollob.chaoticplayground.presentation.platform.getCombinedLoadingState

data class FormScreenState(
    val requirements: List<Requirement> = emptyList(),
    val created: Boolean = false,
    private val requirementsState: LoadingState = LoadingState.Loading,
    private val createHouseState: LoadingState = LoadingState.Ready
) {
    val loadingState: LoadingState
        get() = listOf(
            requirementsState,
            createHouseState
        ).getCombinedLoadingState()
}