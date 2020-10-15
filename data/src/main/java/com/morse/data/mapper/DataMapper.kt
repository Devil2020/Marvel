package com.morse.data.mapper

import com.morse.domain.data.SuperHeroMarvelItem
import com.morse.domain.data.SuperHeroMarvelItemDetail
import com.morse.lib.entity.MarvelHerosItem

class DataMapper : BasicMapper<MarvelHerosItem , SuperHeroMarvelItem> {
    override fun performMap(input: MarvelHerosItem): SuperHeroMarvelItem {
        return  SuperHeroMarvelItem(input?.color, input?.details?.map {
            SuperHeroMarvelItemDetail(
                it?.id,
                it?.name,
                it?.plot,
                it?.poster,
                it?.rootId
            )
        }, input?.id, input?.name, input?.poster, input?.quote)
    }
}