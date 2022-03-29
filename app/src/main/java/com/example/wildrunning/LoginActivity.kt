package com.example.wildrunning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider
import kotlin.properties.Delegates

class LoginActivity : AppCompatActivity() {
    companion object{
        lateinit var useremail: String
        lateinit var providerSession: String
    }

    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()
    private lateinit var etmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var lyTerms: LinearLayout

    private lateinit var mAuth: FirebaseAut


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    //Ocultar linear layout
    lyTerms = findViewById(R.id.tmcTerm)
    lyTerms.visibility = View.INVISIBLE
    //Asignar las variables con los ID de diseño
        etmail = findViewById(R.id.etmail)
        etPassword = findViewById(R.id.etPassword)
        mAuth = FirebaseAuthCredentialsProvider()

   }
}