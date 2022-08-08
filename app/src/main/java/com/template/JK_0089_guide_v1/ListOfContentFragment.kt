package com.template.JK_0089_guide_v1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.template.JK_0089_guide_v1.data.ChapterManager
import com.template.JK_0089_guide_v1.data.ContentListAdapter
import com.template.JK_0089_guide_v1.models.ContentType
import com.template.JK_0089_guide_v1.util.Constants


class ListOfContentFragment : Fragment() {

    var chapterNumber = -1
    var articleNumber = -1

    val navController: NavController by lazy {
        Navigation.findNavController(requireActivity(), R.id.fragment_container_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chapterNumber = arguments?.getInt(Constants.chapterNumberKey, -1) ?: -1
        articleNumber = arguments?.getInt(Constants.articleNumberKey, -1) ?: -1

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contentList: ArrayList<out ContentType>
        if (chapterNumber == -1) {
            (requireActivity() as AppCompatActivity).supportActionBar?.setTitle("Разделы");
            contentList = ChapterManager.getChapters()
        } else {
            (requireActivity() as AppCompatActivity).supportActionBar?.setTitle(ChapterManager.getChapters()[chapterNumber].chapterTitle);
            contentList = ChapterManager.getChapters()[chapterNumber].articles
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.list_content)

        val adapterContent = ContentListAdapter(contentList,
            object : ContentListAdapter.OnListItemTouchListener {
                override fun onItemClick(position: Int) {
                    if (chapterNumber == -1) {
                        val bundle = Bundle()
                        bundle.putInt(Constants.chapterNumberKey,position)
                        navController.navigate(R.id.action_listOfContentFragment_to_listOfContentFragment22,bundle)
                    } else {
                        val bundle = Bundle().apply {
                            putInt(Constants.chapterNumberKey,chapterNumber)
                            putInt(Constants.articleNumberKey,position)
                        }
                        navController.navigate(R.id.action_listOfContentFragment2_to_articleFragment2,bundle)
                    }
                }
            })
        recyclerView.adapter = adapterContent
    }

}