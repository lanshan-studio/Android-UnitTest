package com.example.zane.daggerjunittest.robolectric.robolectric;

import android.content.Intent;

import com.example.zane.daggerjunittest.BuildConfig;
import com.example.zane.daggerjunittest.R;
import com.example.zane.daggerjunittest.robolectric.ActivityOne;
import com.example.zane.daggerjunittest.robolectric.ActivityTwo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

/**
 * Created by Zane on 16/9/8.
 * Email: zanebot96@gmail.com
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ActivityOneTest{
    @Test
    public void testMainActivity() throws Exception{
        //通过robolectric来启动activity
        ActivityOne activityOne = Robolectric.setupActivity(ActivityOne.class);
        activityOne.findViewById(R.id.text_activity_one).performClick();

        //获取robolectric用来代替安卓类的ShadowXXXX
        ShadowActivity shadowActivityOne = Shadows.shadowOf(activityOne);
        //期望的Intent
        Intent expectIntent = new Intent(activityOne, ActivityTwo.class);
        //模拟出来的Intent
        Intent actualIntent = shadowActivityOne.getNextStartedActivity();
        Assert.assertEquals(expectIntent, actualIntent);
    }
}
