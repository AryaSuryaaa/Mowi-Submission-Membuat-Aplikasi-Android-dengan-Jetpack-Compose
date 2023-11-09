package com.aryasurya.mowy.remote.response

data class VideoYoutube(
	val id: Int,
	val results: List<VideoResultsItem>
)

data class VideoResultsItem(
	val site: String,
	val size: Int,
	val iso31661: String,
	val name: String,
	val official: Boolean,
	val id: String,
	val type: String,
	val publishedAt: String,
	val iso6391: String,
	val key: String
)

