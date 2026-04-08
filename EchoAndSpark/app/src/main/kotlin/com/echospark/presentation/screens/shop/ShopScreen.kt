package com.echospark.presentation.screens.shop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.echospark.presentation.components.PartyButton
import com.echospark.ui.theme.SparkGold
import com.echospark.ui.theme.TextLight

@Composable
fun SparkShopScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentBalance by remember { mutableStateOf(2850) }
    val ownedItems by remember { mutableStateOf(listOf("Heart Badge", "Couple Milestone Theme")) }

    val shopItems = listOf(
        ShopItem("💎 Heart Badge", "Show your couple status", 150, "Badge", true),
        ShopItem("🎨 Neon Theme", "Electric pink & purple vibes", 200, "Theme", false),
        ShopItem("⭐ VIP Status", "30 days of premium access", 500, "Premium", false),
        ShopItem("🌟 Couple Milestone Theme", "Custom couple milestone display", 300, "Theme", true),
        ShopItem("🎭 Spicy Questions Pack", "50 extra bold questions", 250, "Questions", false),
        ShopItem("💝 Love Timer", "Track your relationship days", 100, "Feature", false),
        ShopItem("🎪 Party Mode", "Colorful party animations", 175, "Feature", false),
        ShopItem("👑 Premium Crown Badge", "Show you're VIP", 400, "Badge", false),
        ShopItem("🌙 Night Mode Theme", "Comfortable dark theme", 180, "Theme", false),
        ShopItem("🎯 Perfect Streak Badge", "50 consecutive perfect matches", 300, "Badge", false),
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A0033),
                        Color(0xFF2D1B4E),
                        Color(0xFF1A0033)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Spark Shop 🛍️",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = TextLight
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Balance Display
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(SparkGold, Color(0xFFFFA500))
                        )
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Your Balance",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Black.copy(alpha = 0.8f)
                    )
                    Text(
                        "$currentBalance ✨",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold
                        ),
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Grid of items
            Text(
                "Available Items",
                style = MaterialTheme.typography.labelLarge,
                color = TextLight,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                userScrollEnabled = false
            ) {
                items(shopItems.size) { index ->
                    ShopItemCard(
                        item = shopItems[index],
                        currentBalance = currentBalance,
                        isOwned = ownedItems.contains(shopItems[index].name),
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Owned Items Section
            if (ownedItems.isNotEmpty()) {
                Text(
                    "Your Items (${ownedItems.size})",
                    style = MaterialTheme.typography.labelLarge,
                    color = SparkGold,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                ownedItems.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .padding(bottom = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.08f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "✅",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    item,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = SparkGold,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "Owned",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextLight.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

data class ShopItem(
    val name: String,
    val description: String,
    val cost: Int,
    val type: String,
    val isOwned: Boolean
)

@Composable
fun ShopItemCard(
    item: ShopItem,
    currentBalance: Int,
    isOwned: Boolean,
    modifier: Modifier = Modifier
) {
    val canAfford = currentBalance >= item.cost

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = if (isOwned)
                Color(0xFFFFD700).copy(alpha = 0.15f)
            else
                Color.White.copy(alpha = 0.08f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                item.name.split(" ")[0],
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                item.description,
                style = MaterialTheme.typography.labelSmall,
                color = TextLight.copy(alpha = 0.7f),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (isOwned) {
                Box(
                    modifier = Modifier
                        .background(
                            color = SparkGold.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        "Owned ✅",
                        style = MaterialTheme.typography.labelSmall,
                        color = SparkGold,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .background(
                            color = if (canAfford) Color(0xFF00D4FF).copy(alpha = 0.2f)
                            else Color.Red.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        "${item.cost} ✨",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (canAfford) Color(0xFF00D4FF) else Color(0xFFFF6B6B),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
