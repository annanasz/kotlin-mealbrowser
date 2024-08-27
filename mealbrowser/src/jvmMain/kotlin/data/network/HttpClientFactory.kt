package data.network

import data.model.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun createClient(): HttpClient {
        return HttpClient(CIO) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json { ignoreUnknownKeys = true })
            }
        }
    }
}

suspend fun HttpClient.searchMeals(query: String): List<Meal> {
    val url = "https://www.themealdb.com/api/json/v1/1/search.php?s=$query"
    val response = get<String>(url)
    val mealsResponse: MealsResponse = Json.decodeFromString(MealsResponse.serializer(), response)
    return mealsResponse.meals?.toMutableList() ?: listOf()
}

suspend fun HttpClient.randomMeal(): List<Meal> {
    val url = "https://www.themealdb.com/api/json/v1/1/random.php"
    val response = get<String>(url)
    val mealsResponse: MealsResponse = Json.decodeFromString(MealsResponse.serializer(), response)
    return mealsResponse.meals?.toMutableList() ?: listOf()
}

suspend fun HttpClient.getCategories(): List<Category> {
    val url = "https://www.themealdb.com/api/json/v1/1/list.php?c=list"
    val response = get<String>(url)
    val categoriesResponse: CategoryResponse =
        Json.decodeFromString(CategoryResponse.serializer(), response)
    return categoriesResponse.meals?.toMutableList() ?: listOf()
}

suspend fun HttpClient.getMealByCategory(category: Category): List<MealPartial> {
    val url = "https://www.themealdb.com/api/json/v1/1/filter.php?c=${category.strCategory}"
    val response = get<String>(url)
    val partialMealResponse: MealByCategory =
        Json.decodeFromString(MealByCategory.serializer(), response)
    return partialMealResponse.meals?.toMutableList() ?: listOf()
}

suspend fun HttpClient.getMealById(id: String): List<Meal> {
    val url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$id"
    val response = get<String>(url)
    val mealsResponse: MealsResponse = Json.decodeFromString(MealsResponse.serializer(), response)
    return mealsResponse.meals?.toMutableList() ?: listOf()
}