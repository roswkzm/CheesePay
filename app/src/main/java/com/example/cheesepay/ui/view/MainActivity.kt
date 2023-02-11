package com.example.cheesepay.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cheesepay.R
import com.example.cheesepay.databinding.ActivityMainBinding
import com.example.cheesepay.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController : NavController
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpNavigation()
        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.showErrorMsg.observe(this, Observer { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        })

        viewModel.isShowProgressBar.observe(this, Observer { it ->
            var isShow = it.getContentIfNotHandled()
            if (isShow!!){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    private fun setUpNavigation() {
        val host = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment ?: return
        navController = host.navController
        binding.bottomNavView.setupWithNavController(navController)
    }
}