package com.template.JK_0089_guide_v1.data

import android.net.Uri
import android.util.Log
import com.template.JK_0089_guide_v1.MainActivity
import com.template.JK_0089_guide_v1.models.Article
import com.template.JK_0089_guide_v1.models.Chapter
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

object ChapterManager {

    var defaultImage : Uri = Uri.parse("")
    private var chapters : ArrayList<Chapter>? = null

    fun getChapters(directories : File? = null) : ArrayList<Chapter>{
        if (chapters == null) {
            if (directories != null) {
                Log.d("TAG","ChapterManger getChapters path = ${directories.path} size = ${directories.listFiles().size}")
                chapters = getListOfChapters(directories?.listFiles())
            }
            else throw Exception("Chapter is empty, need to initialize.")
        }
        return chapters as ArrayList<Chapter>
    }

    private fun getListOfChapters(directories: Array<File>?): ArrayList<Chapter> {
        val chapters: ArrayList<Chapter> = arrayListOf()
        if (directories != null && directories.isNotEmpty()) {
            for (chapter in directories) {
                getChapter(chapter)?.let {
                    chapters.add(it)
                }
            }
        }
        chapters.sortBy { it.title }
        return chapters
    }

    private fun getChapter(chapterDir: File): Chapter? {
        val chapterContent = chapterDir.listFiles() ?: arrayOf<File>()
        var resultChapter: Chapter? = null
        if (chapterContent.isNotEmpty()) {
            var title: String = chapterDir.nameWithoutExtension
            var image: Uri? = null
            var articles: ArrayList<Article> = arrayListOf()
            for (content in chapterContent) {
                if (content.isDirectory) {
                    getArticles(content)?.let {
                        articles.add(it)
                    }
                } else if (isImage(content.name)) {
                    image = Uri.fromFile(content)
                }
            }
            articles.sortBy { it.articleTitle }
            if (articles.isNotEmpty()) {
                resultChapter = Chapter(
                    title,
                    image ?: defaultImage,
                    articles
                )
            }

        }
        return resultChapter
    }

    private fun getArticles(articleDir: File): Article? {
        val articleContent = articleDir.listFiles() ?: arrayOf()
        var resultArticle: Article? = null
        if (articleContent.isNotEmpty()) {
            var title: String? = null
            var image: Uri? = null
            var stringContent: String? = null
            for (content in articleContent) {
                if (!content.isDirectory) {
                    if (isText(content.name)) {
                        getTextFromFile(content).let {
                            title = it.first
                            stringContent = it.second
                        }
                    } else if (isImage(content.name)){
                        image = Uri.parse(content.path)
                        Log.d("MyImage","getArt isImage = ${isImage(content.name)} uri = ${image}")
                    }
                }
            }
            if (title != null && stringContent != null) {
                resultArticle = Article(
                    title!!,
                    image!!,
                    stringContent!!
                )
            }
        }
        return resultArticle
    }

    private fun isText(name: String): Boolean {
        return name.endsWith("txt") || name.endsWith("doc") || name.endsWith("pdf")
                || name.endsWith("docx") || name.endsWith("rtf")
    }

    private fun isImage(name: String): Boolean {
        return name.endsWith("jpg") || name.endsWith("jpeg") || name.endsWith("png")
                || name.endsWith("webp")
    }

    private fun getTextFromFile(file: File): Pair<String, String> {
        var input: BufferedReader? = null
        var title = ""
        var result: StringBuffer = StringBuffer()
        try {
            input = BufferedReader(InputStreamReader(file.inputStream()))
            var tempStr: String? = ""
            while (tempStr != null) {
                if (title.equals("")) {
                    title = input.readLine()
                }
                tempStr = input.readLine()
                if (tempStr != null)
                    result.append(tempStr)

            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            input?.close()
        }
        return Pair(title, result.toString())
    }

}