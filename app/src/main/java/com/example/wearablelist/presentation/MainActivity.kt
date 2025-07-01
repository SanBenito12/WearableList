package com.example.wearablelist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.ButtonColors
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.CardDefaults
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.SwipeToDismissBox
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberSwipeToDismissBoxState
import androidx.wear.tooling.preview.devices.WearDevices
import com.example.wearablelist.presentation.data.SportsDataProvider
import com.example.wearablelist.presentation.theme.WearableListTheme
import kotlinx.coroutines.delay

// Data classes y enums
data class ListItem(
    val id: Int,
    val title: String,
    val subtitle: String = "",
    val type: ItemType = ItemType.NORMAL,
    val route: String = "",
    val emoji: String = ""
)

enum class ItemType {
    HEADER,
    NORMAL,
    ACTION
}

// Rutas de navegación
object Routes {
    const val HOME = "home"
    const val DETAIL = "detail/{itemId}/{itemTitle}"
    const val CHAMPIONS = "champions"
    const val LALIGA = "laliga"
    const val PREMIER = "premier"
    const val NBA = "nba"
    const val MLB = "mlb"
    const val NFL = "nfl"
    const val SETTINGS = "settings"
    const val ABOUT = "about"
    const val FAVORITES = "favorites"
    const val SPORTS_NEWS = "sports_news"
}

// Colores épicos
object EpicColors {
    val primaryGradient = listOf(
        Color(0xFF6366F1), // Indigo
        Color(0xFF8B5CF6), // Violet
        Color(0xFFEC4899)  // Pink
    )
    val secondaryGradient = listOf(
        Color(0xFF10B981), // Emerald
        Color(0xFF06B6D4), // Cyan
        Color(0xFF3B82F6)  // Blue
    )
    val accentGradient = listOf(
        Color(0xFFEF4444), // Red
        Color(0xFFF59E0B), // Amber
        Color(0xFFEAB308)  // Yellow
    )
    val darkGradient = listOf(
        Color(0xFF1F2937), // Gray-800
        Color(0xFF374151), // Gray-700
        Color(0xFF4B5563)  // Gray-600
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    WearableListTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Routes.HOME
        ) {
            composable(Routes.HOME) {
                HomeScreen(navController = navController)
            }
            composable(Routes.CHAMPIONS) {
                TeamsListScreen(
                    navController = navController,
                    teams = SportsDataProvider.getChampionsLeagueTeams(),
                    gradientColors = EpicColors.primaryGradient
                )
            }
            composable(Routes.LALIGA) {
                TeamsListScreen(
                    navController = navController,
                    teams = SportsDataProvider.getLaLigaTeams(),
                    gradientColors = EpicColors.accentGradient
                )
            }
            composable(Routes.PREMIER) {
                TeamsListScreen(
                    navController = navController,
                    teams = SportsDataProvider.getPremierLeagueTeams(),
                    gradientColors = EpicColors.secondaryGradient
                )
            }
            composable(Routes.NBA) {
                TeamsListScreen(
                    navController = navController,
                    teams = SportsDataProvider.getNBATeams(),
                    gradientColors = listOf(Color(0xFFFF6B35), Color(0xFFF7931E))
                )
            }
            composable(Routes.MLB) {
                TeamsListScreen(
                    navController = navController,
                    teams = SportsDataProvider.getMLBTeams(),
                    gradientColors = listOf(Color(0xFF1E40AF), Color(0xFF3B82F6))
                )
            }
            composable(Routes.NFL) {
                TeamsListScreen(
                    navController = navController,
                    teams = SportsDataProvider.getNFLTeams(),
                    gradientColors = listOf(Color(0xFF059669), Color(0xFF10B981))
                )
            }
            composable("detail/{itemId}/{itemTitle}") { backStackEntry ->
                val itemId = backStackEntry.arguments?.getString("itemId") ?: "0"
                val itemTitle = backStackEntry.arguments?.getString("itemTitle") ?: "Item"
                TeamDetailScreen(
                    navController = navController,
                    itemId = itemId,
                    itemTitle = itemTitle
                )
            }
            composable(Routes.SETTINGS) {
                SimpleScreen(navController, "⚙️", "Configuración", "Personaliza\nNotificaciones", EpicColors.darkGradient)
            }
            composable(Routes.ABOUT) {
                SimpleScreen(navController, "🏆", "EpicSports", "v1.0 Wear OS\n¡Tu app deportiva!", EpicColors.primaryGradient)
            }
            composable(Routes.FAVORITES) {
                SimpleScreen(navController, "⭐", "Favoritos", "Equipos favoritos\n¡Próximamente!", EpicColors.accentGradient)
            }
            composable(Routes.SPORTS_NEWS) {
                NewsScreen(navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val listState = rememberScalingLazyListState()
    val sportsMenu = remember { SportsDataProvider.getSportsMenu() }

    var isVisible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(1000)
    )

    LaunchedEffect(Unit) {
        delay(200)
        isVisible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1E1B4B).copy(alpha = 0.9f),
                        Color(0xFF0F0F23).copy(alpha = 0.95f),
                        Color.Black
                    ),
                    radius = 300f
                )
            )
    ) {
        Scaffold(
            timeText = {
                TimeText(
                    timeTextStyle = MaterialTheme.typography.caption1.copy(
                        color = Color.White.copy(alpha = 0.8f)
                    )
                )
            },
            vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
            positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
        ) {
            ScalingLazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(alpha),
                contentPadding = PaddingValues(
                    top = 40.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 40.dp
                ),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                state = listState
            ) {
                itemsIndexed(sportsMenu) { index, item ->
                    val animationDelay = index * 100
                    EpicListItem(
                        item = item,
                        animationDelay = animationDelay,
                        onClick = {
                            if (item.route.isNotEmpty()) {
                                navController.navigate(item.route)
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun TeamsListScreen(
    navController: NavHostController,
    teams: List<ListItem>,
    gradientColors: List<Color>
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    val listState = rememberScalingLazyListState()

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        onDismissed = { navController.popBackStack() }
    ) { isBackground ->
        if (!isBackground) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = gradientColors.map { it.copy(alpha = 0.1f) } + Color.Black
                        )
                    )
            ) {
                Scaffold(
                    timeText = { TimeText() },
                    positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
                ) {
                    ScalingLazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        state = listState
                    ) {
                        itemsIndexed(teams) { index, team ->
                            EpicListItem(
                                item = team,
                                animationDelay = index * 50,
                                gradientColors = gradientColors,
                                onClick = {
                                    if (team.route.isNotEmpty()) {
                                        navController.navigate(team.route)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun TeamDetailScreen(
    navController: NavHostController,
    itemId: String,
    itemTitle: String
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    val teamDetails = remember { SportsDataProvider.getTeamDetails(itemId) }

    var isVisible by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.8f,
        animationSpec = tween(800)
    )

    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        onDismissed = { navController.popBackStack() }
    ) { isBackground ->
        if (!isBackground) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF312E81).copy(alpha = 0.6f),
                                Color(0xFF1E1B4B).copy(alpha = 0.8f),
                                Color.Black
                            )
                        )
                    )
            ) {
                Scaffold(
                    timeText = { TimeText() }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .scale(scale),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Header con icono y título
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = EpicColors.primaryGradient
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🏆",
                                fontSize = 24.sp
                            )
                        }

                        Text(
                            text = teamDetails["name"] ?: itemTitle,
                            style = MaterialTheme.typography.title2.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            ),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = teamDetails["league"] ?: "Liga",
                            style = MaterialTheme.typography.caption1,
                            color = Color.White.copy(alpha = 0.7f),
                            textAlign = TextAlign.Center
                        )

                        Card(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                EpicDetailRow("🏟️", "Estadio", teamDetails["stadium"] ?: teamDetails["arena"] ?: "N/A")
                                EpicDetailRow("👥", "Capacidad", teamDetails["capacity"] ?: "N/A")
                                EpicDetailRow("📅", "Fundado", teamDetails["founded"] ?: "N/A")
                                EpicDetailRow("👤", "Entrenador", teamDetails["coach"] ?: teamDetails["manager"] ?: "N/A")
                                EpicDetailRow("🏆", "Títulos", teamDetails["titles"] ?: "N/A")
                                EpicDetailRow("🎨", "Colores", teamDetails["colors"] ?: "N/A")
                            }
                        }

                        Button(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("← Volver", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun NewsScreen(navController: NavHostController) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    val listState = rememberScalingLazyListState()

    val news = listOf(
        "🔥 Real Madrid conquista Europa",
        "🏀 Lakers dominan los playoffs",
        "⚾ Yankees rompen récords",
        "🏈 Chiefs, campeones imparables",
        "⚽ Barcelona sorprende al mundo"
    )

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        onDismissed = { navController.popBackStack() }
    ) { isBackground ->
        if (!isBackground) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF0F766E).copy(alpha = 0.3f),
                                Color(0xFF164E63).copy(alpha = 0.5f),
                                Color.Black
                            )
                        )
                    )
            ) {
                Scaffold(
                    timeText = { TimeText() },
                    positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
                ) {
                    ScalingLazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        state = listState
                    ) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "📰 Noticias Épicas",
                                    style = MaterialTheme.typography.title2.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = Color.White
                                )
                            }
                        }

                        itemsIndexed(news) { index, newsItem ->
                            EpicNewsCard(
                                newsItem = newsItem,
                                timeAgo = "Hace ${index + 1}h",
                                onClick = {
                                    navController.navigate("detail/news_${index}/${newsItem}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun SimpleScreen(
    navController: NavHostController,
    icon: String,
    title: String,
    content: String,
    gradientColors: List<Color>
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()

    var isVisible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(800)
    )

    LaunchedEffect(Unit) {
        delay(200)
        isVisible = true
    }

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        onDismissed = { navController.popBackStack() }
    ) { isBackground ->
        if (!isBackground) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = gradientColors.map { it.copy(alpha = 0.3f) } + Color.Black
                        )
                    )
            ) {
                Scaffold(
                    timeText = { TimeText() }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .alpha(alpha),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(
                                    brush = Brush.radialGradient(colors = gradientColors)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = icon, fontSize = 24.sp)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = title,
                            style = MaterialTheme.typography.title2.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = content,
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                            color = Color.White.copy(alpha = 0.8f)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = { navController.popBackStack() }
                        ) {
                            Text("← Volver", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

// Componentes épicos
@Composable
fun EpicListItem(
    item: ListItem,
    animationDelay: Int = 0,
    gradientColors: List<Color> = EpicColors.primaryGradient,
    onClick: () -> Unit = {}
) {
    var isVisible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(600, delayMillis = animationDelay)
    )
    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.8f,
        animationSpec = tween(600, delayMillis = animationDelay)
    )

    LaunchedEffect(Unit) {
        delay(50)
        isVisible = true
    }

    when (item.type) {
        ItemType.HEADER -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .alpha(alpha)
                    .scale(scale),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.radialGradient(colors = gradientColors)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🏆",
                            fontSize = 24.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.title2.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        ItemType.ACTION -> {
            Chip(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(alpha)
                    .scale(scale),
                onClick = onClick,
                label = {
                    Text(
                        text = item.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = ChipDefaults.secondaryChipColors()
            )
        }
        ItemType.NORMAL -> {
            Card(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(alpha)
                    .scale(scale)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.radialGradient(colors = gradientColors)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = when {
                                item.title.contains("Champions") -> "⚽"
                                item.title.contains("Liga") -> "🇪🇸"
                                item.title.contains("Premier") -> "🏴󠁧󠁢󠁥󠁮󠁧󠁿"
                                item.title.contains("NBA") -> "🏀"
                                item.title.contains("MLB") -> "⚾"
                                item.title.contains("NFL") -> "🏈"
                                else -> "⚽"
                            },
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.title3.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (item.subtitle.isNotEmpty()) {
                            Text(
                                text = item.subtitle,
                                style = MaterialTheme.typography.caption1,
                                color = Color.White.copy(alpha = 0.7f),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EpicDetailRow(icon: String, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = icon,
            fontSize = 16.sp,
            modifier = Modifier.width(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.caption1.copy(
                fontWeight = FontWeight.Medium
            ),
            color = Color.White.copy(alpha = 0.9f),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.caption1,
            color = Color.White.copy(alpha = 0.7f),
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun EpicNewsCard(newsItem: String, timeAgo: String, onClick: () -> Unit) {
    var isPressed by remember { mutableStateOf(false) }

    Card(
        onClick = {
            isPressed = !isPressed
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(colors = EpicColors.accentGradient)
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = newsItem,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = timeAgo,
                    style = MaterialTheme.typography.caption1,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}