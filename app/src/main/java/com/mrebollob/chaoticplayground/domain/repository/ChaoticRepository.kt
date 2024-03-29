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

package com.mrebollob.chaoticplayground.domain.repository

import com.mrebollob.chaoticplayground.domain.entity.Requirement
import com.mrebollob.chaoticplayground.domain.exception.PlayGroundException
import com.mrebollob.chaoticplayground.domain.functional.Either

interface ChaoticRepository {
    suspend fun getRequirements(): Either<PlayGroundException, List<Requirement>>
}