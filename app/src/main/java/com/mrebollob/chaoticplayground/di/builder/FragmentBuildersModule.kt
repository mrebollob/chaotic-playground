package com.mrebollob.chaoticplayground.di.builder

import com.mrebollob.chaoticplayground.presentation.main.flats.FlatsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeFlatsFragment(): FlatsFragment
}