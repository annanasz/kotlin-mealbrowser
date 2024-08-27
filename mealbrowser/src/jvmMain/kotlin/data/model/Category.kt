package data.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(val strCategory: String?)

@Serializable
data class CategoryResponse(val meals: List<Category>?)