package com.example.vamz_sp_fri_todo.mainFuncionality

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.database.StudentDatabase
import com.example.vamz_sp_fri_todo.mainFuncionality.view_model.MainFuncViewModel
import com.example.vamz_sp_fri_todo.mainFuncionality.view_model.MainFuncViewModelFactory
import com.example.vamz_sp_fri_todo.student.Student
import com.google.android.material.navigation.NavigationView

class MainFuncionalityActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle

    lateinit var student: Student
    private lateinit var viewModelFactory: MainFuncViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_funcionality)

        student = intent.getSerializableExtra("student") as Student

        //vytvorenie vpremennych pre pracu s datami
        val app = requireNotNull(this).application
        val db = StudentDatabase.getInstance(app).studentDatabaseDao
        viewModelFactory = MainFuncViewModelFactory(student.osCislo_, db, app)

        createAndSetUpToolbar()
    }

    fun createAndSetUpToolbar() {
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
            return true
        }
        return super.onOptionsItemSelected(item)
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

    override fun getDefaultViewModelProviderFactory(): MainFuncViewModelFactory {
        return viewModelFactory
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}