package com.inhouse.pocboxandroid

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.inhouse.pocboxandroid.topiclist.adapter.TopicListAdapter
import org.junit.Test

class ApplicationTest {
    @Test
    fun runApp() {
        ActivityScenario.launch(MainActivity::class.java)

        // Topic List
        Espresso.onView(ViewMatchers.withId(R.id.rv_topic_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<TopicListAdapter.TopicViewHolder>(
                0,
                ViewActions.click()
            )
        )

        // Image Slider
        Espresso.onView(ViewMatchers.withId(R.id.btn_renew_items)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.slider_view_image)).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )

        Espresso.onView(ViewMatchers.withId(R.id.btn_add_new_item)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btn_add_new_item)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btn_add_new_item)).perform(ViewActions.click())

        Espresso.pressBack()
        Espresso.onView(ViewMatchers.withId(R.id.rv_topic_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}