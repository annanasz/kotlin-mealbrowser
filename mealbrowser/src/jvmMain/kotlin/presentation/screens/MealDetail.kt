package presentation.screens
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import data.model.Meal
import java.awt.Desktop
import java.net.URI

@Composable
fun MealDetailsScreen(
    meal: Meal,
    onBackClicked: () -> Unit
) {
    Box(Modifier.verticalScroll(rememberScrollState())) {
    Column(Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(200.dp)
                    .aspectRatio(1f)
                    .border(width = 1.dp, color = Color.Black)
            ) {
                AsyncImage(
                    load = { loadImageBitmap(meal.strMealThumb!!) },
                    painterFor = { remember { BitmapPainter(it) } },
                    contentDescription = "Sample",
                    modifier = Modifier.fillMaxSize()
                )
            }

           Column {
               Text(
                   text = meal.strMeal,
                   style = MaterialTheme.typography.h2,
                   modifier = Modifier.padding(start = 32.dp)
               )
               meal.strCategory?.let {
                   Text(
                       text = "Category: ${meal.strCategory}",
                       style = MaterialTheme.typography.h5,
                       modifier = Modifier.padding(start = 32.dp)
                   )
               }

               Text(
                   text = "Area: ${meal.strArea}",
                   style = MaterialTheme.typography.h5,
                   modifier = Modifier.padding(start = 32.dp)
               )

               meal.strYoutube.let {
                   Text(
                       text = "Youtube video",
                       style = TextStyle(
                           textDecoration = TextDecoration.Underline,
                           fontWeight = FontWeight.Bold,
                           color = Color.Blue
                       ),
                       modifier = Modifier.padding(top = 16.dp, start = 32.dp).clickable {
                           val uri = it?.let { it1 -> URI(it1) }
                           if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                               Desktop.getDesktop().browse(uri)
                           }
                       }

                   )
               }
           }

        }
        val ingredientsWithMeasures = buildList {
            for (i in 1..20) {
                val ingredient = meal.getIngredient(i)
                val measure = meal.getMeasure(i)
                if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
                    add("$measure $ingredient")
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(
                text = "Instructions: ",
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 16.dp, start = 20.dp)
            )
            Text(
                text = "${meal.strInstructions}",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 16.dp,start = 20.dp)
            )
        }

        Row {
            Text(
                text = "Ingredients:",
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 16.dp, start = 20.dp)
            )
            Text(
                text = ingredientsWithMeasures.joinToString("\n"),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 16.dp, start = 20.dp)
            )

        }

        Button(
            onClick = onBackClicked,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Back")
    }
    }
}}





