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
package com.mrebollob.chaoticplayground.presentation.main.houses

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mrebollob.chaoticplayground.R
import com.mrebollob.chaoticplayground.domain.entity.House
import com.mrebollob.chaoticplayground.domain.extension.inflate
import kotlin.properties.Delegates

class HousesAdapter : RecyclerView.Adapter<HouseViewHolder>() {

    var houses: List<House> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChange() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseViewHolder {
        val view = parent.inflate(R.layout.house_list_item)
        return HouseViewHolder(view)
    }

    override fun onBindViewHolder(holder: HouseViewHolder, position: Int) {
        holder.render(houses[position])
    }

    override fun getItemCount(): Int {
        return houses.count()
    }

    private fun notifyDataSetChange() {
        notifyDataSetChanged()
    }
}

class HouseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val priceTextView by lazy { itemView.findViewById(R.id.priceTextView) as TextView }
    private val thumbnailImageView by lazy { itemView.findViewById(R.id.thumbnailImageView) as ImageView }

    fun render(house: House) {

        priceTextView.text =
            itemView.context.getString(R.string.comic_price_format, house.rentPrice)

//        thumbnailImageView.load(comic.thumbnail) {
//            crossfade(true)
//            placeholder(ColorDrawable(ContextCompat.getColor(itemView.context, R.color.black_20a)))
//        }
    }
}