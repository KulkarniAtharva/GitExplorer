package dev.atharvakulkarni.gitexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class splash_screen extends AppCompatActivity
{
    int DELAY_TIME_IN_MILIS = 2000;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        textView = findViewById(R.id.text_git_command);

        Spanned sp = Html.fromHtml(getString(R.string.git_command_explorer),1);

        textView.setText(sp);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(splash_screen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },DELAY_TIME_IN_MILIS);
    }
}
