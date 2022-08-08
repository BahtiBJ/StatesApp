package com.template.JK_0089_guide_v1.models

import android.net.Uri

data class Chapter(
    val chapterTitle: String,
    val chapterImage: Uri,
    val articles: ArrayList<Article>
) : ContentType(chapterTitle,chapterImage)