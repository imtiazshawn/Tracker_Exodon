import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel

class ExpenseViewModel : ViewModel() {
    val title = mutableStateOf("")
    val amount = mutableStateOf("")
    val date = mutableStateOf("")
    val category = mutableStateOf("")
    val type = mutableStateOf("")
}
