package com.template.JK_0089_guide_v1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.template.JK_0089_guide_v1.data.ChapterManager
import com.template.JK_0089_guide_v1.util.Constants


class ArticleFragment : Fragment() {

    var chapterNumber = -1
    var articleNumber = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chapterNumber = arguments?.getInt(Constants.chapterNumberKey, -1)!!
        articleNumber = arguments?.getInt(Constants.articleNumberKey, -1)!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image: ImageView = view.findViewById(R.id.article_preview)
        val stringContent: TextView = view.findViewById(R.id.article_content)
        val homeButton: FloatingActionButton = view.findViewById(R.id.button_home)
        homeButton.setOnClickListener {
            (requireActivity() as GuideActivity).goToFirstFragment()
        }
        if (articleNumber != -1) {
            ChapterManager.getChapters()[chapterNumber].articles[articleNumber].let {
                (requireActivity() as AppCompatActivity).supportActionBar?.setTitle(it.articleTitle);
                image.setImageURI(it.image)
                stringContent.text = it.content
            }
        }
    }


}