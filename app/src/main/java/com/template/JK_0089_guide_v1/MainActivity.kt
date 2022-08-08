package com.template.JK_0089_guide_v1

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.AnyRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.template.JK_0089_guide_v1.data.ChapterManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ChapterManager.defaultImage = getUriToDrawable(this,R.drawable.gradient)

        val progressBar: ProgressBar = findViewById(R.id.progressbar)
        progressBar.visibility = ProgressBar.VISIBLE

        val external = getExternalFilesDir(null)?.listFiles() ?: arrayOf()

        lifecycleScope.launch(Dispatchers.IO) {
            if (external.size == 0) {
                launch { copyExample() }.join()
            }
            launch { ChapterManager.getChapters(getExternalFilesDir(null)!!) }.join()
            Handler(Looper.getMainLooper()).postDelayed({ start() }, 1500)

        }
    }

    fun start() {
        val intent = Intent(this, GuideActivity::class.java). apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun copyExample() {
        val paths = arrayListOf(
            "Животные/Коты",
            "Коты/",
            "Коты/кот1",
            "Коты/кот2",
            "Коты/кот3",
            "Животные/Мыши",
            "Мыши/мышь1",
            "Мыши/мышь2",
            "Мыши/мышь3",
            "Животные/Собаки",
            "Собаки/собака1",
            "Собаки/собака2",
            "Собаки/собака3"
        )
        val fileTXT = arrayListOf(
            R.raw.abcat,
            R.raw.abcat,
            R.raw.cat1,
            R.raw.cat2,
            R.raw.cat3,
            R.raw.abm,
            R.raw.m1,
            R.raw.m2,
            R.raw.m3,
            R.raw.abs,
            R.raw.s1,
            R.raw.s2,
            R.raw.s3
        )
        val fileTXTName = arrayListOf(
            "abcat",
            "cat1",
            "cat1",
            "cat2",
            "cat3",
            "abm",
            "m1",
            "m2",
            "m3",
            "abs",
            "s1",
            "s2",
            "s3"
        )
        val fileJPG = arrayListOf(
            R.drawable.kot,
            R.drawable.kot,
            R.drawable.kot,
            R.drawable.kot1,
            R.drawable.kot2,
            R.drawable.m1,
            R.drawable.m1,
            R.drawable.m2,
            R.drawable.m3,
            R.drawable.s1,
            R.drawable.s1,
            R.drawable.s2,
            R.drawable.s3
        )
        val fileJPGName = arrayListOf(
            "kot",
            "kot",
            "kot",
            "kot1",
            "kot2",
            "m1",
            "m1",
            "m2",
            "m3",
            "s1",
            "s1",
            "s2",
            "s3"
        )
        for (i in 0 until paths.size) {
            val pathTxt = getExternalFilesDir(null).toString() + "/" + paths[i]
            File(pathTxt).let{
                if (!it.exists()){
                    it.mkdirs()
                }
            }
            val pathJpg = getExternalFilesDir(null).toString() +  "/" + paths[i]
            File(pathJpg).let{
                if (!it.exists()){
                    it.mkdirs()
                }
            }
            createFile(fileTXT[i], pathTxt + "/" + fileTXTName[i] + ".txt")
            createFile(fileJPG[i], pathJpg + "/" + fileJPGName[i] + ".jpg")
        }


    }

    fun createFile(id: Int, filePath: String) {
        BufferedOutputStream(
            FileOutputStream(filePath)
        ).use { output ->
            BufferedInputStream(resources.openRawResource(id)).use { input ->
                while (true) {
                    val r = input.read()
                    if (r == -1) break
                    output.write(r)
                }
            }
        }
    }

    fun getUriToDrawable(
        context: Context,
        @AnyRes drawableId: Int
    ): Uri {
        return Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + context.getResources().getResourcePackageName(drawableId)
                    + '/' + context.getResources().getResourceTypeName(drawableId)
                    + '/' + context.getResources().getResourceEntryName(drawableId)
        )
    }
}