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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.tooling.preview.devices.WearDevices
import com.example.wearablelist.presentation.theme.WearableListTheme

// Data class para los elementos de la lista
data class ListItem(
    val id: Int,
    val title: String,
    val subtitle: String = "",
    val type: ItemType = ItemType.NORMAL
)

enum class ItemType {
    HEADER,
    NORMAL,
    ACTION
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

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun WearApp() {
    WearableListTheme {
        val listState = rememberScalingLazyListState()

        // Datos de ejemplo para la lista
        val sampleItems = remember {
            listOf(
                ListItem(1, "Mi Lista Wearable", type = ItemType.HEADER),
                ListItem(2, "Elemento 1", "Descripción del primer elemento"),
                ListItem(3, "Elemento 2", "Descripción del segundo elemento"),
                ListItem(4, "Elemento 3", "Descripción del tercer elemento"),
                ListItem(5, "Configuración", type = ItemType.ACTION),
                ListItem(6, "Elemento 4", "Descripción del cuarto elemento"),
                ListItem(7, "Elemento 5", "Descripción del quinto elemento"),
                ListItem(8, "Elemento 6", "Descripción del sexto elemento"),
                ListItem(9, "Elemento 7", "Descripción del séptimo elemento"),
                ListItem(10, "Acerca de", type = ItemType.ACTION),
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
                                    // Aquí puedes manejar los clicks de los elementos de acción
                                    println("Clicked on action: ${item.title}")
                                }
                            )
                        }
                        ItemType.NORMAL -> {
                            NormalItem(
                                item = item,
                                onClick = {
                                    // Aquí puedes manejar los clicks de los elementos normales
                                    println("Clicked on item: ${item.title}")
                                }
                            )
                        }
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

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true)
@Composable
fun LargeRoundPreview() {
    WearApp()
}

@Preview(device = WearDevices.SQUARE, showSystemUi = true)
@Composable
fun SquarePreview() {
    WearApp()
}