package com.unlimit.jokes.presentation.jokesscreen.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.unlimit.jokes.R
import com.unlimit.jokes.databinding.ActivityMainBinding
import com.unlimit.jokes.presentation.jokesscreen.adapter.JokeListAdapter
import com.unlimit.jokes.presentation.jokesscreen.utils.toGone
import com.unlimit.jokes.presentation.jokesscreen.utils.toVisible
import com.unlimit.jokes.presentation.jokesscreen.viewmodel.MainActivityVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainActivityVM: MainActivityVM by viewModel()


    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val notConnected = intent.getBooleanExtra(
                ConnectivityManager
                    .EXTRA_NO_CONNECTIVITY, false
            )
            if (notConnected) {
                disconnected()
            } else {
                connected()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupRecyclerView()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mainActivityVM.uiState.collectLatest {
                    when (it) {
                        is MainActivityVM.State.Error -> {
                            binding.progressBar.toGone()
                        }

                        MainActivityVM.State.Loading -> {
                            binding.progressBar.toVisible()
                        }

                        is MainActivityVM.State.ShowJokes -> {
                            binding.progressBar.toGone()
                            binding.rvJokesList.toVisible()
                            (binding.rvJokesList.adapter as JokeListAdapter).submitList(it.listOfJokes)
                            lifecycleScope.launch {
                                delay(1000)
                                binding.rvJokesList.scrollToPosition(0)
                            }

                        }
                    }
                }
            }
        }


    }


    private fun disconnected() {
        binding.clErrorLayout.toVisible()
    }

    private fun connected() {
        binding.clErrorLayout.toGone()
        mainActivityVM.startPeriodicDataFetching()

    }


    override fun onStart() {
        super.onStart()
        mainActivityVM.isActivityInForeground = true
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        mainActivityVM.isActivityInForeground = false
        unregisterReceiver(broadcastReceiver)
    }


    private fun setupRecyclerView() {
        val jokeListAdapter = JokeListAdapter(this@MainActivity)
        with(binding.rvJokesList) {
            adapter = jokeListAdapter
        }

        /*val borderColor = ContextCompat.getColor(this, R.color.black)

        val borderWidth =
            resources.getDimensionPixelSize(R.dimen._1dp)
        val bottomBorderDecoration = BottomBorderDecoration(borderColor, borderWidth)
        binding.rvJokesList.addItemDecoration(bottomBorderDecoration)*/
    }


}