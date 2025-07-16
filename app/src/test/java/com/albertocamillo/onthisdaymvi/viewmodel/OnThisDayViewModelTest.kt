package com.albertocamillo.onthisdaymvi.viewmodel

import com.albertocamillo.onthisdaymvi.data.db.OnThisDayDao
import com.albertocamillo.onthisdaymvi.data.db.OnThisDayEntity
import com.albertocamillo.onthisdaymvi.mvi.intent.OnThisDayIntent
import com.albertocamillo.onthisdaymvi.mvi.viewmodel.OnThisDayViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

/**
 * Unit tests for the OnThisDayViewModel class.
 *
 * This test class focuses on verifying the behaviour of the ViewModel when handling intents,
 * particularly the logic for loading data from the Room cache. It uses MockK to simulate the DAO,
 * allowing us to test how the ViewModel reacts without hitting the real database.
 *
 * Key test cases include:
 * - Loading today's historical data from a cached Room entity
 * - Ensuring the ViewModel correctly updates state with expected data
 *
 * This test ensures the ViewModel's intent-handling logic is robust, isolated, and behaves
 * predictably under mocked conditions.
 */

class OnThisDayViewModelTest {

    private lateinit var dao: OnThisDayDao
    private lateinit var viewModel: OnThisDayViewModel

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        viewModel = OnThisDayViewModel(dao)
    }

    @Test
    fun `test LoadToday loads from cache if available`() = runTest {
        val today = LocalDate.now()
        val key = today.format(java.time.format.DateTimeFormatter.ofPattern("MM-dd"))
        val fakeEntity =
            OnThisDayEntity(date = key, json = """{"events":[{"year":1990,"text":"Some event"}]}""")

        coEvery { dao.getByDate(key) } returns fakeEntity

        viewModel.handleIntent(OnThisDayIntent.LoadToday)

        assert(viewModel.state.value.data?.events?.isNotEmpty() == true)
    }
}
