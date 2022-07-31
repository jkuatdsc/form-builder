package com.dsc.formbuilder

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.SemanticsProperties.ToggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val TAG = "MAIN_ACTIVITY_COMPOSE_UI_TEST"

@RunWith(AndroidJUnit4::class)
class MainActivityComposeUiTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun email_updates_on_user_input() {
        with(composeTestRule.onNodeWithText("Email")) {
            performTextClearance()
            performTextInput("testing@email.com")
            assertTextEquals("Email", "testing@email.com")
        }
    }

    @Test
    fun password_updates_on_user_input() {
        with(composeTestRule.onNodeWithText("Password")) {
            performTextClearance()
            performTextInput("abcd@1234")
            assertTextEquals("Password", "abcd@1234")
        }
    }

    @Test
    fun age_updates_on_user_input() {
        with(composeTestRule.onNodeWithText("Age")) {
            performTextClearance()
            performTextInput("23")
            assertTextEquals("Age", "23")
        }
    }

    @Test
    fun gender_updates_on_user_input() {
        with(composeTestRule.onNodeWithText("Male")) {
            onSiblings().filter(hasClickAction()).onLast().performClick()
            onSiblings().filter(hasClickAction()).onFirst().assertIsNotSelected()
        }
    }

    @Test
    fun happiness_index_updates_on_user_input() {
        with(composeTestRule.onNodeWithText("Happiness level: 0")) {
            assertExists()
        }

        with(
            composeTestRule.onNode(
                hasProgressBarRangeInfo(
                    ProgressBarRangeInfo(current = 0.0f, range = 0.0f..1.0f, steps = 0)
                )
            )
        ) {
            assertExists()
            performTouchInput {
                down(centerLeft)
                moveTo(centerLeft + percentOffset(0.5f, 0f), 2000L)
                up()
            }
        }

        with(composeTestRule.onNodeWithText("Happiness level: 47")) {
            assertExists()
        }
    }

    @Test
    fun hobbies_update_on_user_input() {
        with(composeTestRule.onNodeWithText("Chess")) {
            assertExists()

            with(onSiblings().filter(hasClickAction()).onFirst()) {
                assertExists()

                assert(fetchSemanticsNode().config[ToggleableState] == ToggleableState(true))
                performClick()
                assert(fetchSemanticsNode().config[ToggleableState] == ToggleableState(false))
            }
        }

    }

    @Test
    fun print_semantics_tree() {
        composeTestRule.onRoot(useUnmergedTree = false).printToLog(TAG)
    }
}