package com.muratipek.munote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.Navigation
import com.muratipek.munote.view.NoteFeedFragment
import com.muratipek.munote.view.NoteFeedFragmentDirections

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_note -> {
                //Go Details Fragment
                GoDetailsFragment()
            }
            R.id.sign_out -> {
                //Sign out
                SignOut()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun GoDetailsFragment(){
    }
    fun SignOut(){

    }
}