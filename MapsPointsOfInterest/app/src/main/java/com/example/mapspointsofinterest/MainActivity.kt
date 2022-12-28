package com.example.mapspointsofinterest

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    val GET_LOCATION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonTap()
    }

    private fun buttonTap(){
        val button_location = findViewById<Button>(R.id.btn_location)
        button_location.setOnClickListener{
            checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION,"location",GET_LOCATION)

        }
    }

    private fun checkPermission(permission: String,name: String,requestCode: Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            when{
                ContextCompat.checkSelfPermission(applicationContext,permission) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(applicationContext,"permission granted", Toast.LENGTH_SHORT).show()
                    val nextPage = Intent(this, MapsPOI::class.java)
                    startActivity(nextPage)
                    finish()
                }
                shouldShowRequestPermissionRationale(permission) -> showDialog(permission,name,requestCode)

                else -> ActivityCompat.requestPermissions(this, arrayOf(permission),requestCode)
            }
        }

    }


    private fun showDialog(permission: String,name: String,requestCode: Int){
        val builder = AlertDialog.Builder(this)

        builder.apply {
            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission Required")
            setPositiveButton("OK"){ dialog,which ->
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission),requestCode)

            }
        }
        val dialog = builder.create()
        dialog.show()

    }
}