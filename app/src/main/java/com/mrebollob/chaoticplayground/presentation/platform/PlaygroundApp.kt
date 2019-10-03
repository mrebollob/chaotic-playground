package com.mrebollob.chaoticplayground.presentation.platform

import android.app.Activity
import android.app.Application
import com.mrebollob.chaoticplayground.BuildConfig
import com.mrebollob.chaoticplayground.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class PlaygroundApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        initInjector()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initInjector() {
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}