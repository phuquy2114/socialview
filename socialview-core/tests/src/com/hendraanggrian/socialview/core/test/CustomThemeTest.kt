package com.hendraanggrian.socialview.core.test

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.hendraanggrian.socialview.core.test.activity.CustomThemeActivity
import com.hendraanggrian.socialview.test.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
@RunWith(AndroidJUnit4::class)
class CustomThemeTest : BaseTest() {

    @Rule @JvmField var rule = ActivityTestRule(CustomThemeActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun introduction() {
        onView(withId(R.id.editText)).perform(
                typeText("This is a standard TextView with #hashtag, @mention, and http://some.url support."),
                closeSoftKeyboard())
        onView(withId(R.id.progressBar)).perform(delay())
    }
}