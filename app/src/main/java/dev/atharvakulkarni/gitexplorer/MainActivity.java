package dev.atharvakulkarni.gitexplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    TextView title,display_git_command,display_note,text_note;
    CardView cardview_secondary,cardview_note,cardview_usage;
    ArrayList<String> input_secondary;
    String primary_selected,secondary_selected,usage,note;
    ArrayList<String> primary_list_label = new ArrayList<String>();
    ArrayList<String> primary_list_value = new ArrayList<String>();
    ArrayList<String> secondary_list = new ArrayList<String>();
    JSONObject primaryDetail;
    JSONArray primarySeleted_Array;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.text_git_command);
        cardview_secondary = findViewById(R.id.card_view_second_field);
       // cardview_usage  = findViewById(R.id.card_view_usage);
        cardview_note = findViewById(R.id.card_view_note);

        display_git_command  = findViewById(R.id.text_display_git_command);
        display_note = findViewById(R.id.text_display_note);
        text_note = findViewById(R.id.text_note);

        primary_list_label.add("...");
        primary_list_value.add("...");
        secondary_list.add("...");

        Spanned sp = Html.fromHtml(getResources().getString(R.string.git_command_explorer),1);

        title.setText(sp);

        Spinner dropdown1 = findViewById(R.id.spinner1);
        final Spinner dropdown2 = findViewById(R.id.spinner2);

       // ArrayList<String> input_primary = getPrimaryOptions();

        getPrimaryOptions();

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, primary_list_label);
        dropdown1.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, secondary_list);
        dropdown2.setAdapter(adapter2);

                dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                    {
                        if(position > 0)
                        {
                            cardview_secondary.setVisibility(View.VISIBLE);
                            primary_selected = primary_list_value.get(position);
                            dropdown2.setSelection(0);
                            secondary_list.clear();
                            getSecondaryOptions();


                           // cardview_usage.setVisibility(View.VISIBLE);
                            //display_git_command.setVisibility(View.GONE);

                        }
                        cardview_note.setVisibility(View.GONE);
                        text_note.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView)
                    {
                    }
                });

        dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                if(position > 0)
                {
                   // secondary_selected = secondary_list.get(position);

                    try
                    {
                        primaryDetail = primarySeleted_Array.getJSONObject(position-1);

                        if (primaryDetail.has("usage"))
                            usage = primaryDetail.getString("usage");
                        if (primaryDetail.has("nb"))
                            note = primaryDetail.getString("nb");

                        if(primaryDetail.has("nb"))
                        {
                            cardview_note.setVisibility(View.VISIBLE);
                            text_note.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            cardview_note.setVisibility(View.GONE);
                            text_note.setVisibility(View.GONE);
                        }
                    }
                    catch(JSONException e)
                    {
                        e.printStackTrace();
                    }

                    display_git_command.setVisibility(View.VISIBLE);
                    display_git_command.setText(usage);
                    display_note.setText(note);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {
            }
        });
    }

    private void getPrimaryOptions()
    {
        try
        {
            JSONObject obj = new JSONObject(loadJSONFromAsset());   // get JSONObject from JSON file
            JSONArray userArray = obj.getJSONArray("primary_options");    // fetch JSONArray named users

            for (int i = 0; i < userArray.length(); i++)            // implement for loop for getting users list data
            {
                JSONObject userDetail = userArray.getJSONObject(i);      // create a JSONObject for fetching single user data
                primary_list_label.add(userDetail.getString("label"));
                primary_list_value.add(userDetail.getString("value"));
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void getSecondaryOptions()
    {
        secondary_list.add("...");
        try
        {
            JSONObject obj = new JSONObject(loadJSONFromAsset());   // get JSONObject from JSON file
            JSONObject secondaryObject = obj.getJSONObject("secondary_options");  // fetch JSONObject named secondary options
            primarySeleted_Array = secondaryObject.getJSONArray(primary_selected);

            for(int i = 0; i < primarySeleted_Array.length(); i++)            // implement for loop for getting users list data
            {
                primaryDetail = primarySeleted_Array.getJSONObject(i);      // create a JSONObject for fetching single user data
                secondary_list.add(primaryDetail.getString("label"));
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset()
    {
        String json = null;
        try
        {
            InputStream is = getAssets().open("git_command_explorer.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}

/*
    <AutoCompleteTextView
                android:id="@+id/input_first_field"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@null"
                android:completionThreshold="1"
                android:drawableEnd="@drawable/arrow_downwards"
                android:drawableTint="#ACACAC"
                android:fontFamily="@font/calibri"
                android:hint="..."
                android:padding="12dp"
                android:inputType="textNoSuggestions"
                android:textColor="@color/colorPrimary"
                android:textSize="14sp"
                android:textStyle="bold"
                tools:text="add" />


 */