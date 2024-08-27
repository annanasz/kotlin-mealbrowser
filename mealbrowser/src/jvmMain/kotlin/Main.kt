import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import presentation.screens.MealApp
import presentation.viewmodels.MealViewModel

fun main() = application {
    Window(
        title = "My Compose Desktop app",
        onCloseRequest = ::exitApplication,
        state = WindowState(width = 1400.dp, height = 800.dp)
    ) {
        val viewModel = MealViewModel()

        MealApp(viewModel)
    }
}
