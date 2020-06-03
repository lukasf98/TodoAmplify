package com.mobileappdevelopment.todoamplify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.AuthUser
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin

class ShowLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_login)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Login Demo"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
        // Get Text Boxes
        val userName: EditText = findViewById(R.id.user_name)
        val password: EditText = findViewById(R.id.password)
        // Set up Buttons
        val loginButton: Button = findViewById(R.id.log_in)
        val logoutButton: Button = findViewById(R.id.log_out)
        val signupButton: Button = findViewById(R.id.sign_up)
        var confirmSignupButton: Button = findViewById(R.id.confirm_signup)
        // Click Listener
        loginButton.setOnClickListener {
            Amplify.Auth.signIn(
                userName.text.toString(),
                password.text.toString(),
                { result -> // Sign in success, update UI with the signed-in user's information
                    val user = Amplify.Auth.currentUser
                    updateUI(user) },
                { error -> // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null) }
            )
        }

        logoutButton.setOnClickListener{
            Amplify.Auth.signOut(
                { },
                { error -> Toast.makeText(baseContext, "SignOut failed.",
                    Toast.LENGTH_SHORT).show() }
            )
            updateUI(null)
        }

        signupButton.setOnClickListener{
            Amplify.Auth.signUp(
                userName.text.toString(),
                password.text.toString(),
                AuthSignUpOptions.builder().userAttribute(
                    AuthUserAttributeKey.email(), userName.text.toString()).build(),
                { result -> updateUI(Amplify.Auth.currentUser)},
                { error -> // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null) }
            )
        }

        confirmSignupButton.setOnClickListener {
            Amplify.Auth.confirmSignUp(
                userName.text.toString(),
                password.text.toString(),
                { result -> Log.i("AuthQuickstart", if (result.isSignUpComplete) "Confirm signUp succeeded" else "Confirm sign up not complete") },
                { error -> Log.e("AuthQuickstart", error.toString()) }
            )
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = Amplify.Auth.currentUser
        updateUI(currentUser)
    }

    fun updateUI(user: AuthUser?) {
        val tv: TextView = findViewById(R.id.current_user) as TextView
        if (user != null) {
            tv.text = user.username
        } else {
            tv.text = "No current user"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
