package com.example.morningfirebasedatabaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    var MedtName:EditText ?= null
    var MedtEmail:EditText ?= null
    var MedtId:EditText ?= null
    var MbtnSave:Button ?= null
    var MbtnView:Button?= null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MedtName=findViewById(R.id.MedtName)
        MedtEmail=findViewById(R.id.medtEmail)
        MedtId=findViewById(R.id.Medtid)
        MbtnSave=findViewById(R.id.MbtnSave)
       MbtnView =findViewById(R.id.MbtnView)

        MbtnSave!!.setOnClickListener{
            val userName=MedtName!!.text.toString().trim()
            val userEmail=MedtEmail!!.text.toString().trim()
            val userId=MedtId!!.text.toString().trim()
            val id=System.currentTimeMillis().toString()

            if (userName.isEmpty()){
                MedtName!!.setError("PLease fill this field!!")
                MedtName!!.requestFocus()
            }else if (userEmail.isEmpty()){
                MedtEmail!!.setError("PLease fill this field!!")
                MedtEmail!!.requestFocus()
            }else if (userId.isEmpty()){
                MedtId!!.setError("PLease fill this field!!")
                MedtId!!.requestFocus()
            }
            else{
//                proceed to save the data

                val userData=User(userName,userEmail,userId,id)
//                create a reference to the database to store data
                val reference=FirebaseDatabase.getInstance().
                                                        getReference().child("User/$id")
                reference.setValue(userData).addOnCompleteListener {
                    task->
                    if (task.isSuccessful){
                        Toast.makeText(applicationContext,"Data Saving successfull",Toast.LENGTH_LONG).show()
                    }else if (task.isSuccessful){
                        Toast.makeText(applicationContext,"Data Saving Failed",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        MbtnView!!.setOnClickListener{
            val  intent=Intent(applicationContext,UsersActivity::class.java)
            startActivity(intent)

        }
    }

}