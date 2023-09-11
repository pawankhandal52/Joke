package com.unlimit.jokes.core

import android.app.Application
import com.unlimit.jokes.koin.apiHelperModule
import com.unlimit.jokes.koin.databaseModule
import com.unlimit.jokes.koin.jokeHelperModule
import com.unlimit.jokes.koin.jokeRepositoryImplModule
import com.unlimit.jokes.koin.retrofitModule
import com.unlimit.jokes.koin.useCaseModule
import com.unlimit.jokes.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Joke:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Joke)
            modules(databaseModule, viewModelModule,useCaseModule,
                jokeRepositoryImplModule,jokeHelperModule,apiHelperModule,retrofitModule)
        }
    }
}