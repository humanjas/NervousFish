package com.nervousfish.nervousfish.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.nervousfish.nervousfish.*;
import com.nervousfish.nervousfish.R;
import com.nervousfish.nervousfish.service_locator.EntryActivity;
import com.robotium.solo.Solo;

import org.hamcrest.Matcher;

import cucumber.api.CucumberOptions;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@CucumberOptions(features = "features")
public class RobotiumTestExample extends ActivityInstrumentationTestCase2<EntryActivity> {

    private Solo solo;

    public RobotiumTestExample(EntryActivity activityClass) {
        super(EntryActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Before
    public void before() {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @After
    public void after() throws Throwable {
        //clean up
        solo.finalize();
    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    @When("^I input password \"(.*?)\" and click submit$")
    public void iInputPasswordAndSubmit(final String password) {
        assertTrue(solo.searchText("Enter your password below"));
        solo.clickOnEditText(R.id.password);
        solo.enterText(R.id.password, password);
        solo.clickOnButton("Submit");
    }

    @Then("^I (true|false) continue to another activity$")
    public void iShouldContinueToNextActivity(boolean continuesToNextActivity) {
        if (continuesToNextActivity) {
            assertFalse(solo.searchText("Enter your password below"));
        } else {
            assertTrue(solo.searchText("Enter your password below"));
        }
    }
}
