package com.stonetree.demowagen

import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.intent.Checks
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import com.stonetree.demowagen.features.manufacturer.view.adapter.ManufacturerAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher
import kotlin.reflect.KClass

class AnyClass {
    companion object {
        inline fun <reified T> of(clazz: Class<T>): T {
            return clazz.newInstance()
        }
    }
}

fun<T: Any> T.accessField(fieldName: String): Any? {
    return this?.javaClass?.getDeclaredField(fieldName).let { field ->
        field?.isAccessible = true
        return@let field?.get(this)
    }
}

fun action(function: () -> Unit): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return ViewMatchers.isEnabled()
        }

        override fun getDescription(): String {
            return "with view action: $function"
        }

        override fun perform(uiController: UiController, view: View) {
            function()
        }
    }
}

 fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position)
                ?:
                return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}

fun withItemId(id: Long): BoundedMatcher<RecyclerView.ViewHolder, ManufacturerAdapter.ViewHolder> {
    return object : BoundedMatcher<RecyclerView.ViewHolder, ManufacturerAdapter.ViewHolder>(
        ManufacturerAdapter.ViewHolder::class.java!!
    ) {
        override fun matchesSafely(item: ManufacturerAdapter.ViewHolder): Boolean {
            return item.itemId == id
        }

        override fun describeTo(description: Description) {
            description.appendText("view holder with id: $id")
        }
    }
}