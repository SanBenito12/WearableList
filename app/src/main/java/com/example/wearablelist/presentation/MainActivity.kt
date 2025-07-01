package com.example.wearablelist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.wear.compose.material.Icon
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
    const val TASKS = "tasks"
    const val CONTACTS = "contacts"
    const val FITNESS = "fitness"
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
                DetailScreen(
                    navController = navController,
                    itemId = itemId,
                    itemTitle = itemTitle
                )
            }

            composable(Routes.SETTINGS) {
                SettingsScreen(navController = navController)
            }

            composable(Routes.ABOUT) {
                AboutScreen(navController = navController)
            }

            composable(Routes.TASKS) {
                TasksScreen(navController = navController)
            }

            composable(Routes.CONTACTS) {
                ContactsScreen(navController = navController)
            }

            composable(Routes.FITNESS) {
                FitnessScreen(navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val listState = rememberScalingLazyListState()

    // Datos de ejemplo para la lista principal
    val sampleItems = remember {
        listOf(
            ListItem(1, "Mi Lista Wearable", type = ItemType.HEADER),
            ListItem(2, "Mis Tareas", "Ver todas las tareas pendientes", route = Routes.TASKS),
            ListItem(3, "Contactos", "Lista de contactos favoritos", route = Routes.CONTACTS),
            ListItem(4, "Fitness", "Estadísticas de actividad física", route = Routes.FITNESS),
            ListItem(5, "Configuración", type = ItemType.ACTION, route = Routes.SETTINGS),
            ListItem(6, "Elemento 4", "Descripción del cuarto elemento"),
            ListItem(7, "Elemento 5", "Descripción del quinto elemento"),
            ListItem(8, "Elemento 6", "Descripción del sexto elemento"),
            ListItem(9, "Elemento 7", "Descripción del séptimo elemento"),
            ListItem(10, "Acerca de", type = ItemType.ACTION, route = Routes.ABOUT),
            ListItem(11, "Elemento 8", "Descripción del octavo elemento"),
            ListItem(12, "Elemento 9", "Descripción del noveno elemento"),
            ListItem(13, "Elemento 10", "Descripción del décimo elemento")
        )
    }

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
            itemsIndexed(sampleItems) { index, item ->
                when (item.type) {
                    ItemType.HEADER -> {
                        HeaderItem(item = item)
                    }
                    ItemType.ACTION -> {
                        ActionItem(
                            item = item,
                            onClick = {
                                if (item.route.isNotEmpty()) {
                                    navController.navigate(item.route)
                                } else {
                                    println("Clicked on action: ${item.title}")
                                }
                            }
                        )
                    }
                    ItemType.NORMAL -> {
                        NormalItem(
                            item = item,
                            onClick = {
                                if (item.route.isNotEmpty()) {
                                    navController.navigate(item.route)
                                } else {
                                    // Navegar a pantalla de detalle
                                    navController.navigate("detail/${item.id}/${item.title}")
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
fun DetailScreen(
    navController: NavHostController,
    itemId: String,
    itemTitle: String
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState()

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
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Detalles",
                        style = MaterialTheme.typography.title2,
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = itemTitle,
                        style = MaterialTheme.typography.title3,
                        color = MaterialTheme.colors.onBackground,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = "ID: $itemId",
                        style = MaterialTheme.typography.caption1,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(top = 16.dp),
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
fun TasksScreen(navController: NavHostController) {
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
                            text = "Mis Tareas",
                            style = MaterialTheme.typography.title2,
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    items(5) { index ->
                        Card(
                            onClick = {
                                navController.navigate("detail/task_${index}/Tarea ${index + 1}")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    text = "Tarea ${index + 1}",
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.onSurface
                                )
                                Text(
                                    text = "Descripción de la tarea ${index + 1}",
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
fun ContactsScreen(navController: NavHostController) {
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
                            text = "Contactos",
                            style = MaterialTheme.typography.title2,
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    items(4) { index ->
                        val names = listOf("Ana García", "Carlos López", "María Rodríguez", "Juan Pérez")
                        Card(
                            onClick = {
                                navController.navigate("detail/contact_${index}/${names[index]}")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                        ) {
                            Text(
                                text = names[index],
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.onSurface,
                                modifier = Modifier.padding(12.dp)
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
fun FitnessScreen(navController: NavHostController) {
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
                        text = "Fitness",
                        style = MaterialTheme.typography.title2,
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "👟 8,245 pasos",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(4.dp)
                    )

                    Text(
                        text = "🔥 320 calorías",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(4.dp)
                    )

                    Text(
                        text = "📍 6.2 km",
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(4.dp)
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
                        text = "Pantalla de configuración",
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
                        text = "ℹ️ Acerca de",
                        style = MaterialTheme.typography.title2,
                        color = MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "WearableList v1.0",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "App de ejemplo para Wear OS",
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