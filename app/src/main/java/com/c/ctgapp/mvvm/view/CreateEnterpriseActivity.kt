package com.c.ctgapp.mvvm.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.c.ctgapp.MainActivity
import com.c.ctgapp.R
import com.c.ctgapp.mvvm.di.AppComponent
import kotlinx.android.synthetic.main.title.*
import kotlinx.android.synthetic.main.createenterprise.*
class CreateEnterpriseActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.createenterprise)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        skip_tv.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        if(v == skip_tv){
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}