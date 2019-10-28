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

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.mrebollob.chaoticplayground.data.auth.SessionManager
import com.mrebollob.chaoticplayground.data.model.UserDataModel
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

class FirestoreRepository @Inject constructor(
    private val sessionManager: SessionManager,
    private val db: FirebaseFirestore
) {

    suspend fun addData(house: House): Either<PlayGroundException, Unit> {
        return withContext(Dispatchers.IO) {
            sessionManager.getUser()
                .flatMap { user -> getReference(user.id) }
                .flatMap { ref -> runBlocking { createOrUpdateHouse(ref, house) } }
        }
    }

    suspend fun readData(): Either<PlayGroundException, List<House>> {

        return sessionManager.getUser()
            .flatMap { user -> getReference(user.id) }
            .flatMap { ref -> runBlocking { getHouses(ref) } }
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

    private suspend fun getHouses(
        ref: DocumentReference
    ): Either<PlayGroundException, List<House>> {
        return suspendCoroutine { continuation ->
            ref.get().addOnSuccessListener { document ->
                if (document != null) {
                    val userDataModel = document.toObject(UserDataModel::class.java)
                    val houses = userDataModel?.houses?.map { it.toHouse() } ?: emptyList()
                    continuation.resume(Either.Right(houses))
                } else {
                    continuation.resume(Either.Left(PlayGroundException("Error")))
                }
            }.addOnFailureListener {
                continuation.resume(Either.Left(PlayGroundException("Error")))
            }
        }
    }

    private fun getReference(userId: String): Either<PlayGroundException, DocumentReference> {
        return Either.Right(db.collection(HOUSES_DB_KEY).document(userId))
    }

    private suspend fun createOrUpdateHouse(
        ref: DocumentReference,
        house: House
    ): Either<PlayGroundException, Unit> {
        return suspendCoroutine { continuation ->
            val data = hashMapOf(house.id to house)
            ref.set(data, SetOptions.merge())
                .addOnSuccessListener { continuation.resume(Either.Right(Unit)) }
                .addOnFailureListener {
                    Timber.e(it, "Create error ")
                    continuation.resume(Either.Left(PlayGroundException("Error")))
                }
        }
    }
}