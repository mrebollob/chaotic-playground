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

package com.mrebollob.chaoticplayground.domain.interactor

import javax.inject.Inject
import javax.inject.Singleton

// From: http://codekata.com/kata/kata09-back-to-the-checkout/

@Singleton
class Checkout @Inject constructor() {

    /**
     * Scan an individual item.
     */
    fun scan(item: Char) {}

    /**
     * Calculate the total price of all the items scanned.
     */
    fun total(): Int {

        return 0
    }
}