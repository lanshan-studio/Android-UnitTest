package com.example.zane.daggerjunittest.robolectric;

import android.widget.Button;
import android.widget.EditText;

import com.example.zane.daggerjunittest.BuildConfig;
import com.example.zane.daggerjunittest.LoginComponentHolder;
import com.example.zane.daggerjunittest.LoginPresenter;
import com.example.zane.daggerjunittest.MainActivity;
import com.example.zane.daggerjunittest.PasswordValidator;
import com.example.zane.daggerjunittest.R;
import com.example.zane.daggerjunittest.UsersManager;
import com.example.zane.daggerjunittest.inject.DaggerLoginTestComponent;
import com.example.zane.daggerjunittest.inject.LoginTestComponent;
import com.example.zane.daggerjunittest.inject.LoginTestModule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import it.cosenonjaviste.daggermock.DaggerMockRule;

/**
 * Created by Zane on 16/9/17.
 * Email: zanebot96@gmail.com
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LoginPresenterTest {

    private LoginTestModule mockModule;

    @Rule
    public DaggerMockRule daggerMockRule = new DaggerMockRule(LoginTestComponent.class, new LoginTestModule(RuntimeEnvironment.application))
            .set(new DaggerMockRule.ComponentSetter() {
                @Override
                public void setComponent(Object o) {
                    LoginComponentHolder.setComponent((LoginTestComponent) o);
                }
            });
    @Mock
    LoginPresenter loginPresenter;

    @Before
    public void setup(){
        //不能调用mock()因为dagger规定module的provide方法不能返回null
        mockModule = Mockito.spy(new LoginTestModule(RuntimeEnvironment.application));
    }

    @Test
    public void testMainActivityLogin(){

//        //开始更改mockModule的方法行为,返回mock的loginPresenter到mockModule的dependency中去
//        Mockito.when(mockModule.provideLoginPresenter(Mockito.any(UsersManager.class),
//                Mockito.any(PasswordValidator.class))).thenReturn(loginPresenter);
//
//        //将这个module生成的component放到容器中去
//        LoginTestComponent component = DaggerLoginTestComponent.builder()
//                .loginTestModule(mockModule)
//                .build();
//        LoginComponentHolder.setComponent(component);

        //通过Robolectric生成mock MainActivity
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        ((EditText)mainActivity.findViewById(R.id.edit_name)).setText("zane");
        ((EditText)mainActivity.findViewById(R.id.edit_password)).setText("123");
        Button mButton = (Button) mainActivity.findViewById(R.id.button);
        mButton.performClick();

        //验证
        Mockito.verify(loginPresenter).login("zane", "123");
    }

}
