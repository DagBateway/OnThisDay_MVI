package com.albertocamillo.onthisdaymvi.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.albertocamillo.onthisdaymvi.mvi.intent.OnThisDayIntent
import com.albertocamillo.onthisdaymvi.mvi.viewmodel.OnThisDayViewModel

import java.time.format.DateTimeFormatter

// ============================
// OnThisDayScreen.kt
// ============================
/**
 * Jetpack Compose UI screen that renders the "On This Day" events.
 *
 * It displays the current selected date, lets the user navigate between days,
 * and shows a list of historical events. It also supports tapping on events
 * to show a detail dialog.
 *
 * Observes state from [OnThisDayViewModel] and triggers [OnThisDayIntent]s.
 */

@Composable
fun OnThisDayScreen(viewModel: OnThisDayViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding().padding(16.dp)) {
        // Date and navigation
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { viewModel.handleIntent(OnThisDayIntent.PreviousDate) }) {
                Text("← Previous")
            }
            Text(
                text = state.selectedDate.format(DateTimeFormatter.ofPattern("MMMM dd")),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Button(onClick = { viewModel.handleIntent(OnThisDayIntent.NextDate) }) {
                Text("Next →")
            }
        }

        Spacer(Modifier.height(16.dp))

        when {
            state.isLoading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            state.error != null -> Text("Error: ${state.error}")
            state.data != null -> {
                LazyColumn {
                    items(state.data?.events.orEmpty()) { event ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable {
                                    viewModel.handleIntent(OnThisDayIntent.SelectEvent(event))
                                },
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = event.year.toString(),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = event.text,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Event detail modal
    state.selectedEvent?.let { event ->
        AlertDialog(
            onDismissRequest = { viewModel.handleIntent(OnThisDayIntent.ClearSelectedEvent) },
            title = { Text("Event Details") },
            text = { Text("${event.year}: ${event.text}") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.handleIntent(OnThisDayIntent.ClearSelectedEvent)
                }) {
                    Text("Close")
                }
            }
        )
    }
}
