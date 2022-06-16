package com.example.vamz_sp_fri_todo.calendar

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.mainFuncionality.MainFuncionalityActivity
import com.example.vamz_sp_fri_todo.student.Student
import com.example.vamz_sp_fri_todo.student.StudentInformationActivity
import kotlinx.android.synthetic.main.activity_calendar.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle

    lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        student = intent.getSerializableExtra("student") as Student

        createAndSetUpToolbar()

        //nastavenie akcii pre bocne menu
        calNvView.setNavigationItemSelectedListener {
            var intent: Intent? = null

            when (it.itemId) {
                R.id.nav_lists -> {
                    intent = Intent(this, MainFuncionalityActivity::class.java)
                    intent.putExtra("student", student)
                    startActivity(intent)
                }

                R.id.nav_account -> {
                    intent = Intent(this, StudentInformationActivity::class.java)
                    intent.putExtra("student", student)
                    startActivity(intent)
                }

                R.id.nav_calendae -> {}
            }

            return@setNavigationItemSelectedListener true
        }
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
        supportActionBar?.title = "Kalendár"
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

    override fun onBackPressed() {

    }
}