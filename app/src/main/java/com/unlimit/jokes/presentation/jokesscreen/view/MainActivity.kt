package com.unlimit.jokes.presentation.jokesscreen.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.unlimit.jokes.R
import com.unlimit.jokes.data.JokeRepositoryImpl
import com.unlimit.jokes.data.local.AppDatabase
import com.unlimit.jokes.data.local.JokeHelperImpl
import com.unlimit.jokes.data.remote.ApiHelperImpl
import com.unlimit.jokes.data.remote.RetrofitBuilder
import com.unlimit.jokes.databinding.ActivityMainBinding
import com.unlimit.jokes.domain.GetJokeFromApiAndStoreInDbUseCase
import com.unlimit.jokes.domain.GetTopTenJokesUseCase
import com.unlimit.jokes.presentation.jokesscreen.adapter.JokeItem
import com.unlimit.jokes.presentation.jokesscreen.adapter.JokeListAdapter
import com.unlimit.jokes.presentation.jokesscreen.viewmodel.MainActivityVM
import com.unlimit.jokes.presentation.jokesscreen.viewmodel.ViewModelProviderFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityVM:MainActivityVM
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val database = AppDatabase.getInstance(this)

         mainActivityVM = MainActivityVM(
            getJokeFromApiAndStoreInDbUseCase = GetJokeFromApiAndStoreInDbUseCase(
                jokeRepositoryImpl = JokeRepositoryImpl(
                    apiService = ApiHelperImpl(apiService = RetrofitBuilder.apiService),
                    jokeHelper = JokeHelperImpl(appDatabase = database)
                )
            ),
            getTopTenJokesUseCase = GetTopTenJokesUseCase(
                jokeRepositoryImpl = JokeRepositoryImpl(
                    apiService = ApiHelperImpl(apiService = RetrofitBuilder.apiService),
                    jokeHelper = JokeHelperImpl(appDatabase = database)
                )
            ),
             repository =JokeRepositoryImpl(
                 apiService = ApiHelperImpl(apiService = RetrofitBuilder.apiService),
                 jokeHelper = JokeHelperImpl(appDatabase = database)
             ),
             jokeHelperImpl = JokeHelperImpl(appDatabase = database)
         )

        val viewModelProviderFactory = ViewModelProviderFactory(mainActivityVM)
        val viewModel =
            ViewModelProvider(this, viewModelProviderFactory)[MainActivityVM::class.java]

        setupRecyclerView()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collectLatest {
                    when(it){
                        is MainActivityVM.State.Error -> {

                        }
                        MainActivityVM.State.Loading -> {

                        }
                        is MainActivityVM.State.ShowJokes -> {
                            Log.d("List of jokes",it.listOfJokes.toString())
                            (binding.rvJokesList.adapter as JokeListAdapter).submitList(it.listOfJokes.map {
                                JokeItem(joke = it.joke, time = "12345")
                            })
                        }
                    }
                }
            }
        }


    }

    private fun setupRecyclerView(){
        val jokeListAdapter = JokeListAdapter(this@MainActivity)
        with(binding.rvJokesList) {
            adapter = jokeListAdapter
        }

        val borderColor = ContextCompat.getColor(this, R.color.black)

        val borderWidth =
            resources.getDimensionPixelSize(R.dimen._1dp)
        val bottomBorderDecoration = BottomBorderDecoration(borderColor, borderWidth)
        binding.rvJokesList.addItemDecoration(bottomBorderDecoration)
    }

    override fun onPause() {
        super.onPause()
    }
}