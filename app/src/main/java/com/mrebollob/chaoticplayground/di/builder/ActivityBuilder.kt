package com.mrebollob.chaoticplayground.di.builder

import com.mrebollob.chaoticplayground.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}