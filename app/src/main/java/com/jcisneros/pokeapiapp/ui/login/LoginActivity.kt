package com.jcisneros.pokeapiapp.ui.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jcisneros.pokeapiapp.R
import com.jcisneros.pokeapiapp.datasource.models.User

class LoginActivity : AppCompatActivity() {

    //declare ViewModel
    private lateinit var viewModel: LoginViewModel

    lateinit var btnLogin: Button
    lateinit var btnRegister: Button
    lateinit var editTxttEmail: EditText
    lateinit var editTextPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //init reference to ViewModel
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        //inits UI components
        btnRegister = findViewById(R.id.btn_register)
        btnLogin = findViewById(R.id.btn_login)
        editTxttEmail = findViewById(R.id.edit_txt_email)
        editTextPassword = findViewById(R.id.editTextTextPassword)

        viewModel.isRegister.observe(this, Observer { isRegister ->
            if(isRegister){
                Toast.makeText(this, "User register success!", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "User register failed!", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.userData.observe(this, Observer { userData ->
            Toast.makeText(this, userData.email, Toast.LENGTH_SHORT).show()
            //if user login and return data
            val prefs = getSharedPreferences("com.jcisneros.pokeapiapp", Context.MODE_PRIVATE)
                .edit()
            //save email for login
            prefs.putString("emailLogin", userData.email).apply()
        })

        //register user
        btnRegister.setOnClickListener{
            viewModel.registerUser(User(
                editTxttEmail.text.toString(),
                editTextPassword.text.toString()
            ))
        }
        btnLogin.setOnClickListener{
            viewModel.loginUser(User(
                    editTxttEmail.text.toString(),
                    editTextPassword.text.toString()
            ))
        }
    }
}

