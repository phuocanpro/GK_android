    package com.example.hothiphuocan_22iteb001

    import android.os.Bundle
    import android.view.MenuItem
    import androidx.appcompat.app.ActionBarDrawerToggle
    import androidx.appcompat.app.AppCompatActivity
    import androidx.appcompat.widget.Toolbar
    import androidx.core.view.GravityCompat
    import androidx.drawerlayout.widget.DrawerLayout
    import com.google.android.material.navigation.NavigationView
    import android.util.Log

    class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
        private lateinit var drawerLayout: DrawerLayout
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            setSupportActionBar(toolbar)

            val navigationView = findViewById<NavigationView>(R.id.nav_view)
            navigationView.setNavigationItemSelectedListener(this)

            val toggle = ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open_nav, R.string.close_nav)

            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment()).commit()
                navigationView.setCheckedItem(R.id.nav_home)
            }
        }

            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                Log.d("Navigation", "onNavigationItemSelected() called")

                when (item.itemId) {
                    R.id.nav_home -> {
                        Log.d("Navigation", "Selected nav_home")
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, HomeFragment()).commit()
                    }
                    R.id.nav_equation -> {
                        Log.d("Navigation", "Selected nav_equation")
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, EquationFragment()).commit()
                    }
                    R.id.nav_prime -> {
                        Log.d("Navigation", "Selected nav_prime")
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, PrimeFragment()).commit()
                    }
                    R.id.nav_student -> {
                        Log.d("Navigation", "Selected nav_student")
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, CourseFragment()).commit()
                    }

                }
            drawerLayout.closeDrawer(GravityCompat.START)
            return true
        }


        override fun onBackPressed() {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        }

    }