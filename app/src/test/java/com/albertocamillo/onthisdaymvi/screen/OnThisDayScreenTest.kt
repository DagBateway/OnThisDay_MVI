package com.albertocamillo.onthisdaymvi.screen


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.albertocamillo.onthisdaymvi.data.model.HistoricalEvent
import com.albertocamillo.onthisdaymvi.data.model.WikimediaResponse
import com.albertocamillo.onthisdaymvi.mvi.state.OnThisDayState
import com.albertocamillo.onthisdaymvi.mvi.viewmodel.OnThisDayViewModel
import com.albertocamillo.onthisdaymvi.ui.screen.OnThisDayScreen
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

/**
 * UI tests for the OnThisDayScreen Composable using Jetpack Compose testing framework.
 *
 * This class uses `createComposeRule` to render and inspect the Composable in a test environment.
 * It uses a mocked ViewModel and MutableStateFlow to simulate different UI states.
 *
 * Test cases verify:
 * - That historical events are displayed properly from the ViewModel's state
 * - That navigation buttons are present and visible
 *
 * These tests ensure the composable renders the correct UI elements for a given state,
 * making it easier to catch regressions and layout issues early.
 */


class OnThisDayScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: OnThisDayViewModel
    private val testState = MutableStateFlow(
        OnThisDayState(
            isLoading = false,
            data = WikimediaResponse(
                births = emptyList(),
                deaths = emptyList(),
                holidays = emptyList(),
                events = listOf(
                    HistoricalEvent("Something happened", 1990),
                    HistoricalEvent("Another thing happened", 2001)
                )
            ),
            error = null,
            selectedDate = LocalDate.of(2023, 7, 15)
        )
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockViewModel = mockk(relaxed = true)
        every { mockViewModel.state } returns testState
    }

    @Test
    fun eventsAreDisplayedCorrectly() {
        composeTestRule.setContent {
            OnThisDayScreen(viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("1990: Something happened").assertIsDisplayed()
        composeTestRule.onNodeWithText("2001: Another thing happened").assertIsDisplayed()
    }

    @Test
    fun navigationButtonsAreVisible() {
        composeTestRule.setContent {
            OnThisDayScreen(viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("← Previous").assertIsDisplayed()
        composeTestRule.onNodeWithText("Next →").assertIsDisplayed()
    }
}
