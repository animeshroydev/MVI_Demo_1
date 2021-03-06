package com.animesh.roy.mvidemo1.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.animesh.roy.mvidemo1.R
import com.animesh.roy.mvidemo1.ui.DataStateListener
import com.animesh.roy.mvidemo1.util.DataState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DataStateListener
 {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java);

        showMainFragment()

    }

    fun showMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                MainFragment(), "MainFragment")
            .commit()
    }

     override fun onDataStateChange(dataState: DataState<*>?) {
         handleDataStateChanged(dataState)
     }

     private fun handleDataStateChanged(dataState: DataState<*>?) {
         dataState?.let {

             // handle loading
             showProgressBar(it.loading)

             // handle message
             it.message?.let {event ->

                 event.getContentIfNotHandled()?.let { message ->
                     showToast(message)
                 }

             }
         }
     }

     fun showToast(message: String) {
         Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
     }

     fun showProgressBar(isVisible: Boolean) {
         if (isVisible) {
             progress_bar.visibility = View.VISIBLE
         } else {
             progress_bar.visibility = View.GONE
         }
     }

 }























