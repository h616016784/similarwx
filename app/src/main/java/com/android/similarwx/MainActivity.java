package com.android.similarwx;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.outbaselibrary.utils.LogUtil;
import com.android.similarwx.base.AppApplication;
import com.android.similarwx.beans.DbUser;
import com.android.similarwx.beans.User;
import com.android.similarwx.greendaodemo.gen.DaoSession;
import com.android.similarwx.utils.netmodle.HttpUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.button)
    public void onViewClicked() {
//        User user=new User();
//        user.setName("username");
//        user.setToken("token");
//        user.setPhone(123456);
//        HttpUtil.getInstance().getServiceHandler().login(user).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//
//            }
//        });
//        Glide.with(this)
//                        .load("http://goo.gl/gEgYUd")
//                        .into(imageView);
//                Gson gson=new Gson();
//                String a =gson.toJson(obj);
//                String[] strings = {"abc", "def", "ghi"};
//                String arrs=gson.toJson(strings);
//                int[] ints2 = gson.fromJson("[1,2,3,4,5]", int[].class);
//                Logger.d(ints2);
//        DbUser dbUser=new DbUser();
//        dbUser.setName("test3");
//       DaoSession userDao=AppApplication.getInstance().getDaoSession();
//
//       userDao.insert(dbUser);
//       List<DbUser> list =userDao.queryBuilder(DbUser.class).list();
//       for (DbUser dbUser1:list){
//           LogUtil.d(dbUser1);
//       }
//        userDao.clear();
    }
}
