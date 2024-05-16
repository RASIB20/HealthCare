package com.example.healthcare

import Database
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivty : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleApiClient: GoogleApiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_activty)

        // Get shared preferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("healthcare", MODE_PRIVATE)

        // Check if shared preferences contain any data
        if (sharedPreferences.getString("userName", "") != "") {
            // If no data, create an intent to register activity
            val intent: Intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val eUserName:EditText=findViewById(R.id.editTextloginUsername)
        val ePassword:EditText=findViewById(R.id.editTextloginPassword)
        val btn:Button=findViewById(R.id.buttonloginButton)
        val registerText:TextView =findViewById(R.id.newUser)
        val googlesignin: ImageView = findViewById(R.id.googleImageView)

        btn.setOnClickListener {
            if(eUserName.text.toString().isNullOrEmpty() || ePassword.text.toString().isNullOrEmpty()){
                Toast.makeText(this,"Fill all fields",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                var userName=eUserName.text.toString()
                var pass=ePassword.text.toString()
                if(( Database(this,"healthcare",null,1).login(userName,pass))==1){
                    val sharedPreferences:SharedPreferences = getSharedPreferences("healthcare",
                        MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("userName",userName)
                    editor.apply()
                    Toast.makeText(this,"Log In success",Toast.LENGTH_SHORT).show()
                    val intent: Intent = Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Log In failed",Toast.LENGTH_SHORT).show()
                }
            }
        }
        registerText.setOnClickListener{
            val intent: Intent =Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        googlesignin.setOnClickListener{
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build()

            googleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, GoogleApiClient.OnConnectionFailedListener {
                    // Handle connection failure
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

            // Initialize Firebase Auth
            auth = FirebaseAuth.getInstance()

            // Call signIn() method when your button is clicked
            try {
                signIn()
            } catch (e: Exception) {
                Toast.makeText(this,"An Error occurred",Toast.LENGTH_SHORT).show()

            }
        }
    }
    private fun signIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val result = data?.let { Auth.GoogleSignInApi.getSignInResultFromIntent(it) }
            if (result!!.isSuccess) {
                // Google Sign In was successful, authenticate with Firebase
                val account = result.signInAccount
                firebaseAuthWithGoogle(account!!)
            } else {
                // Google Sign In failed
                // Handle failure
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    val user = auth.currentUser
                    var username:String = user!!.displayName.toString()
                    var pass:String = user!!.phoneNumber.toString()

                    Database(this,"healthcare",null,1).register(username,pass)
                    val sharedPreferences: SharedPreferences = getSharedPreferences("healthcare",
                        MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("userName",username)
                    editor.apply()
                    val intent: Intent = Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    // Sign in failed
                    // Update UI accordingly
                }
            }
    }
    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
