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
package com.mrebollob.chaoticplayground.presentation.main.houses

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mrebollob.chaoticplayground.R
import com.mrebollob.chaoticplayground.domain.extension.observe
import com.mrebollob.chaoticplayground.domain.extension.toast
import com.mrebollob.chaoticplayground.domain.extension.viewModel
import com.mrebollob.chaoticplayground.presentation.platform.BaseFragment
import com.mrebollob.chaoticplayground.presentation.platform.LoadingState
import kotlinx.android.synthetic.main.houses_fragment.*

class HousesFragment : BaseFragment() {

    private lateinit var flatsViewModel: HousesViewModel
    private val adapter = HousesAdapter()
    override fun layoutId(): Int = R.layout.houses_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        flatsViewModel = viewModel(viewModelFactory) {
            observe(screenState, ::handleScreenState)
        }
    }

    private fun initUI() {
        activity?.setTitle(R.string.app_name)
        housesListView.adapter = adapter
    }

    private fun handleScreenState(screenState: HousesScreenState?) {
        screenState ?: return
        when (screenState.housesState) {
            LoadingState.Ready -> renderReadyState(screenState)
            LoadingState.Loading -> renderLoadingState()
            LoadingState.Error -> renderErrorState()
        }
    }

    private fun renderReadyState(screenState: HousesScreenState) {
        adapter.houses = screenState.houses
    }

    private fun renderLoadingState() {
        context?.toast("Loading")
    }

    private fun renderErrorState() {
        context?.toast("Error", Toast.LENGTH_LONG)
    }

    companion object {
        fun newInstance() = HousesFragment()
    }
}
