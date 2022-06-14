package com.example.vamz_sp_fri_todo.mainFuncionality

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.vamz_sp_fri_todo.R
import com.google.android.material.navigation.NavigationView

class MainFuncionalityActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var nvDrawer: NavigationView
    private lateinit var drawerToggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_funcionality)

        //nastavenie toolbaru a nahradenie ActionBaru toolbarom
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //ziskanie layoutu
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerToggle = setUpToggle()

        //nastavenie animacie a ikony hamburhera
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()

        //napojenie eventou DrawerLayoutu ku ActionBaru
        drawerLayout.addDrawerListener(drawerToggle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        newConfig.let { super.onConfigurationChanged(it) }
        drawerToggle.onConfigurationChanged(newConfig)
    }

    private fun setUpToggle(): ActionBarDrawerToggle {
        return ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
    }
}