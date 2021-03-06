package org.blitzortung.android.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.blitzortung.android.app.AppService
import org.blitzortung.android.app.Main
import org.blitzortung.android.app.Preferences

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): Main

    @ContributesAndroidInjector
    abstract fun contributePreferencesActivityInjector(): Preferences

    @ContributesAndroidInjector
    abstract fun contributesAppServiceInjector(): AppService
}