package org.schulcloud.mobile;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.schulcloud.mobile.data.model.CurrentUser;
import org.schulcloud.mobile.data.model.User;
import org.schulcloud.mobile.test.common.TestComponentRule;
import org.schulcloud.mobile.test.common.TestDataFactory;
import org.schulcloud.mobile.ui.main.MainActivity;

import java.util.List;

import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<MainActivity> main =
            new ActivityTestRule<MainActivity>(MainActivity.class, false, false) {
                @Override
                protected Intent getActivityIntent() {
                    // Override the default intent so we pass a false flag for syncing so it doesn't
                    // start a sync service in the background that would affect  the behaviour of
                    // this test.
                    return MainActivity.getStartIntent(
                            InstrumentationRegistry.getTargetContext(), false);
                }
            };

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public final TestRule chain = RuleChain.outerRule(component).around(main);

    @Test
    @Ignore("This test will be ignored")
    public void listOfUsersShows() {
        final String FAKE_USER_ID = "fakeUserId";
        List<User> testDataUsers = TestDataFactory.makeListUsers(20);
        when(component.getMockDataManager().getUsers())
                .thenReturn(Observable.just(testDataUsers));
        when(component.getMockDataManager().getCurrentUserId())
                .thenReturn(FAKE_USER_ID);
        when(component.getMockDataManager().syncCurrentUser(FAKE_USER_ID))
                .thenReturn(Observable.just(new CurrentUser()));

        main.launchActivity(null);

        int position = 0;
        for (User user : testDataUsers) {
            onView(withId(R.id.recycler_view))
                    .perform(RecyclerViewActions.scrollToPosition(position));
            String name = String.format("%s %s", user.getLastName(),
                    user.getFirstName());
            onView(withText(name))
                    .check(matches(isDisplayed()));
            onView(withText(user.getEmail()))
                    .check(matches(isDisplayed()));
            position++;
        }
    }

}