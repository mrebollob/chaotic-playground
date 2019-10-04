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

package com.mrebollob.chaoticplayground.presentation.main

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.mrebollob.chaoticplayground.R
import com.mrebollob.chaoticplayground.domain.entity.MarvelComic
import com.mrebollob.chaoticplayground.domain.extension.inflate
import kotlin.properties.Delegates

class ComicsAdapter : RecyclerView.Adapter<ComicViewHolder>() {

    var comics: List<MarvelComic> by Delegates.observable(emptyList()) { _, _, _ -> notifyDataSetChange() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val view = parent.inflate(R.layout.comic_list_item)
        return ComicViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.render(comics[position])
    }

    override fun getItemCount(): Int {
        return comics.count()
    }

    private fun notifyDataSetChange() {
        notifyDataSetChanged()
    }
}

class ComicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val priceTextView by lazy { itemView.findViewById(R.id.priceTextView) as TextView }
    private val thumbnailImageView by lazy { itemView.findViewById(R.id.thumbnailImageView) as ImageView }

    fun render(comic: MarvelComic) {

        if (comic.printPrice > 0) {
            priceTextView.text =
                itemView.context.getString(R.string.comic_price_format, comic.printPrice)
        }
        thumbnailImageView.load(comic.thumbnail) {
            crossfade(true)
            placeholder(ColorDrawable(ContextCompat.getColor(itemView.context, R.color.black_20a)))
        }
    }
}