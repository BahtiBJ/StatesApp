package com.template.JK_0089_guide_v1

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.template.JK_0089_guide_v1.data.ChapterManager
import com.template.JK_0089_guide_v1.models.Chapter
import com.template.JK_0089_guide_v1.util.Constants
import kotlin.system.exitProcess


class GuideActivity : AppCompatActivity() {

    var countOfItem = 0

    lateinit var chapters: ArrayList<Chapter>
    val drawerLayout: DrawerLayout by lazy { findViewById(R.id.drawer_layout) }
    val navigationView: NavigationView by lazy { findViewById(R.id.navView) }

    val navController: NavController by lazy {
        Navigation.findNavController(this, R.id.fragment_container_view)
    }

    val navOption: NavOptions = NavOptions.Builder()
        .setEnterAnim(R.anim.fade_in)
        .setExitAnim(R.anim.fade_out)
        .setPopEnterAnim(R.anim.fade_in)
        .setPopExitAnim(R.anim.fade_out)
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        chapters = ChapterManager.getChapters()


        navigationView.menu.let { menu ->
            countOfItem = chapters.size
            for (n in 0 until countOfItem) {
                menu.add(Menu.NONE, 100 + n, n, chapters[n].title)
                    .setIcon(R.drawable.bookmark_plus_outline)
            }
            menu.add(Menu.NONE, 100 + countOfItem, countOfItem, "Выход")
                .setIcon(R.drawable.location_exit)
        }

        navigationView.setNavigationItemSelectedListener {
            selectItem(it)
        }
    }

    fun goToFirstFragment() {
        navController.navigate(R.id.action_to_start)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun selectItem(menuItem: MenuItem): Boolean {
        val id = menuItem.itemId
        val bundle = Bundle()
        if (id == (100 + countOfItem)) {
            finish()
            exitProcess(0)
        } else {
            for (i in (100 until (100 + countOfItem))) {
                if (id == i) {
                    bundle.putInt(Constants.chapterNumberKey, i - 100)
                    break
                }
            }
        }
        when (navController.currentDestination?.id) {
            R.id.listOfContentFragment1 -> navController.navigate(
                R.id.action_listOfContentFragment_to_listOfContentFragment22,
                bundle,
                navOption
            )
            R.id.articleFragment -> navController.navigate(
                R.id.action_articleFragment_to_listOfContentFragment22,
                bundle,
                navOption
            )
            R.id.listOfContentFragment2 -> navController.navigate(
                R.id.action_listOfContentFragment2_self2,
                bundle,
                navOption
            )
        }

        menuItem.setChecked(true);
        supportActionBar?.setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
        return true

    }




}