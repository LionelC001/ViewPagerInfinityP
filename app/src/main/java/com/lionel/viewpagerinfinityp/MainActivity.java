package com.lionel.viewpagerinfinityp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPagerInfinity viewPagerInfinity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViewPager();
    }

    private void initViewPager() {
        List<String> data = new ArrayList<>();
        data.add("https://i.kinja-img.com/gawker-media/image/upload/s--9bGhMLte--/c_scale,f_auto,fl_progressive,q_80,w_800/yvgvuhqazyrdbdcqrfkr.jpg");
        data.add("https://pbs.twimg.com/media/D9WrmU6XYAE7viN.jpg");
        data.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSl332x2YeKEqdLpdYtGMsz6OovU2utAag02Mqdwi1ddyp7Xn97");
        data.add("https://images-na.ssl-images-amazon.com/images/I/81MSTWBZr6L.jpg");
        data.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQFL1_InrDUsDvj8JzjJDDF6PiUBNAoi20f7SVNujrjUEhv1a_Txg");


        viewPagerInfinity = findViewById(R.id.viewPagerInfinity);
        viewPagerInfinity.showData(data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(viewPagerInfinity!=null) {
            viewPagerInfinity.onDestroy();
        }
    }
}
