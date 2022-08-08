package com.template.JK_0089_guide_v1.models

import android.net.Uri

data class Article(
    val articleTitle: String,
    val articleImage: Uri,
    val content: String
) : ContentType(articleTitle,articleImage)