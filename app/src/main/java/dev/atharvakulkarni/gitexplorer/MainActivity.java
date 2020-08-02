package dev.atharvakulkarni.gitexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.text_git_command);

        Spanned sp = Html.fromHtml(getResources().getString(R.string.git_command_explorer),1);

        title.setText(sp);
    }
}