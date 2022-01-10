package com.ojtapp.divinglog.view.main;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.ojtapp.divinglog.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UITest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void uITest() {
        // --------データを作成--------------------------------
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.button_add),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withText("本数"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView.check(matches(withText("本数")));

        ViewInteraction textView2 = onView(
                allOf(withText("場所"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView2.check(matches(withText("場所")));

        ViewInteraction textView3 = onView(
                allOf(withText("日付"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView3.check(matches(withText("日付")));

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.add_dive_number),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0)));
        appCompatEditText.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.add_dive_number),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0)));
        appCompatEditText2.perform(scrollTo(), replaceText("111"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.add_place),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1)));
        appCompatEditText3.perform(scrollTo(), replaceText("aaa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.add_point),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1)));
        appCompatEditText4.perform(scrollTo(), replaceText("bbb"), closeSoftKeyboard());

        ViewInteraction textView4 = onView(
                allOf(withText("時間"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView4.perform(ViewActions.scrollTo()).check(matches(withText("時間")));

        ViewInteraction textView5 = onView(withText("水深 ( m )"));
        textView5.perform(ViewActions.scrollTo()).check(matches(withText("水深 ( m )")));

        ViewInteraction textView6 = onView(withText("残圧 ( bar )"));
        textView6.perform(ViewActions.scrollTo()).check(matches(withText("残圧 ( bar )")));

        ViewInteraction textView7 = onView(withText("コンディション"));
        textView7.perform(ViewActions.scrollTo()).check(matches(withText("コンディション")));

        ViewInteraction textView8 = onView(withText("バディ"));
        textView8.perform(ViewActions.scrollTo()).check(matches(withText("バディ")));

        ViewInteraction textView9 = onView(withText("メモ"));
        textView9.perform(ViewActions.scrollTo()).check(matches(withText("メモ")));

        ViewInteraction textView10 = onView(withText("写真"));
        textView10.perform(ViewActions.scrollTo()).check(matches(withText("写真")));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button_make_task), withText("作成"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                23)));
        appCompatButton.perform(scrollTo(), click());

        // --------作成したデータをリストで確認--------------------------------
        ViewInteraction textView11 = onView(
                allOf(withId(R.id.list_diving_number), withText("111"),
                        withParent(allOf(withId(R.id.list_log_item),
                                withParent(withId(R.id.list_view_log)))),
                        isDisplayed()));
        textView11.check(matches(withText("111")));

        ViewInteraction textView12 = onView(
                allOf(withId(R.id.list_place), withText("aaa"),
                        withParent(allOf(withId(R.id.list_log_item),
                                withParent(withId(R.id.list_view_log)))),
                        isDisplayed()));
        textView12.check(matches(withText("aaa")));

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.list_point), withText("bbb"),
                        withParent(allOf(withId(R.id.list_log_item),
                                withParent(withId(R.id.list_view_log)))),
                        isDisplayed()));
        textView13.check(matches(withText("bbb")));

        // --------データを編集-------------------------------
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.list_button_edit),
                        childAtPosition(
                                allOf(withId(R.id.list_log_item),
                                        withParent(withId(R.id.list_view_log))),
                                6),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.edit_dive_number), withText("111"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0)));
        appCompatEditText5.perform(scrollTo(), replaceText("222"));

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.edit_dive_number), withText("222"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText6.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.edit_place), withText("aaa"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1)));
        appCompatEditText7.perform(scrollTo(), replaceText("ccc"));

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.edit_place), withText("ccc"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1),
                        isDisplayed()));
        appCompatEditText8.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.edit_point), withText("bbb"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1)));
        appCompatEditText9.perform(scrollTo(), replaceText("ddd"));

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.edit_point), withText("ddd"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1),
                        isDisplayed()));
        appCompatEditText10.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.edit_button_update), withText("更新"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        23),
                                0)));
        appCompatButton2.perform(scrollTo(), click());


        // -------編集したデータをリストで確認-------------------------------
        ViewInteraction textView14 = onView(
                allOf(withId(R.id.list_diving_number), withText("222"),
                        withParent(allOf(withId(R.id.list_log_item),
                                withParent(withId(R.id.list_view_log)))),
                        isDisplayed()));
        textView14.check(matches(withText("222")));

        ViewInteraction textView15 = onView(
                allOf(withId(R.id.list_place), withText("ccc"),
                        withParent(allOf(withId(R.id.list_log_item),
                                withParent(withId(R.id.list_view_log)))),
                        isDisplayed()));
        textView15.check(matches(withText("ccc")));

        ViewInteraction textView16 = onView(
                allOf(withId(R.id.list_point), withText("ddd"),
                        withParent(allOf(withId(R.id.list_log_item),
                                withParent(withId(R.id.list_view_log)))),
                        isDisplayed()));
        textView16.check(matches(withText("ddd")));

        // -------詳細画面でデータを確認-------------------------------
        DataInteraction constraintLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.list_view_log),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)))
                .atPosition(0);
        constraintLayout.perform(click());

        ViewInteraction textView17 = onView(
                allOf(withId(R.id.det_dive_number), withText("222"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView17.check(matches(withText("222")));

        ViewInteraction textView18 = onView(
                allOf(withId(R.id.det_place), withText("ccc"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView18.check(matches(withText("ccc")));

        ViewInteraction textView19 = onView(
                allOf(withId(R.id.det_point), withText("ddd"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView19.check(matches(withText("ddd")));

        ViewInteraction textView20 = onView(
                allOf(withText("本数"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView20.check(matches(withText("本数")));

        ViewInteraction textView21 = onView(
                allOf(withText("場所"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView21.check(matches(withText("場所")));

        ViewInteraction textView22 = onView(
                allOf(withText("日付"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView22.check(matches(withText("日付")));

        ViewInteraction textView23 = onView(
                allOf(withText("潜水時間"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView23.check(matches(withText("潜水時間")));

        ViewInteraction textView24 = onView(
                allOf(withText("水深 ( m )"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView24.check(matches(withText("水深 ( m )")));

        ViewInteraction textView25 = onView(
                allOf(withText("残圧 ( bar )"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView25.check(matches(withText("残圧 ( bar )")));

        ViewInteraction textView26 = onView(
                allOf(withText("コンディション"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView26.check(matches(withText("コンディション")));

        ViewInteraction textView27 = onView(
                allOf(withText("メンバー"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView27.check(matches(withText("メンバー")));

        ViewInteraction textView28 = onView(
                allOf(withText("メンバー"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView28.check(matches(withText("メンバー")));

        ViewInteraction textView29 = onView(
                allOf(withText("メモ"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView29.check(matches(withText("メモ")));

        ViewInteraction textView30 = onView(
                allOf(withText("写真"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView30.check(matches(withText("写真")));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("前に戻る"),
                        childAtPosition(
                                allOf(withId(R.id.action_bar),
                                        childAtPosition(
                                                withId(R.id.action_bar_container),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
