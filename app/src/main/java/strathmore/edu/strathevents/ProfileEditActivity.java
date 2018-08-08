package strathmore.edu.strathevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static strathmore.edu.strathevents.SharedPrefManager.SHARED_PREF_NAME;

public class ProfileEditActivity extends AppCompatActivity {

    EditText username;
    EditText email;

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


        username =  findViewById(R.id.username);
        email =  findViewById(R.id.email);

        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews

        username.setText(user.getUsername());
        email.setText(user.getEmail());

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("username"),
                                        userJson.getString("email")
//                                        userJson.getString(password)
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username.getText().toString());
                params.put("email", email.getText().toString());
                params.put("id", String.valueOf(new SharedPrefManager(ProfileEditActivity.this).getUser().getId()));
//                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
//        startActivity(new Intent(ProfileEditActivity.this, LoginActivity.class));
    }

}


