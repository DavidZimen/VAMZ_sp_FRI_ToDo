package com.example.vamz_sp_fri_todo.app_logging.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.vamz_sp_fri_todo.R
import kotlinx.android.synthetic.main.fragment_register.view.*
import kotlinx.android.synthetic.main.fragment_welcome.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        val view =  inflater.inflate(R.layout.fragment_welcome, container, false)


        view.login.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_loginFragment)
        }

        view.signup.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_welcomeFragment_to_registerFragment)
        }

        return view
    }
}