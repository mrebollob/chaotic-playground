/*
 *   Copyright (C) 2019 mrebollob.
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.mrebollob.chaoticplayground.presentation.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.mrebollob.chaoticplayground.R
import com.mrebollob.chaoticplayground.domain.extension.observe
import com.mrebollob.chaoticplayground.domain.extension.toast
import com.mrebollob.chaoticplayground.domain.extension.viewModel
import com.mrebollob.chaoticplayground.presentation.platform.BaseActivity
import com.mrebollob.chaoticplayground.presentation.platform.LoadingState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity() {

    private lateinit var mainViewModel: MainViewModel
    private val comicsAdapter = ComicsAdapter()

    override fun layoutId() = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = viewModel(viewModelFactory) {
            observe(screenState, ::handleScreenState)
        }

        initUI()
    }

    private fun initUI() {
        initToolbar()
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        comicsListView.adapter = comicsAdapter
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun handleScreenState(screenState: MainScreenState?) {
        screenState ?: return
        when (screenState.comicsState) {
            LoadingState.Ready -> renderReadyState(screenState)
            LoadingState.Loading -> renderLoadingState()
            LoadingState.Error -> renderErrorState()
        }
    }

    private fun renderReadyState(screenState: MainScreenState) {
        comicsAdapter.comics = screenState.comics
    }

    private fun renderLoadingState() {
        toast("Loading")
    }

    private fun renderErrorState() {
        toast("Error")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                mainViewModel.onSortClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
