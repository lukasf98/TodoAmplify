package com.mobileappdevelopment.todoamplify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize Amplify Auth and Data
        Amplify.addPlugin(AWSDataStorePlugin())
        Amplify.addPlugin(AWSApiPlugin())
        Amplify.addPlugin(AWSCognitoAuthPlugin())
        Amplify.configure(getApplicationContext());
        // Get Buttons
        val loginDemoButton = findViewById<Button>(R.id.login_demo)
        val apiDemoButton = findViewById<Button>(R.id.api_demo)
        // Click Listener
        loginDemoButton.setOnClickListener {
            showLogin()
        }

        apiDemoButton.setOnClickListener{
            showTodos()
        }
    }

    fun showTodos() {
        val intent = Intent(this, ShowTodoActivity::class.java)
        startActivity(intent)
    }

    fun showLogin() {
        val intent = Intent(this, ShowLoginActivity::class.java)
        startActivity(intent)
    }
}
