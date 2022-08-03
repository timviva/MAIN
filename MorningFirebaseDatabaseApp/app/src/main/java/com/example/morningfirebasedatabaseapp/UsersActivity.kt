package com.example.morningfirebasedatabaseapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UsersActivity : AppCompatActivity() {
    var listusers:ListView?=null
    var Adapter:CustomAdapter ?=null
    var users:ArrayList<User> ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        listusers=findViewById(R.id.MlistUsers)
        users=ArrayList()
         Adapter = CustomAdapter(this, users!!)
        val reference=FirebaseDatabase.getInstance().
                                        getReference().child("User")
        reference.addValueEventListener(object :ValueEventListener{
//            Override on data changed
            override fun onDataChange(snapshot: DataSnapshot) {
                users!!.clear()
//    User for loop to add the user on the arraylist
    for (snap in  snapshot.children){
        var user=snap.getValue(User::class.java)
        users!!.add(user!!)
    }
    Adapter!!.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"Please Contact the Admin",Toast.LENGTH_LONG).show()
            }
        })
        listusers!!.adapter=Adapter!!
//        Set on item click listener to the listview
        listusers!!.setOnItemClickListener { adapterView, view, i, l ->
            val UserId= users!!.get(i).Id
            val  deletionReference=FirebaseDatabase.getInstance().
                                                       getReference().child("User/$UserId")
            val alertDialog=AlertDialog.Builder(this)

            alertDialog.setTitle("Alert!!!")
            alertDialog.setMessage("Select An option you want perform")
            alertDialog.setNegativeButton("Update",
                DialogInterface.OnClickListener { dialogInterface, i ->
//Dismiss alert

                })
            alertDialog.setPositiveButton("Delete",DialogInterface.OnClickListener{dialogInterface, i ->
                deletionReference.removeValue()
                Toast.makeText(applicationContext,"Deleted Successfully",Toast.LENGTH_LONG).show()
            })
            alertDialog.create().show()

        }
    }
}

