package com.nervousfish.nervousfish.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.nervousfish.nervousfish.service_locator.EntryActivity;
import com.robotium.solo.Solo;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;

public class test extends ActivityInstrumentationTestCase2<EntryActivity> {

    private Solo solo;

    public test(EntryActivity activityClass) {
        super(EntryActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testLoggingIn() throws Exception {

        assertTrue(solo.searchText("Enter your password below"));
        solo.clickOnEditText(com.nervousfish.nervousfish.R.id.password);
        solo.enterText(2, "12345");
        solo.clickOnButton("Submit");
        assertFalse(solo.searchText("Enter your password below"));
    }
}
