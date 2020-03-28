package com.c.ctgapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.navigation.ui.NavigationUI.onNavDestinationSelected;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.nav_view)
    BottomNavigationView navView;//底部菜单栏
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;//标题
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.left_tv)
    TextView left_tv;
    @BindView(R.id.right_tv)
    TextView right_tv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar_title.setText(R.string.title_home);
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                       toolbar_title.setText(item.getTitle());

                           switch (item.getItemId()){
                               case R.id.navigation_home:
                                   left_tv.setVisibility(View.GONE);
                                   right_tv.setVisibility(View.GONE);
                                   toolbar.setVisibility(View.VISIBLE);
                                   break;

                               case R.id.navigation_news:
                                   left_tv.setVisibility(View.VISIBLE);
                                   right_tv.setVisibility(View.VISIBLE);
                                   left_tv.setText("我的商友");
                                   right_tv.setText("添加");
                                   toolbar.setVisibility(View.VISIBLE);
                                   break;
                               case R.id.navigation_work:
                                   left_tv.setVisibility(View.GONE);
                                   right_tv.setVisibility(View.GONE);
                                   toolbar.setVisibility(View.VISIBLE);
                                   break;
                               case R.id.navigation_transaction:
                                   left_tv.setVisibility(View.GONE);
                                   right_tv.setVisibility(View.GONE);
                                   toolbar.setVisibility(View.VISIBLE);
                                   break;

                               case R.id.navigation_my:
                                   toolbar.setVisibility(View.GONE);
                                   left_tv.setVisibility(View.GONE);
                                   right_tv.setVisibility(View.GONE);
                                   break;
                           }





                        return onNavDestinationSelected(item, navController);
                    }
                });





 /*       navView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            //二次点击触发动作
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

            }
        });*/
    }


}
