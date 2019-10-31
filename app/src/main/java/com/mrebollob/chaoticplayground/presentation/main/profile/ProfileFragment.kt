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
package com.mrebollob.chaoticplayground.presentation.main.profile

import android.os.Bundle
import android.view.View
import com.mrebollob.chaoticplayground.R
import com.mrebollob.chaoticplayground.domain.extension.observe
import com.mrebollob.chaoticplayground.domain.extension.viewModel
import com.mrebollob.chaoticplayground.presentation.platform.BaseFragment
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : BaseFragment() {

    private lateinit var profileViewModel: ProfileViewModel
    override fun layoutId(): Int = R.layout.profile_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        profileViewModel = viewModel(viewModelFactory) {
            observe(screenState, ::handleScreenState)
        }
    }

    private fun initUI() {
        activity?.setTitle(R.string.app_name)
        signOutButton.setOnClickListener {
            profileViewModel.onSignOutClick()
        }
    }

    private fun handleScreenState(screenState: ProfileScreenState?) {
        screenState ?: return
        when (screenState) {
            is ProfileScreenState.Error -> {
            }
            is ProfileScreenState.Loading -> {
            }
            is ProfileScreenState.Ready -> {
                idTextView.text = screenState.user.id
                nameTextView.text = screenState.user.name
            }
        }
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }
}
