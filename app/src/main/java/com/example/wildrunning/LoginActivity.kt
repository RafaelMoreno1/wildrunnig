package com.example.wildrunning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
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

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    //Ocultar linear layout
    lyTerms = findViewById(R.id.tmcTerm)
    lyTerms.visibility = View.INVISIBLE
    //Asignar las variables con los ID de diseño
        etmail = findViewById(R.id.etmail)
        etPassword = findViewById(R.id.etPassword)
        mAuth = FirebaseAuth.getInstance()
   }

    fun login (view:View){
        loginUser()
    }

    private fun loginUser(){
        email = etmail.text.toString()
        password = etPassword.text.toString()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if(task.isSuccessful ) goHome(email,  "email")
                else{
                    if (lyTerms.visibility == View.INVISIBLE) lyTerms.visibility = View.VISIBLE
                    else{
                        var tmchekAcept = findViewById<CheckBox>(R.id.tmchekAcept)
                        if (tmchekAcept.isChecked) register()
                    }
                }
            }
    }
    //mantener session abierta
    public override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null)
            goHome(currentUser.email.toString(), currentUser.providerId)
    }

    private fun goHome(email: String, provider: String){
        useremail = email
        providerSession = provider
        val intent = Intent( this, MainActivity::class.java)
        startActivity(intent)
    }
    //regresar al menu globlal
    override fun onBackPressed() {
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }
    private fun  register(){
        email = etmail.text.toString()
        password = etPassword.text.toString()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password )
            .addOnCompleteListener{
                if(it.isSuccessful){
                    //Guardar la fecha de registro
                        var dateRegister = SimpleDateFormat("dd/mm/yyyy" ).format(Date())
                        var dbRegister = FirebaseFirestore.getInstance()
                        dbRegister.collection("user").document(email).set(hashMapOf(
                            "user" to email,
                            "dateRegister" to dateRegister
                        ))
                    goHome(email, "email")
                }
                else Toast.makeText(this, "error, algo ha ido mal :(", Toast.LENGTH_SHORT).show()
            }
    }
}