import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExpenseInsect(
    rentPercentage: Float,
    foodPercentage: Float,
    clothesPercentage: Float,
    travelPercentage: Float,
    accessoriesPercentage: Float,
    gadgetsPercentage: Float,
    educationPercentage: Float,
    familyPercentage: Float,
    donationsPercentage: Float,
    othersPercentage: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color(0xFF31434D))
            .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ExpenseCategoryProgress("Food", foodPercentage, Color(0xFFFFC444))
            ExpenseCategoryProgress("Clothes", clothesPercentage, Color(0xFFFFC444))
            ExpenseCategoryProgress("Rent", rentPercentage, Color(0xFFFFC444))
            ExpenseCategoryProgress("Travel", travelPercentage, Color(0xFFFFC444))
            ExpenseCategoryProgress("Family", familyPercentage, Color(0xFFFFC444))
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ExpenseCategoryProgress("Accessories", accessoriesPercentage, Color(0xFFFFC444))
            ExpenseCategoryProgress("Gadgets", gadgetsPercentage, Color(0xFFFFC444))
            ExpenseCategoryProgress("Education", educationPercentage, Color(0xFFFFC444))
            ExpenseCategoryProgress("Donations", donationsPercentage, Color(0xFFFFC444))
            ExpenseCategoryProgress("Others", othersPercentage, Color(0xFFFFC444))
        }
    }
}

@Composable
fun ExpenseCategoryProgress(
    category: String,
    percentage: Float,
    color: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$category (${(percentage * 100).toInt()}%)",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LinearProgressIndicator(
            progress = percentage,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = Color.Gray
        )
    }
}
