package me.kingjason.waldoproject;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith( AndroidJUnit4.class )
public class FileIOInstrumentationTest
{
    @Test
    public void testNoFile() throws Exception
    {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        String file = Utils.loadStringFromFile( "not_a_file.txt", appContext );

        assertNull( file );
    }
    @Test
    public void testFileNotNull() throws Exception
    {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        String file = Utils.loadStringFromFile( "album.json", appContext );

        assertNotNull( file );
    }
}
