package com.stonetree.demowagen

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.view.View
import androidx.navigation.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.Visibility.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.stonetree.demowagen.features.manufacturer.view.adapter.ManufacturerAdapter.*
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.recyclerview.widget.RecyclerView
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread

@RunWith(AndroidJUnit4::class)
class ManufacturerViewTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainView::class.java)

    @Before
    fun setup() {
        activityTestRule.activity.apply {
            runOnUiThread {
                findNavController(R.id.wagen_nav_fragment).navigate(R.id.manufacturer_view)
            }
        }
    }

    @Test
    fun test_manufacturerListVisibility_shouldReturnGone() {
        onView(withId(R.id.manufacturer_list)).check(matches(withEffectiveVisibility(GONE)))
    }

    @Test
    fun test_screenRotation_shouldKeepTitle() {
        activityTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        assertEquals(activityTestRule.activity.title, "Das Auto App")
    }

    @Test
    fun test_evenRowColor_shouldReturnGray() {
        Thread.sleep(5000)
        onView(withId(R.id.manufacturer_list))
            .perform(action {RecyclerViewActions.scrollTo<ViewHolder>(atPosition(0, hasBackground(Color.LTGRAY)))})
    }

    @Test
    fun test_liveDataOnRotation_shouldKeepData() {
        activityTestRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onView(withId(R.id.manufacturer_list)).perform(
            action {RecyclerViewActions.scrollTo<ViewHolder>(withText("Tesla"))}
        )
    }

    @Test
    fun test_manufacturerBundle_ShouldReturnWKDA() {
        Thread.sleep(5000)
        onView(withId(R.id.manufacturer_list))
            .perform(action {RecyclerViewActions.scrollTo<ViewHolder>(withText("Suzuki"))}, click())
    }
}