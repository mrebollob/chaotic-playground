package com.mrebollob.chaoticplayground.di.builder

import com.mrebollob.chaoticplayground.presentation.main.flats.FlatsFragment
import com.mrebollob.chaoticplayground.presentation.main.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeFlatsFragment(): FlatsFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment
}