package com.example.wearablelist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import androidx.wear.compose.material.Card
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

// Data classes y enums
data class ListItem(
    val id: Int,
    val title: String,
    val subtitle: String = "",
    val type: ItemType = ItemType.NORMAL,
    val route: String = ""
)

enum class ItemType {
    HEADER,
    NORMAL,
    ACTION
}

// Rutas Para usar la navegación wear
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
                    teams = SportsDataProvider.getChampionsLeagueTeams()
                )
            }
            composable(Routes.LALIGA) {
                TeamsListScreen(
                    navController = navController,
                    teams = SportsDataProvider.getLaLigaTeams()
                )
            }
            composable(Routes.PREMIER) {
                TeamsListScreen(
                    navController = navController,
                    teams = SportsDataProvider.getPremierLeagueTeams()
                )
            }
            composable(Routes.NBA) {
                TeamsListScreen(
                    navController = navController,
                    teams = SportsDataProvider.getNBATeams()
                )
            }
            composable(Routes.MLB) {
                TeamsListScreen(
                    navController = navController,
                    teams = SportsDataProvider.getMLBTeams()
                )
            }
            composable(Routes.NFL) {
                TeamsListScreen(
                    navController = navController,
                    teams = SportsDataProvider.getNFLTeams()
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
                SimpleScreen(navController, "⚙️ Configuración", "Notificaciones deportivas\nEquipos favoritos")
            }
            composable(Routes.ABOUT) {
                SimpleScreen(navController, "🏆 EpicSports", "v1.0 para Wear OS\nTu app deportivo definitivo")
            }
            composable(Routes.FAVORITES) {
                SimpleScreen(navController, "⭐ Favoritos", "Prueba")
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

    Scaffold(
        timeText = { TimeText() },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
    ) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = 32.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            state = listState
        ) {
            itemsIndexed(sportsMenu) { _, item ->
                ListItemComponent(
                    item = item,
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

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun TeamsListScreen(navController: NavHostController, teams: List<ListItem>) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    val listState = rememberScalingLazyListState()

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        onDismissed = { navController.popBackStack() }
    ) { isBackground ->
        if (!isBackground) {
            Scaffold(
                timeText = { TimeText() },
                positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
            ) {
                ScalingLazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    state = listState
                ) {
                    itemsIndexed(teams) { _, team ->
                        ListItemComponent(
                            item = team,
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

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun TeamDetailScreen(
    navController: NavHostController,
    itemId: String,
    itemTitle: String
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    val teamDetails = remember { SportsDataProvider.getTeamDetails(itemId) }

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        onDismissed = { navController.popBackStack() }
    ) { isBackground ->
        if (!isBackground) {
            Scaffold(
                timeText = { TimeText() }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = teamDetails["name"] ?: itemTitle,
                        style = MaterialTheme.typography.title2,
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Card(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            DetailRow("🏟️ Estadio", teamDetails["stadium"] ?: teamDetails["arena"] ?: "N/A")
                            DetailRow("👥 Capacidad", teamDetails["capacity"] ?: "N/A")
                            DetailRow("📅 Fundado", teamDetails["founded"] ?: "N/A")
                            DetailRow("👤 Entrenador", teamDetails["coach"] ?: teamDetails["manager"] ?: "N/A")
                            DetailRow("🏆 Títulos", teamDetails["titles"] ?: "N/A")
                            DetailRow("🎨 Colores", teamDetails["colors"] ?: "N/A")
                        }
                    }

                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.primaryButtonColors()
                    ) {
                        Text("Volver")
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
        "Real Madrid gana la Champions",
        "Lakers en playoffs de NBA",
        "Yankees firman nuevo jugador",
        "Chiefs ganan Super Bowl",
        "Barcelona fichaje sorpresa cr7!"
    )

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        onDismissed = { navController.popBackStack() }
    ) { isBackground ->
        if (!isBackground) {
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
                        Text(
                            text = "📰 Noticias",
                            style = MaterialTheme.typography.title2,
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    itemsIndexed(news) { index, newsItem ->
                        Card(
                            onClick = {
                                navController.navigate("detail/news_${index}/${newsItem}")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = newsItem,
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.onSurface
                                )
                                Text(
                                    text = "Hace ${index + 1}h",
                                    style = MaterialTheme.typography.caption1,
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun SimpleScreen(navController: NavHostController, title: String, content: String) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        onDismissed = { navController.popBackStack() }
    ) { isBackground ->
        if (!isBackground) {
            Scaffold(
                timeText = { TimeText() }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.title2,
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = content,
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
                    )

                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}

// Componentes reutilizables
@Composable
fun ListItemComponent(item: ListItem, onClick: () -> Unit = {}) {
    when (item.type) {
        ItemType.HEADER -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.title2,
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        ItemType.ACTION -> {
            Chip(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClick,
                label = {
                    Text(
                        text = item.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = ChipDefaults.secondaryChipColors()
            )
        }
        ItemType.NORMAL -> {
            Card(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.title3,
                        color = MaterialTheme.colors.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (item.subtitle.isNotEmpty()) {
                        Text(
                            text = item.subtitle,
                            style = MaterialTheme.typography.caption1,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.caption1,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.caption1,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}