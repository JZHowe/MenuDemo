package com.jju.howe.menudemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
