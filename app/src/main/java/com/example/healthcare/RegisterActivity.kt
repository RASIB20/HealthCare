package com.example.healthcare

import Database
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val userName:EditText = findViewById(R.id.registerUserName)
        val password:EditText = findViewById(R.id.registerPassword)
        val confirmPassword:EditText = findViewById(R.id.registerConfirmPassword)
        val registerbtn:Button= findViewById(R.id.registerButton)
        val alreadAccount:TextView = findViewById(R.id.alreadyAccount)

        alreadAccount.setOnClickListener {
            val intent: Intent = Intent(this,LoginActivty::class.java)
            startActivity(intent)
        }
        registerbtn.setOnClickListener {
            if(userName.text.toString().isNullOrEmpty() || password.text.toString().isNullOrEmpty()||confirmPassword.text.toString().isNullOrEmpty()){
                Toast.makeText(this,"Fill all credentials",Toast.LENGTH_SHORT).show()
            }else{
                var username:String = userName.text.toString()
                var pass :String= password.text.toString();
                var confirmpass :String= confirmPassword.text.toString();
                if(pass.compareTo(confirmpass)==0){
                    if(isvalid(pass)==true){
                        Database(this,"healthcare",null,1).register(username,pass)
                        Toast.makeText(this,"Registered successfully. ",Toast.LENGTH_SHORT).show()
                        val intent: Intent = Intent(this,LoginActivty::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this,"Password is invalid. ",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,"Password and confirm password doesn't match",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
     fun isvalid(pass:String): Boolean {
         var f1 = 0;
         var f2 = 0;
         var f3 = 0;
         if (pass.length < 0) {
             return false;
         } else {
             for (p in 0 until pass.length) {
                 if (pass[p].isLetter()) {
                     f1 = 1;
                 }
             }
             for (p in 0 until pass.length) {
                 if (pass[p].isDigit()) {
                     f2 = 1;
                 }
             }
             for (p in 0 until pass.length) {
                 var ch: Char = pass[p]

                 if (ch.toInt() in 33..46 || ch.toInt() == 64) {
                     f3 = 1
                 }
             }


         }
         if (f1 == 1 && f2 == 1 && f3 == 1) {
             return true;
         }
         return false;
     }
}