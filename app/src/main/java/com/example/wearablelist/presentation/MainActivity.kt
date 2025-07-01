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
import com.example.wearablelist.data.SportsDataProvider
import com.example.wearablelist.presentation.theme.WearableListTheme

// Data class para los elementos de la lista
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

// Rutas de navegación
object Routes {
    const val HOME = "home"
    const val DETAIL = "detail/{itemId}/{itemTitle}"
    const val SETTINGS = "settings"
    const val ABOUT = "about"
    const val CHAMPIONS = "champions"
    const val LALIGA = "laliga"
    const val PREMIER = "premier"
    const val NBA = "nba"
    const val MLB = "mlb"
    const val NFL = "nfl"
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

            composable(Routes.DETAIL + "/{itemId}/{itemTitle}") { backStackEntry ->
                val itemId = backStackEntry.arguments?.getString("itemId") ?: "0"
                val itemTitle = backStackEntry.arguments?.getString("itemTitle") ?: "Item"
                TeamDetailScreen(
                    navController = navController,
                    itemId = itemId,
                    itemTitle = itemTitle
                )
            }

            composable(Routes.CHAMPIONS) {
                ChampionsLeagueScreen(navController = navController)
            }

            composable(Routes.LALIGA) {
                LaLigaScreen(navController = navController)
            }

            composable(Routes.PREMIER) {
                PremierLeagueScreen(navController = navController)
            }

            composable(Routes.NBA) {
                NBAScreen(navController = navController)
            }

            composable(Routes.MLB) {
                MLBScreen(navController = navController)
            }

            composable(Routes.NFL) {
                NFLScreen(navController = navController)
            }

            composable(Routes.SETTINGS) {
                SettingsScreen(navController = navController)
            }

            composable(Routes.ABOUT) {
                AboutScreen(navController = navController)
            }

            composable(Routes.FAVORITES) {
                FavoritesScreen(navController = navController)
            }

            composable(Routes.SPORTS_NEWS) {
                SportsNewsScreen(navController = navController)
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
            itemsIndexed(sportsMenu) { index, item ->
                when (item.type) {
                    ItemType.HEADER -> {
                        HeaderItem(item = item)
                    }
                    ItemType.ACTION -> {
                        ActionItem(
                            item = item,
                            onClick = {
                                navController.navigate(item.route)
                            }
                        )
                    }
                    ItemType.NORMAL -> {
                        NormalItem(
                            item = item,
                            onClick = {
                                navController.navigate(item.route)
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
fun ChampionsLeagueScreen(navController: NavHostController) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    val listState = rememberScalingLazyListState()
    val teams = remember { SportsDataProvider.getChampionsLeagueTeams() }

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
                    itemsIndexed(teams) { index, team ->
                        when (team.type) {
                            ItemType.HEADER -> HeaderItem(item = team)
                            ItemType.ACTION -> ActionItem(
                                item = team,
                                onClick = { navController.navigate(team.route) }
                            )
                            ItemType.NORMAL -> NormalItem(
                                item = team,
                                onClick = { navController.navigate(team.route) }
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
fun LaLigaScreen(navController: NavHostController) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    val listState = rememberScalingLazyListState()
    val teams = remember { SportsDataProvider.getLaLigaTeams() }

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
                    itemsIndexed(teams) { index, team ->
                        when (team.type) {
                            ItemType.HEADER -> HeaderItem(item = team)
                            ItemType.ACTION -> ActionItem(
                                item = team,
                                onClick = { navController.navigate(team.route) }
                            )
                            ItemType.NORMAL -> NormalItem(
                                item = team,
                                onClick = { navController.navigate(team.route) }
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
fun PremierLeagueScreen(navController: NavHostController) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    val listState = rememberScalingLazyListState()
    val teams = remember { SportsDataProvider.getPremierLeagueTeams() }

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
                    itemsIndexed(teams) { index, team ->
                        when (team.type) {
                            ItemType.HEADER -> HeaderItem(item = team)
                            ItemType.ACTION -> ActionItem(
                                item = team,
                                onClick = { navController.navigate(team.route) }
                            )
                            ItemType.NORMAL -> NormalItem(
                                item = team,
                                onClick = { navController.navigate(team.route) }
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
fun NBAScreen(navController: NavHostController) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    val listState = rememberScalingLazyListState()
    val teams = remember { SportsDataProvider.getNBATeams() }

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
                    itemsIndexed(teams) { index, team ->
                        when (team.type) {
                            ItemType.HEADER -> HeaderItem(item = team)
                            ItemType.ACTION -> ActionItem(
                                item = team,
                                onClick = { navController.navigate(team.route) }
                            )
                            ItemType.NORMAL -> NormalItem(
                                item = team,
                                onClick = { navController.navigate(team.route) }
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
fun MLBScreen(navController: NavHostController) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    val listState = rememberScalingLazyListState()
    val teams = remember { SportsDataProvider.getMLBTeams() }

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
                    itemsIndexed(teams) { index, team ->
                        when (team.type) {
                            ItemType.HEADER -> HeaderItem(item = team)
                            ItemType.ACTION -> ActionItem(
                                item = team,
                                onClick = { navController.navigate(team.route) }
                            )
                            ItemType.NORMAL -> NormalItem(
                                item = team,
                                onClick = { navController.navigate(team.route) }
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
fun NFLScreen(navController: NavHostController) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()
    val listState = rememberScalingLazyListState()
    val teams = remember { SportsDataProvider.getNFLTeams() }

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
                    itemsIndexed(teams) { index, team ->
                        when (team.type) {
                            ItemType.HEADER -> HeaderItem(item = team)
                            ItemType.ACTION -> ActionItem(
                                item = team,
                                onClick = { navController.navigate(team.route) }
                            )
                            ItemType.NORMAL -> NormalItem(
                                item = team,
                                onClick = { navController.navigate(team.route) }
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

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        onDismissed = { navController.popBackStack() }
    ) { isBackground ->
        if (isBackground) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Desliza para volver", color = MaterialTheme.colors.onBackground)
            }
        } else {
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

                    Text(
                        text = teamDetails["league"] ?: "Liga",
                        style = MaterialTheme.typography.caption1,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            DetailRow("🏟️ Estadio", teamDetails["stadium"] ?: "N/A")
                            DetailRow("👥 Capacidad", teamDetails["capacity"] ?: "N/A")
                            DetailRow("📅 Fundado", teamDetails["founded"] ?: "N/A")
                            DetailRow("👤 Entrenador", teamDetails["coach"] ?: teamDetails["manager"] ?: "N/A")
                            DetailRow("🏆 Títulos", teamDetails["titles"] ?: "N/A")
                            DetailRow("🎨 Colores", teamDetails["colors"] ?: "N/A")
                        }
                    }

                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(top = 8.dp),
                        colors = ButtonDefaults.primaryButtonColors()
                    ) {
                        Text("Volver")
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

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun FavoritesScreen(navController: NavHostController) {
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
                        text = "⭐ Favoritos",
                        style = MaterialTheme.typography.title2,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Aquí aparecerán tus equipos favoritos",
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

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun SportsNewsScreen(navController: NavHostController) {
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
                    item {
                        Text(
                            text = "📰 Noticias",
                            style = MaterialTheme.typography.title2,
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    items(5) { index ->
                        val news = listOf(
                            "Real Madrid gana la Champions",
                            "Lakers en playoffs de NBA",
                            "Yankees firman nuevo jugador",
                            "Chiefs ganan Super Bowl",
                            "Barcelona fichaje sorpresa"
                        )

                        Card(
                            onClick = {
                                navController.navigate("detail/news_${index}/${news[index]}")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = news[index],
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
fun SettingsScreen(navController: NavHostController) {
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
                        text = "⚙️ Configuración",
                        style = MaterialTheme.typography.title2,
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Notificaciones deportivas",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Equipos favoritos",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
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

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun AboutScreen(navController: NavHostController) {
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
                        text = "🏆 Sports Hub",
                        style = MaterialTheme.typography.title2,
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "v1.0 para Wear OS",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Tu hub deportivo definitivo",
                        style = MaterialTheme.typography.caption1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
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

@Composable
fun HeaderItem(item: ListItem) {
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

@Composable
fun ActionItem(
    item: ListItem,
    onClick: () -> Unit
) {
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

@Composable
fun NormalItem(
    item: ListItem,
    onClick: () -> Unit
) {
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

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}