package com.unlimit.jokes.koin

import com.unlimit.jokes.data.JokeRepositoryImpl
import com.unlimit.jokes.data.local.AppDatabase
import com.unlimit.jokes.data.local.JokeHelper
import com.unlimit.jokes.data.local.JokeHelperImpl
import com.unlimit.jokes.data.remote.ApiHelper
import com.unlimit.jokes.data.remote.ApiHelperImpl
import com.unlimit.jokes.data.remote.RetrofitBuilder
import com.unlimit.jokes.domain.GetJokeFromApiAndStoreInDbUseCase
import com.unlimit.jokes.domain.GetTopTenJokesUseCase
import com.unlimit.jokes.presentation.jokesscreen.viewmodel.MainActivityVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase.getInstance(get()) }
}

val viewModelModule = module {
    viewModel { MainActivityVM(get(), get()) }
}

val useCaseModule = module {
    factory { GetJokeFromApiAndStoreInDbUseCase(get()) }
    factory { GetTopTenJokesUseCase(get()) }
}

val jokeRepositoryImplModule = module {
    factory { JokeRepositoryImpl(get(), get()) }

}


val jokeHelperModule = module {
    factory<JokeHelper> { JokeHelperImpl(get()) }
}
val apiHelperModule = module {
    factory<ApiHelper> { ApiHelperImpl(get()) }

}
val retrofitModule = module {
    single {
        RetrofitBuilder.apiService
    }
}