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
package com.mrebollob.chaoticplayground.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrebollob.chaoticplayground.di.PlaygroundViewModelFactory
import com.mrebollob.chaoticplayground.di.annotation.ViewModelKey
import com.mrebollob.chaoticplayground.presentation.form.FormViewModel
import com.mrebollob.chaoticplayground.presentation.main.houses.HousesViewModel
import com.mrebollob.chaoticplayground.presentation.main.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HousesViewModel::class)
    abstract fun bindHousesViewModel(housesViewModel: HousesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FormViewModel::class)
    abstract fun bindFormViewModel(formViewModel: FormViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: PlaygroundViewModelFactory):
            ViewModelProvider.Factory
}