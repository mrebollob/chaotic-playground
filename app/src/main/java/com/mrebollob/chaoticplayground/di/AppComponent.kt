package com.mrebollob.chaoticplayground.di

import android.app.Application
import com.mrebollob.chaoticplayground.di.builder.ActivityBuilder
import com.mrebollob.chaoticplayground.di.module.ApiModule
import com.mrebollob.chaoticplayground.di.module.AppModule
import com.mrebollob.chaoticplayground.presentation.platform.PlaygroundApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ApiModule::class,
        ActivityBuilder::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: PlaygroundApp)
}