package data.model

import kotlinx.serialization.Serializable

@Serializable
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strDrinkAlternate: String?,
    val strCategory: String?,
    val strArea: String?,
    val strInstructions: String?,
    val strMealThumb: String?,
    val strTags: String?,
    val strYoutube: String?,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    val strIngredient16: String?,
    val strIngredient17: String?,
    val strIngredient18: String?,
    val strIngredient19: String?,
    val strIngredient20: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,
    val strMeasure16: String?,
    val strMeasure17: String?,
    val strMeasure18: String?,
    val strMeasure19: String?,
    val strMeasure20: String?,
    val strSource: String?,
    val strImageSource: String?,
    val strCreativeCommonsConfirmed: String?,
    val dateModified: String?,
) {
    fun getMeasure(index: Int): String? {
        val measureField = "strMeasure$index"
        val measureValue = this::class.java.getDeclaredField(measureField).get(this) as? String
        return measureValue?.takeIf { it.isNotBlank() }
    }

    fun getIngredient(index: Int): String? {
        val ingredientField = "strIngredient$index"
        val ingredientValue = this::class.java.getDeclaredField(ingredientField).get(this) as? String
        return ingredientValue?.takeIf { it.isNotBlank() }
    }
}

@Serializable
data class MealsResponse(val meals: List<Meal>?)

@Serializable
data class MealPartial(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String?,
)

@Serializable
data class MealByCategory(val meals: List<MealPartial>?)
