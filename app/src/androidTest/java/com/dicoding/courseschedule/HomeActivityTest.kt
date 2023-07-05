package com.dicoding.courseschedule

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import com.dicoding.courseschedule.ui.home.HomeActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class HomeActivityTest {

    @Before
    fun prepareTest() {
        ActivityScenario.launch(HomeActivity::class.java)
        Intents.init()
    }

    @Test
    fun onTapGoToAddScreen() {
        Espresso.onView(ViewMatchers.withId(R.id.action_add)).perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasComponent(AddCourseActivity::class.java.name))
    }

}