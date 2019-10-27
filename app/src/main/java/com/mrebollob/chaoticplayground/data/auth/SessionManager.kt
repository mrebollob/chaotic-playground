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

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.mrebollob.chaoticplayground.domain.entity.User
import com.mrebollob.chaoticplayground.domain.exception.PlayGroundException
import com.mrebollob.chaoticplayground.domain.functional.Either
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class SessionManager @Inject constructor(private val auth: FirebaseAuth) {

    suspend fun getUser(): Either<PlayGroundException, User> {
        val currentUser = auth.currentUser
        return if (currentUser != null) {
            Timber.d("Current user")
            Either.Right(currentUser.toUser())
        } else {
            Timber.d("New user")
            suspendCoroutine {
                signInAnonymousUser { result ->
                    it.resume(result)
                }
            }
        }
    }

    fun signOut() {
        auth.currentUser?.delete()
        auth.signOut()
    }

    private fun signInAnonymousUser(result: (Either<PlayGroundException, User>) -> Unit) {
        auth.signInAnonymously().addOnCompleteListener { task ->
            val currentUser = auth.currentUser
            if (task.isSuccessful && currentUser != null) {
                currentUser.updateProfile(
                    UserProfileChangeRequest.Builder()
                        .setDisplayName(RandomUser.generateRandomName())
                        .build()
                ).addOnSuccessListener {
                    currentUser.reload().addOnSuccessListener {
                        result(Either.Right(currentUser.toUser()))
                    }
                }
            } else {
                result(Either.Left(PlayGroundException("Error")))
            }
        }
    }

    private fun FirebaseUser.toUser(): User {
        return User(
            id = this.uid,
            name = this.displayName ?: ""
        )
    }
}