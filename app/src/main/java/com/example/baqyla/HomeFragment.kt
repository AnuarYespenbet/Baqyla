package com.example.baqyla

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.baqyla.data.User
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.intent?.let {
            if (it.hasExtra(Constants.USER)) {
                val user = it.getSerializableExtra(Constants.USER) as User

                if (!user.children.isNullOrEmpty()) {
                    val child = user.children[0]
                    name.text = child.name

                    Glide.with(context!!)
                        .load(child.profilePhoto)
                        .centerCrop()
                        .into(image)
                }

            }
        }



        /*val subjects = ArrayList<Subject>()
        subjects.add(Subject("Программирование", active = true))
        subjects.add(Subject("Рисование", active = false))
        subjects.add(Subject("Дзюдо", active = false))

        subjects_rv.adapter = SubjectsAdapter(subjects)*/
    }
}