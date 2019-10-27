package ru.world18.a18worldlauncher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    User user_inf;
    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSettings = getSharedPreferences("world18", 0);
        static_vars.activity=this;
        user_inf=new User();
        static_vars.user=user_inf;
        if(!mSettings.contains("login")) {
            if(user_inf.status=="created"){
                loadLoadingPanel();
            }
        }else{
            user_inf.login=mSettings.getString("login", "");
            user_inf.password=mSettings.getString("password", "");
            user_inf.sess_id=mSettings.getString("sess_id", "");
            cteateMain();

        }
    }
    protected  void cteateMain(){
        setContentView(R.layout.game);
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.linearLayout);
        tabSpec.setIndicator("Игра");
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.linearLayout2);
        tabSpec.setIndicator("Poke-book");
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.linearLayout3);
        tabSpec.setIndicator("Форум");
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec("tag4");
        tabSpec.setContent(R.id.linearLayout4);
        tabSpec.setIndicator("Инфо");
        tabHost.addTab(tabSpec);
        tabHost.setCurrentTab(0);

        WebView gameWebView = (WebView) findViewById(R.id.game);
        MyWebViewClient.overrideSetWevClient(gameWebView);
        gameWebView.loadUrl("http://18world.ru/mobile/indexn.php?sess_id="+static_vars.user.sess_id);

        WebView pbWebView = (WebView) findViewById(R.id.wikip);
        MyWebViewClient.overrideSetWevClient(pbWebView);
        pbWebView.loadUrl("http://18world.ru/wiki");

        WebView forumWebView = (WebView) findViewById(R.id.forum);
        MyWebViewClient.overrideSetWevClient(forumWebView);
        forumWebView.loadUrl("http://world18.forum2x2.com/");

        Button exitB = (Button) findViewById(R.id.exit);
        exitB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                user_inf = user_inf.exit();
                static_vars.user=user_inf;
                setContentView(R.layout.activity_login);
                loadLoadingPanel();
            }
        });
    }
    protected void loadLoadingPanel(){
        /*EditText login = (EditText) findViewById(R.id.login);
        login.setText("testing");
        EditText password = (EditText) findViewById(R.id.password);
        password.setText("123456");*/
        Button loginB = (Button) findViewById(R.id.mmLogin);
        loginB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView welcomeText = (TextView) findViewById(R.id.welcomeText);
                try {
                    EditText login = (EditText) findViewById(R.id.login);
                    user_inf.login=login.getText().toString();
                    EditText password = (EditText) findViewById(R.id.password);
                    user_inf.password=password.getText().toString();
                    new GetUrlContentTask().execute("http://18world.ru/api/connector.php");
                }
                catch(Exception e){
                    welcomeText.setText(e.getMessage());
                }
            }
        });
    }
}
