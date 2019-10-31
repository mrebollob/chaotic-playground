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
package com.mrebollob.chaoticplayground.presentation.form

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.ViewCompat
import com.mrebollob.chaoticplayground.R
import com.mrebollob.chaoticplayground.di.Injectable
import com.mrebollob.chaoticplayground.domain.entity.House
import com.mrebollob.chaoticplayground.domain.extension.observe
import com.mrebollob.chaoticplayground.domain.extension.toast
import com.mrebollob.chaoticplayground.domain.extension.viewModel
import com.mrebollob.chaoticplayground.presentation.platform.BaseActivity
import com.mrebollob.chaoticplayground.presentation.platform.LoadingState
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.toolbar.*

class FormActivity : BaseActivity(), Injectable {

    private lateinit var formViewModel: FormViewModel
    private val adapter = RequirementsAdapter()
    override fun layoutId() = R.layout.activity_form
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        formViewModel = viewModel(viewModelFactory) {
            observe(screenState, ::handleScreenState)
        }
    }

    private fun initUI() {
        initToolbar()

        requirementsListView.adapter = adapter
        ViewCompat.setNestedScrollingEnabled(requirementsListView, false)

        addButton.setOnClickListener {
            val newHouse = House(
                title = titleEditText.text.toString(),
                imageUrl = "",
                rentPrice = priceEditText.text.toString().toFloat(),
                requirements = emptyList()
            )

            formViewModel.addHouse(newHouse)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun handleScreenState(screenState: FormScreenState?) {
        screenState ?: return
        when (screenState.loadingState) {
            LoadingState.Ready -> renderReadyState(screenState)
            LoadingState.Loading -> renderLoadingState()
            LoadingState.Error -> renderErrorState()
        }
    }

    private fun renderReadyState(screenState: FormScreenState) {
        if (screenState.created) {
            val returnIntent = Intent()
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        } else {
            adapter.requirements = screenState.requirements
        }
    }

    private fun renderLoadingState() {
        toast("Loading")
    }

    private fun renderErrorState() {
        toast("Error", Toast.LENGTH_LONG)
    }

    companion object Navigator {
        fun open(context: Context) {
            val intent = Intent(context, FormActivity::class.java)
            context.startActivity(intent)
        }
    }
}
