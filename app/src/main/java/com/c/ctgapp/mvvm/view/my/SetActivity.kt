package com.c.ctgapp.mvvm.view.my

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.c.ctgapp.R
import kotlinx.android.synthetic.main.activity_set.*
import kotlinx.android.synthetic.main.title.*
import java.util.*

class SetActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set)
        toolbar_title.text = "设置"
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        Event()
    }

    private fun Event() {
        ChangePassword_rl!!.setOnClickListener(this)
        Receivingaddress_rl!!.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        if (v === ChangePassword_rl) { //修改密码
            startActivity(Intent(applicationContext, ChangePasswordActivity::class.java))
        }
        if (v === Receivingaddress_rl) {
            startActivity(Intent(applicationContext, ReceivingAddressActivity::class.java))
        }
    }
}