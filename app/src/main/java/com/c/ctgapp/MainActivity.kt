package com.c.ctgapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.c.ctgapp.Tools.DialogUtils
import com.c.ctgapp.Tools.Utils
import com.c.ctgapp.mvvm.model.*
import com.c.ctgapp.mvvm.view.my.MainViewModel
import com.c.ctgapp.mvvm.viewmodel.DaggerViewModelFactory
import com.c.ctgapp.mvvm.viewmodel.PersonalInformationViewModel
import kotlinx.android.synthetic.main.title.*
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjection
import javax.inject.Inject
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(),Observer<PersonalInfo> {
    @JvmField
    @Inject
    var viewModelFactory: DaggerViewModelFactory? = null
    var model: MainViewModel? = null
    private var dialogUtils: DialogUtils? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dialogUtils = DialogUtils()
        model = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        model!!.PeronalInfo(Utils.getShared2(this, "userId").toInt())
        model!!.getdata().observe(this, Observer { response: Response<PersonalInfo> ->
            if(response.status == 0){
                Log.e("s","d")
                putpersondata(response.data)
            }else{
                dialogUtils!!.showTwo(this, "提示", response.msg)
            }

        })
        toolbar_title!!.setText(R.string.title_home)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        nav_view!!.setOnNavigationItemSelectedListener { item: MenuItem ->
            toolbar_title!!.text = item.title
            when (item.itemId) {
                R.id.navigation_home -> {
                    left_tv!!.visibility = View.GONE
                    right_tv!!.visibility = View.GONE
                    toolbar!!.visibility = View.VISIBLE
                }
                R.id.navigation_news -> {
                    left_tv!!.visibility = View.VISIBLE
                    right_tv!!.visibility = View.VISIBLE
                    left_tv!!.text = "我的商友"
                    right_tv!!.text = "添加"
                    toolbar!!.visibility = View.VISIBLE
                }
                R.id.navigation_work -> {
                    left_tv!!.visibility = View.GONE
                    right_tv!!.visibility = View.GONE
                    toolbar!!.visibility = View.VISIBLE
                }
                R.id.navigation_transaction -> {
                    left_tv!!.visibility = View.GONE
                    right_tv!!.visibility = View.GONE
                    toolbar!!.visibility = View.VISIBLE
                }
                R.id.navigation_my -> {
                    toolbar!!.visibility = View.GONE
                    left_tv!!.visibility = View.GONE
                    right_tv!!.visibility = View.GONE
                }
            }
            NavigationUI.onNavDestinationSelected(item, navController)
        }
        /*       navView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            //二次点击触发动作
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

            }
        });*/
    }

    private fun putpersondata(personalInfo: PersonalInfo){
        UserInfoLiveData.getInstance().value = personalInfo
    }

    override fun onChanged(t: PersonalInfo?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}