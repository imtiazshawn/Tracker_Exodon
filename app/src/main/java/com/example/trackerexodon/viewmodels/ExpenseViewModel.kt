import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.*

class ExpenseViewModel : ViewModel() {
    val title = mutableStateOf("")
    val amount = mutableStateOf("")
    val date = mutableStateOf(Date().time.toString())
    val category = mutableStateOf("")
    val type = mutableStateOf("")

    val deleteDialogOpen = mutableStateOf(false)
    val editDialogOpen = mutableStateOf(false)
}
