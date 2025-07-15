package com.albertocamillo.onthisdaymvi.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.albertocamillo.onthisdaymvi.mvi.intent.OnThisDayIntent
import com.albertocamillo.onthisdaymvi.mvi.viewmodel.OnThisDayViewModel

import java.time.format.DateTimeFormatter

@Composable
fun OnThisDayScreen(viewModel: OnThisDayViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
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
            state.isLoading -> CircularProgressIndicator()
            state.error != null -> Text("Error: ${state.error}")
            state.data != null -> {
                LazyColumn {
                    items(state.data!!.events) { event ->
                        Text(
                            text = "${event.year}: ${event.text}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.handleIntent(OnThisDayIntent.SelectEvent(event))
                                }
                                .padding(8.dp)
                        )
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
