package com.morse.domain.usecases

import com.morse.domain.repository.MarvelHerosRepositoryInterface

public class GetSuperHerosInformations (private var superHeroRepository : MarvelHerosRepositoryInterface) {

    public fun loadIt () = superHeroRepository?.laodSuperHeros()

}