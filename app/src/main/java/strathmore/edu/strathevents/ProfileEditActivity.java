package strathmore.edu.strathevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ProfileEditActivity extends AppCompatActivity {

    EditText textViewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        //if the user is not logged in
        //starting the login activity
        if (!strathmore.edu.strathevents.SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        textViewUsername =  findViewById(R.id.textViewUsername);

        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews

        textViewUsername.setText(user.getUsername());

        //when the user presses edit button
        //calling the edit method
        findViewById(R.id.buttonEditprofile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editprofile();
            }
        });
    }

    //this method will edit profile
    public void editprofile() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
}


