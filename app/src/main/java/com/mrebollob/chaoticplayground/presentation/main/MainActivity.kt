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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mrebollob.chaoticplayground.R
import com.mrebollob.chaoticplayground.domain.extension.navigate
import com.mrebollob.chaoticplayground.presentation.main.houses.HousesFragment
import com.mrebollob.chaoticplayground.presentation.main.profile.ProfileFragment
import com.mrebollob.chaoticplayground.presentation.platform.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class MainActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun layoutId() = R.layout.activity_main
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initToolbar()
        initNavigation()
        navigate(HousesFragment.newInstance())
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initNavigation() {
        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navigate(HousesFragment.newInstance())
                    true
                }
                R.id.navigation_profile -> {
                    navigate(ProfileFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

    companion object Navigator {

        fun open(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
