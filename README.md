## Android 上下文菜单：给ListView注册ContextMenu后获取被长按item的position ##
Android中的ContextMenu又称上下文菜单，当长按某个View时，调出上下文菜单。
在Android中，还有一类菜单——OptionsMenu，可以称为选项菜单。与ContextMenu不同的是，OptionsMenu是属于一个Activity的，而ContextMenu是和某个View绑定的，针对的是这个View。

实现上下文菜单
-------

要实现一个上下文菜单，需要经过以下三个步骤：

1、注册上下文菜单

这里给ListView注册了上下文菜单。
```
this.listView = (ListView) findViewById(R.id.listView);
this.registerForContextMenu(listView);
```

2、生成上下文菜单

重写Activity或者Fragment中的onCreateContextMenu方法，生成上下文菜单。
```
@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v == listView) {
            menu.setHeaderIcon(R.mipmap.ic_launcher);
            menu.setHeaderTitle("请选择：");
            menu.add(0, 0, 0, "获取此item的position");
            menu.add(0, 1, 0, "取消");
        }
    }
```


3、菜单事件监听

 重写Activity或者Fragment中的onContextItemSelected方法，根据ItemId来判断当前选中的是哪个item，然后做对应处理。

```
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this, "menuInfo.position:" + menuInfo.position, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
```


----------

获取Listview被选中项的信息
-----------------------------------------

回到本文重点，在给ListView中的每一项都添加上下文菜单后，长按ListView的某个item，就会弹出上下文菜单。我们有时候需要对这个item进行如删除、修改等操作，这时候就需要知道我们点击的是哪个item，即在onContextItemSelected方法中获取到点击item的position。

效果图如下图所示

![获取到所点击item的position](https://github.com/JZHowe/MenuDemo/blob/master/gif/ContextMenu.gif)

怎么实现这个操作呢？先来看下代码：

首先是布局，这里只添加了一个ListView，其数据来源是在strings.xml文件中，在这里直接引用了。

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <ListView
        android:id="@+id/listView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:entries="@array/listView"
        />

</LinearLayout>
```
string.xml文件内容：

```
<resources>
    <string name="app_name">MenuDemo</string>

    <array name="listView">
        <item>故事的小黄花 </item>
        <item>从出生那年就飘着 </item>
        <item>童年的荡秋千 </item>
        <item>随记忆一直晃到现在 </item>
        <item>rui sou sou xi dou xi la </item>
        <item>sou la xi xi xi xi la xi la sou </item>
        <item>吹着前奏望着天空 </item>
        <item>我想起花瓣试着掉落 </item>
        <item>为你翘课的那一天 </item>
        <item>花落的那一天 </item>
        <item>教室的那一间 </item>
        <item>我怎么看不见 </item>
        <item>消失的下雨天 </item>
        <item>我好想再淋一遍 </item>
        <item>没想到失去的勇气我还留着 </item>
        <item>好想再问一遍 </item>
        <item>你会等待还是离开 </item>
    </array>
</resources>
```

下面是给ListView注册上下文菜单以及获取到所点击item的position相关代码。

```
public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listView = (ListView) findViewById(R.id.listView);

        //给listView注册上下文菜单ContextMenu
        this.registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //根据View生成对应的菜单
        if (v == listView) {
            //设置菜单图标和标题
            menu.setHeaderIcon(R.mipmap.ic_launcher);
            menu.setHeaderTitle("请选择：");
            //添加菜单项
            menu.add(0, 0, 0, "获取此item的position");
            menu.add(0, 1, 0, "取消");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //关键代码
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 0:
                //输出当前点击的item的position
                Toast.makeText(this, "menuInfo.position:" + menuInfo.position, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销listView上的ContextMenu
        unregisterForContextMenu(listView);
    }
}

```
在onContextItemSelected方法中，通过**AdapterContextMenuInfo** 的实例，我们就可以得到当前所点击item的position、id等信息了。
