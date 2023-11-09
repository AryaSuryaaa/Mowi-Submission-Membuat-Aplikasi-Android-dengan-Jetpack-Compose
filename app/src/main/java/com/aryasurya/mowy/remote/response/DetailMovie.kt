package com.aryasurya.mowy.remote.response

data class DetailMovie(
	val originalLanguage: String,
	val imdbId: String,
	val video: Boolean,
	val title: String,
	val backdropPath: String,
	val revenue: Int,
	val genres: List<GenresItem>,
	val popularity: Any,
	val productionCountries: List<ProductionCountriesItem>,
	val id: Int,
	val voteCount: Int,
	val budget: Int,
	val overview: String,
	val originalTitle: String,
	val runtime: Int,
	val posterPath: String,
	val spokenLanguages: List<SpokenLanguagesItem>,
	val productionCompanies: List<ProductionCompaniesItem>,
	val releaseDate: String,
	val voteAverage: Any,
	val belongsToCollection: Any,
	val tagline: String,
	val adult: Boolean,
	val homepage: String,
	val status: String
)

data class ProductionCompaniesItem(
	val logoPath: String,
	val name: String,
	val id: Int,
	val originCountry: String
)

data class ProductionCountriesItem(
	val iso31661: String,
	val name: String
)

data class SpokenLanguagesItem(
	val name: String,
	val iso6391: String,
	val englishName: String
)

data class GenresItem(
	val name: String,
	val id: Int
)

