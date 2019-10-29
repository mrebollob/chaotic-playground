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
package com.mrebollob.chaoticplayground.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.mrebollob.chaoticplayground.data.auth.SessionManager
import com.mrebollob.chaoticplayground.data.model.HouseModel
import com.mrebollob.chaoticplayground.data.model.toHouseModel
import com.mrebollob.chaoticplayground.domain.entity.House
import com.mrebollob.chaoticplayground.domain.exception.PlayGroundException
import com.mrebollob.chaoticplayground.domain.functional.Either
import com.mrebollob.chaoticplayground.domain.functional.flatMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


private const val HOUSES_DB_KEY = "houses"
internal const val USER_ID_FIELD = "userId"

class FirestoreRepository @Inject constructor(
    private val sessionManager: SessionManager,
    private val db: FirebaseFirestore
) {

    suspend fun addData(house: House): Either<PlayGroundException, Unit> {
        return withContext(Dispatchers.IO) {
            sessionManager.getUser()
                .flatMap { user -> runBlocking { createOrUpdateHouse(user.id, house) } }
        }
    }

    suspend fun getHouses(): Either<PlayGroundException, List<House>> {
        return withContext(Dispatchers.IO) {
            sessionManager.getUser()
                .flatMap { user -> runBlocking { getHouses(user.id) } }
        }
    }

    suspend fun clearData() {
        sessionManager.getUser().either({
            Timber.w("Error getting user")
        }, {
            db.collection("users").document(it.id).delete()
                .addOnSuccessListener { Timber.d("Deleted") }
                .addOnFailureListener { e -> Timber.w(e, "Error deleting document") }
        })
    }

    private suspend fun getHouses(userId: String): Either<PlayGroundException, List<House>> {
        return suspendCoroutine { continuation ->
            db.collection(HOUSES_DB_KEY).whereEqualTo(USER_ID_FIELD, userId).get()
                .addOnSuccessListener { documents ->
                    val houses = mutableListOf<House>()
                    for (document in documents) {
                        houses.add(document.toObject(HouseModel::class.java).toHouse())
                    }
                    continuation.resume(Either.Right(houses))

                }.addOnFailureListener {
                    Timber.e(it, "Read error ")
                    continuation.resume(Either.Left(PlayGroundException("Error")))
                }
        }
    }

    private suspend fun createOrUpdateHouse(
        userId: String,
        house: House
    ): Either<PlayGroundException, Unit> {
        return suspendCoroutine { continuation ->
            db.collection(HOUSES_DB_KEY).document(house.id)
                .set(house.toHouseModel(userId), SetOptions.merge())
                .addOnSuccessListener { continuation.resume(Either.Right(Unit)) }
                .addOnFailureListener {
                    Timber.e(it, "Create error ")
                    continuation.resume(Either.Left(PlayGroundException("Error")))
                }
        }
    }
}