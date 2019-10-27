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

package com.mrebollob.chaoticplayground

import com.mrebollob.chaoticplayground.domain.interactor.Checkout
import org.junit.Assert.assertEquals
import org.junit.Test

class CheckoutTest {

    private fun price(items: CharSequence): Int {
        val checkout = Checkout()
        items.forEach { checkout.scan(it) }
        return checkout.total()
    }

    @Test
    fun total_isCorrect_for_empty_basket() = assertEquals(0, price(""))

    @Test
    fun total_isCorrect_for_A() = assertEquals(50, price("A"))

    @Test
    fun total_isCorrect_for_AB() = assertEquals(80, price("AB"))

    @Test
    fun total_isCorrect_for_CDBA() = assertEquals(115, price("CDBA"))

    @Test
    fun total_isCorrect_for_AA() = assertEquals(100, price("AA"))

    @Test
    fun total_isCorrect_for_AAA() = assertEquals(130, price("AAA"))

    @Test
    fun total_isCorrect_for_AAAA() = assertEquals(180, price("AAAA"))

    @Test
    fun total_isCorrect_for_AAAAA() = assertEquals(230, price("AAAAA"))

    @Test
    fun total_isCorrect_for_AAAAAA() = assertEquals(260, price("AAAAAA"))

    @Test
    fun total_isCorrect_for_AAAB() = assertEquals(160, price("AAAB"))

    @Test
    fun total_isCorrect_for_AAABB() = assertEquals(175, price("AAABB"))

    @Test
    fun total_isCorrect_for_AAABBD() = assertEquals(190, price("AAABBD"))

    @Test
    fun total_isCorrect_for_DABABA() = assertEquals(190, price("DABABA"))
}