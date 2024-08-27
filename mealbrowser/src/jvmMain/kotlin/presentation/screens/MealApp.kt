package presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.dp
import data.model.Category
import data.model.Meal
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import presentation.viewmodels.MealViewModel
import java.net.URL
import data.model.MealPartial

@Composable
fun MealApp(viewModel: MealViewModel) {
    MaterialTheme {
        val searchQuery by remember { viewModel.searchQuery }
        var meals by remember { viewModel.meals }
        val catMeals by remember { viewModel.catMeals }
        val categories by remember { viewModel.categories }
        val selectedMeal by viewModel.selectedMeal.collectAsState()
        val selectedCategory by viewModel.selectedCategory.collectAsState()

        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {
            if (selectedMeal != null) {
                MealDetailsScreen(
                    meal = selectedMeal!!,
                    onBackClicked = { viewModel.clearSelectedMeal() }
                )
            } else {
                Row {
                    TextField(
                        value = searchQuery,
                        onValueChange = viewModel::onSearchQueryChange,
                        label = { Text("Search for meals") },
                        modifier = Modifier
                            .weight(4f)
                            .padding(end = 8.dp)
                    )
                    CategoryDropdown(categories, viewModel::categorySelected, viewModel)
                }


                Spacer(Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { meals = emptyList(); viewModel.searchMeals() },
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Text("Search Meals")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { viewModel.randomMeal() },
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Text("Show me a random meal")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }


                Spacer(Modifier.height(16.dp))

                if (selectedCategory == null)
                    MealGrid(meals = meals, onItemClick = viewModel::mealClicked)
                else
                    PartialMealGrid(meals = catMeals, onItemClick = viewModel::partialMealClicked)
            }
        }
    }
}


@Composable
fun MealGrid(meals: List<Meal>, onItemClick: (Meal) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(meals) { meal ->
            MealItem(meal = meal, onItemClick = onItemClick)
        }
    }
}

@Composable
fun MealItem(meal: Meal, onItemClick: (Meal) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray),
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = { onItemClick(meal) })
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                load = { loadImageBitmap(meal.strMealThumb!!) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "Sample",
                modifier = Modifier.width(200.dp)
            )

            Text(text = meal.strMeal)
        }
    }
}

@Composable
fun PartialMealGrid(meals: List<MealPartial>, onItemClick: (MealPartial) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(200.dp),
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(meals) { meal ->
            PartialMealItem(meal = meal, onItemClick = onItemClick)
        }
    }
}

@Composable
fun PartialMealItem(meal: MealPartial, onItemClick: (MealPartial) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray),
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = { onItemClick(meal) })
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                load = { loadImageBitmap(meal.strMealThumb!!) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "Sample",
                modifier = Modifier.width(200.dp)
            )

            Text(text = meal.strMeal)
        }
    }
}

@Composable
fun <T> AsyncImage(
    load: suspend () -> T,
    painterFor: @Composable (T) -> Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val image: T? by produceState<T?>(null) {
        value = withContext(Dispatchers.IO) {
            try {
                load()
            } catch (e: IOException) {

                e.printStackTrace()
                null
            }
        }
    }

    if (image != null) {
        Image(
            painter = painterFor(image!!),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}

fun loadImageBitmap(url: String): ImageBitmap =
    URL(url).openStream().buffered().use(::loadImageBitmap)

@Composable
fun CategoryDropdown(categories: List<Category>, onCategorySelected: (Category) -> Unit, viewModel: MealViewModel) {
    var expandedState by remember { mutableStateOf(false) }
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    var catMeals by remember { viewModel.catMeals }

    Column {

        Box(
            modifier = Modifier
                .width(150.dp)
                .height(40.dp)
                .clickable(onClick = { expandedState = !expandedState })
                .background(Color.LightGray)
        ) {
            Text(
                text = selectedCategory?.strCategory ?: "Browse categories",
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }

        DropdownMenu(
            expanded = expandedState,
            onDismissRequest = { expandedState = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    onClick = {
                        catMeals = emptyList()
                        expandedState = false
                        onCategorySelected(category)
                    }
                ) {
                    Text(text = category.strCategory ?: "")
                }
            }
        }
    }
}