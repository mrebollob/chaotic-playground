package com.mrebollob.chaoticplayground.data.model

import com.mrebollob.chaoticplayground.domain.entity.Requirement

data class RequirementModel(
    val id: String? = null,
    val title: String? = null,
    val checked: Boolean? = null
) {
    fun toRequirement(): Requirement {
        return Requirement(
            id = id ?: "",
            title = title ?: "",
            checked = checked ?: false
        )
    }
}

fun Requirement.toRequirementModel() =
    RequirementModel(
        id = id,
        title = title,
        checked = checked
    )