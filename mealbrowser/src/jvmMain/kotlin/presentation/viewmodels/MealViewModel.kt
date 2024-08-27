package presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import data.model.Category
import kotlinx.coroutines.flow.*
import data.model.Meal
import data.model.MealPartial
import data.network.*
import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealViewModel {
    private var client: HttpClient = data.network.HttpClientFactory.createClient()
    val searchQuery: MutableState<String> = mutableStateOf("")
    val meals: MutableState<List<Meal>> = mutableStateOf(emptyList())
    val categories: MutableState<List<Category>> = mutableStateOf(emptyList())
    private val _selectedMeal = MutableStateFlow<Meal?>(null)
    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()
    val selectedMeal: StateFlow<Meal?> = _selectedMeal.asStateFlow()
    val catMeals: MutableState<List<MealPartial>> = mutableStateOf(emptyList())

    init {
        CoroutineScope(Dispatchers.Default).launch {
            val response = client.getCategories()
            categories.value = response
        }
    }

    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
    }

    fun searchMeals() {
        _selectedCategory.value = null
        CoroutineScope(Dispatchers.Default).launch {
            val query = searchQuery.value
            val response = client.searchMeals(query)
            meals.value = emptyList()
            meals.value = response
            clearSelectedMeal()
        }
    }

    fun randomMeal() {
        _selectedCategory.value = null
        CoroutineScope(Dispatchers.Default).launch {
            val response = client.randomMeal()
            meals.value = emptyList()
            meals.value = response
            _selectedMeal.value = meals.value[0]
        }
    }

    fun mealClicked(meal: Meal) {
        _selectedMeal.value = meal
    }

    fun clearSelectedMeal() {
        _selectedMeal.value = null
    }

    fun categorySelected(category: Category) {
        _selectedCategory.value = category
        CoroutineScope(Dispatchers.Default).launch {
            val response = client.getMealByCategory(category)
            catMeals.value = emptyList()
            catMeals.value = response
        }
    }

    fun partialMealClicked(meal: MealPartial) {
        CoroutineScope(Dispatchers.Default).launch {
            val response = client.getMealById(meal.idMeal)
            meals.value = emptyList()
            meals.value = response
            _selectedMeal.value = meals.value[0]
        }
    }

}