## 单元测试小总结

​	单元测试往往在产品赶着上线的情况下被忽视。然后单元测试往往会节约大量修改bug的时间。还有一点，单元测试能够验证你的代码的结构是否具有松耦性，因为高度耦合的代码是难以做单元测试的。所以代码中经常通过Dagger来做单元测试。另一点，可以发现老外的项目基本上每个项目都做过不同程度的单元测试，不论项目是大是小，然而中国人好像比较忽视这一点，从现在AS一打开都会给你建测试包都可以看出来老外是很重视单元测试的。

### 单元测试是什么

​	不关有单元测试，还有UI测试，集成测试等等。单元测试简单的来说就是来验证某个类里面的某个方法能不能传入一个参数然后返回一个我们预期的值，如果没有返回值就去验证函数中代码能不能正常按预期跑一遍。实际上是如何测试是个非常灵活的事情。常用的单元测试框架有：JUnit，Mockito，Robolectric，DaggerMock等等

### 最简单的示例

```java
public class Add {
  public int sum(int num1, int num2) {
  return num1 + num2;
}
}
```

​	对上述代码进行单元测试只需要最基本的JUnit框架就够了，没错，就是AS给你自动引入的JUnit。

```java
public class addTest {
  @Test
  public void testAdd() throws Exception{
	  	Add add = new Add();
    assertEquals(3, add.sum(1, 2));
}
}
```

​	然后将直接control+sheft+R运行这个测试方法。看上面的代码当然是可以通过，所以控制台会直接通过，如果将预期值改为4，那么就会报错。

​	所以单元测试大概可以看成三部分：

+ 初始化对象
+ 调用
+ 验证

​	也许这样的代码让你觉得单元测试毫无意义，但是在实际项目中，复杂的逻辑没有这么好写单元测试。首先MVP这样的模块化解耦构建的项目会对单元测试代码的书写轻松很多。总之：**松耦合**

​	**JUnit**还有很有的小使用技巧：比如@Before，@After，@Beforeclass，@Afterclass，fail（），assertXXXXXX（）等等，可以在我最下面的推荐的文章里面去深入学习。这里我们只做大概的介绍和总结。

### 用Mockito去做一些不可思议的事情

​	Mockito中有一个类很重要叫**Mock**。不知道听说过Mock数据没有，就是用自己模拟的数据去代替真实的数据，实际开发中，移动端和后台定义好json字段之后，移动端可以先用Mock数据代替真实数据，和后台一起进行开发。这里的Mock类一样道理，就是在单元测试中去模拟一个实例，然后代替需要被测试的代码中真实的对象。然后通过Mock出来的对象替换被测试代码中的对象，再使用Mockito进行验证对象的函数是否成功被调用。示例如下：

```java
public class LoginPresenter {
    private UserManager mUserManager = new UserManager();
    public void login(String username, String password) {
        if (username == null || username.length() == 0) return;
        if (password == null || password.length() < 6) return;
        mUserManager.performLogin(username, password);
    }
  
   public void setUserManager(UserManager userManager) {
        this.mUserManager = userManager;
    }
}
```

```java
public class LoginPresenterTest{
  @Test
public void testLogin() throws Exception {
    UserManager mockUserManager = Mockito.mock(UserManager.class);
    LoginPresenter loginPresenter = new LoginPresenter();
    loginPresenter.setUserManager(mockUserManager); 

    loginPresenter.login("xiaochuang", "xiaochuang password");

    Mockito.verify(mockUserManager).performLogin("xiaochuang", "xiaochuang password");
}
}
```

大概步骤如下：

+ Mock对象出来
+ 将对象手动替换到被测试代码中
+ 验证Mock对象的函数是否被成功调用

也许你发现了，这样必须要给被测试类写一个setter方法，这是极度。。不优雅的。所以！所以！我们引出了Dagger来给我们将Mock对象注入到被测试类～～（激动）

**Mockito**的功能大概如下：

+ 更改Mock对象的函数行为
+ 验证Mock对象的函数是否被调用

具体的，，使用方法当然不再多说。

### 通过Robolectrie在JVM上直接加载Android类

​	单元测试方便的就是不需要跑一遍项目，不需要打一堆Log，不需要balabala~，但是Android类是不能直接跑在JVM上的，只能在手机上的虚拟机上（ART）。所以如果单元测试中涉及到Android类，就通过Robolectrio来解决。我感觉他的思路就是类似动态代理一样的在代码跑的过程中用他自己的类来替换Android类。比如Activity就对应了ShadoActivity类。并且这些代理类还提供了更多的接口让开发者能够更方便的获得对象的状态。

Ps：Robolectrie对于as插件的版本要求比较坑，我遇到一个版本bug，弄了一下午。。。

### 通过Dagger来优雅的进行依赖注入

​	**Dagger**的使用自然我不会在这里多说。我说说单元测试是如何使用Dagger的：

```java
@Module
public class LoginTestModule {

    private Context context;

    public LoginTestModule(Context context){
        this.context = context;
    }

    @Provides
    public Context provideContext(){
        return context;
    }

    @Provides
    public PasswordValidator providePasswordValidator(){
        return new PasswordValidator();
    }

    @Provides
    public SharedPreferences provideSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    public ApiService provideApiService(){
        return new ApiService();
    }

    @Provides
    public UsersManager provideUserManager(SharedPreferences sp, ApiService service){
        return new UsersManager(sp, service);
    }

    @Provides
    public LoginPresenter provideLoginPresenter(UsersManager manager, PasswordValidator passwordValidator){
        return new LoginPresenter(manager, passwordValidator);
    }

}
```

```java
@Component(modules = {LoginTestModule.class})
public interface LoginTestComponent {
    void inject(MainActivity activity);
}
```

```java
public class MainActivity extends AppCompatActivity {

    @Inject
    LoginPresenter loginPresenter;

    private EditText mUsername;
    private EditText mPassword;
    private Button mButton;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //通过一个holder来传入mock出来的mocule
        LoginComponentHolder.getComponent().inject(this);

        mUsername = (EditText) findViewById(R.id.edit_name);
        mPassword = (EditText) findViewById(R.id.edit_password);
        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(v -> {
            String username = mUsername.getText().toString();
            String password = mPassword.getText().toString();
            loginPresenter.login(username, password);
        });
    }
}
```

```java
public class LoginComponentHolder {

    private static LoginTestComponent component;

    public static void setComponent(LoginTestComponent component2){
        component = component2;
    }

    public static LoginTestComponent getComponent(){
        return component;
    }
}
```

上述代码中要测试的是Button点击之后presenter的login（）方法会被成功调用。loginPresenter是通过项目中使用Dagger简单注入进去的（**注意是通过Field Injection inject进去的**），并且我们用一个ComponentHolder来维护这个Module对应的Component。测试代码如下：

```java
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LoginPresenterTest {

    private LoginTestModule mockModule;

    @Before
    public void setup(){
        //不能调用mock()因为dagger规定module的provide方法不能返回null
        mockModule = Mockito.spy(new LoginTestModule(RuntimeEnvironment.application));
    }

    @Test
    public void testMainActivityLogin(){
      
      	LoginPresenter loginPresenter = Mock(LoginPresenter.class);

        //开始更改mockModule的方法行为,返回mock的loginPresenter到mockModule的dependency中去
        Mockito.when(mockModule.provideLoginPresenter(Mockito.any(UsersManager.class),
                Mockito.any(PasswordValidator.class))).thenReturn(loginPresenter);

        //将这个module生成的component放到容器中去
        LoginTestComponent component = DaggerLoginTestComponent.builder()
                .loginTestModule(mockModule)
                .build();
        LoginComponentHolder.setComponent(component);

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
```

​	大致的思路其实就是Mock对应的Module，然后通过Mockito来更改provide方法的行为（返回一个Mock对象，更改Module的Dependency），然后将Mock的Module（更改了Dependency行为）注入到ComponentHolder中。最终MainActivity中inject的时候，就会被注入一个Mock对象。

​	首先强调一点，不应该为了使用Dagger而使用Dagger。嗯，就是如果能不用Dagger，那就尽量不用。首先上面我强调了LoginPresenter是通过Field Inject的方式注入进去的，一般通过这种方式注入进去的，很难不通过更改Dagger依赖图去改变注入的依赖对象。但是如果LoginPresenter是通过Constructor Inject注入到依赖图的话，完全可以调用构造函数new一个对象出来，构造函数的参数都可以直接传进去，所以不用费大力气去修改依赖图了。

### 使用DaggerMock来简化代码

​	如果觉得上述通过Dagger来做单元测试代码还是很复杂，可以使用黑科技DaggerMock来简化你的代码。在使用这个框架之前必须学习一下JUnit的Rule。

```java
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
```

就靠这些模版代码就可以轻松的将loginPresenter注入到被测试代码中。

+ 通过JUnit的Rule注解来修饰DaggerMockRule，然后告诉DaggerMock你想怎么build Component，使用什么Module，Component build了之后放到哪里。
+ 通过Mock注解修饰你需要修改的依赖对象
+ 框架会反射，再遍历Module中所有被@Provider修饰的函数，如果发现返回的对象类型是@Mock修饰的对象类型，那么就会进行Mock替换。

### 异步代码的单元测试

​	对于异步的单元测试，有两种解决办法

+ 等待异步执行完，回调之后在进行比较
+ 将异步过程在测试代码中转换成同步过程

异步的实现手段太多了，线程池，AsyncTask，**RxJava**等等....

第一种方式我们可以通过CountDownLatch来通知异步完成，还可以在Callback中直接测试。这种方式适用于所有拥有Callback形式的异步操作。

第二种方式，比如线程池，我们可以通过依赖注入一个同步的线程池来解决，Rxjava可以使用上述方式，因为Subscriber实际上是一个Callback，可以通过自定义调度插件（RxjavaScheduleHook）来覆盖默认的线程调度方式。

参考代码：[AndroidUnitTest](https://github.com/lanshan-studio/Android-UnitTest)

参考文章：[小创的系列教程](http://chriszou.com/2016/07/24/android-unit-testing-daggermock.html)(作者是蘑菇街的Android开发工程师。敬佩，感谢写出如此优秀的单元测试教学系列文章)
