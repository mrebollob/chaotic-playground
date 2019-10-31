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
package com.mrebollob.chaoticplayground.presentation.form

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.mrebollob.chaoticplayground.R
import com.mrebollob.chaoticplayground.domain.entity.Requirement
import com.mrebollob.chaoticplayground.domain.extension.inflate
import kotlin.properties.Delegates

class RequirementsAdapter : RecyclerView.Adapter<RequirementViewHolder>() {

    var requirements: List<Requirement> by Delegates.observable(emptyList())
    { _, _, _ -> notifyDataSetChange() }
    private var checkedRequirements: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequirementViewHolder {
        val view = parent.inflate(R.layout.requirement_list_item)
        return RequirementViewHolder(
            itemView = view,
            addRequirement = addRequirement,
            removeRequirement = removeRequirement
        )
    }

    override fun onBindViewHolder(holder: RequirementViewHolder, position: Int) {
        holder.render(requirements[position], false)
    }

    override fun getItemCount(): Int {
        return requirements.count()
    }

    private fun notifyDataSetChange() {
        notifyDataSetChanged()
    }

    private val addRequirement: (String) -> Unit = { requirementId ->
        checkedRequirements.add(requirementId)
    }

    private val removeRequirement: (String) -> Unit = { requirementId ->
        checkedRequirements.remove(requirementId)
    }

    fun getCheckedRequirements(): List<String> {
        return checkedRequirements
    }
}

class RequirementViewHolder(
    itemView: View,
    val addRequirement: (String) -> Unit,
    val removeRequirement: (String) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val titleCheckBox by lazy { itemView.findViewById(R.id.titleCheckBox) as CheckBox }

    fun render(requirement: Requirement, checked: Boolean) {

        titleCheckBox.text = requirement.title
        titleCheckBox.isChecked = checked
        titleCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                addRequirement(requirement.id)
            } else {
                removeRequirement(requirement.id)
            }
        }
    }
}