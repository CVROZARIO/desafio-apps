package com.caiovictor.android.apps.infoglobo;

import android.content.Context;

import com.caiovictor.android.apps.infoglobo.util.Commons;
import com.caiovictor.android.apps.infoglobo.view.MainActivity;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CommonsTests {

    @Test
    public void tryParseDate() {
        assertNotNull(Commons.getDateFromString("2017-03-08T13:25:03-0300"));
        assertNull(Commons.getDateFromString("03-2017-08T13:25:03-0300"));
        assertNull(Commons.getDateFromString("2017-03-08X13:25:03-0300"));
    }

    @Test
    public void tryHumanElapsedTimeFrom(){
        assertFalse(Commons.getHumanElapsedTimeFrom(ApplicationProvider.getApplicationContext(), new Date()).equals(""));
    }

}
