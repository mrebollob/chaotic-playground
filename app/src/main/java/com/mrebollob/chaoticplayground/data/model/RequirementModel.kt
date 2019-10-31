package com.mrebollob.chaoticplayground.data.model

import com.mrebollob.chaoticplayground.domain.entity.Requirement

data class RequirementModel(
    val id: String,
    val title: String,
    val description: String,
    val value: Int
) {
    fun toRequirement(): Requirement {
        return Requirement(
            id = id,
            title = title,
            description = description,
            value = value
        )
    }
}

fun Requirement.toRequirementModel() =
    RequirementModel(
        id = id,
        title = title,
        description = description,
        value = value
    )