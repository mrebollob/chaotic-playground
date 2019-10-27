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
package com.mrebollob.chaoticplayground.data.auth

import java.security.SecureRandom

object RandomUser {
    private const val CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz"
    private const val NUMBER = "0123456789"

    fun generateRandomName(): String {
        val random = SecureRandom()
        val sb = StringBuilder()

        for (i in 1..5) {
            val rndCharAt = random.nextInt(CHAR_LOWER.length)
            val rndChar = CHAR_LOWER[rndCharAt]
            sb.append(rndChar)
        }

        sb.append("-")

        for (i in 1..3) {
            val rndCharAt = random.nextInt(NUMBER.length)
            val rndChar = NUMBER[rndCharAt]
            sb.append(rndChar)
        }

        return sb.toString().toUpperCase()
    }
}