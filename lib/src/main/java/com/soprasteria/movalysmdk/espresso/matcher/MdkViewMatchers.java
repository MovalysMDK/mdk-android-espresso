/**
 * Copyright (C) 2010 Sopra Steria Group (movalys.support@soprasteria.com)
 *
 * This file is part of Movalys MDK.
 * Movalys MDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Movalys MDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with Movalys MDK. If not, see <http://www.gnu.org/licenses/>.
 */
package com.soprasteria.movalysmdk.espresso.matcher;

import android.support.annotation.NonNull;
import android.support.test.espresso.core.deps.guava.base.Preconditions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

/**
 * Add view matchers for espresso tests.
 */
public class MdkViewMatchers {

    /**
     * Log tag.
     */
    private static final String LOG_TAG = "MdkViewMatchers";

    /**
     * Create a matcher checking a text is equals to the concat text computed from string resource ids.
     * @param resourceIds list of resource id.
     * @return matcher.
     */
    public static Matcher<View> withConcatText(int... resourceIds) {
        return withCharSequence(resourceIds);
    }

    /**
     * Create macher checking a hint is equals to the concat text computed from string ressource ids.
     * @param resourceIds list of resource ids
     * @return matcher
     */
    public static Matcher<View> withConcatHint(final int... resourceIds) {
        return new AbstractTextViewBoundedMatcher(resourceIds) {
            @Override
            protected CharSequence getText(TextView textView) {
                return textView.getHint();
            }
        };
    }

    /**
     * Create macher checking a text in upper case is equals to the upper case value of a view.
     * @param text text
     * @return matcher.
     */
    public static Matcher<View> withTextUpperCase(@NonNull String text) {
        return withTextUpperCase(Matchers.is(text.toUpperCase()));
    }

    /**
     * Create macher checking a text in upper case is equals to the upper case value of a view.
     * @param stringMatcher string matcher
     * @return
     */
    public static Matcher<View> withTextUpperCase(final Matcher<String> stringMatcher) {
        Preconditions.checkNotNull(stringMatcher);
        return new BoundedMatcher<View, TextView>(TextView.class) {

            public void describeTo(Description description) {
                description.appendText("with text: ");
                stringMatcher.describeTo(description);
            }

            public boolean matchesSafely(TextView textView) {
                return stringMatcher.matches(textView.getText().toString().toUpperCase());
            }
        };
    }

    /**
     * Create a matcher checking a text is equals to the concat text computed from string resource ids.
     * @param resourceIds  list of resource id.
     * @return matcher.
     */
    private static Matcher<View> withCharSequence(final int... resourceIds) {
        return new AbstractTextViewBoundedMatcher(resourceIds) {
            @Override
            protected CharSequence getText(TextView textView) {
                return textView.getText();
            }
        };
    }
}
