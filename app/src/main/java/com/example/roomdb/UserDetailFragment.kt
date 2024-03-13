package com.example.roomdb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.roomdb.databinding.FragmentUserDetailBinding


class UserDetailFragment : Fragment() {
    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var userDetailsViewModel: UserDetailsViewModel
    private var id: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)

        userDetailsViewModel = ViewModelProvider(this@UserDetailFragment)[UserDetailsViewModel::class.java]

        id = arguments?.getString("id", null).toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llHeader.tvPageName.text = "User Detail"
        binding.llHeader.btnBack.setOnClickListener {
//            findNavController().navigate(R.id.action_UserDetailFragment_to_userList_fragment)
            (activity as HomeActivity).addFragment(-1, UserListFragment(), "UserListFragment")
        }

        userDetailsViewModel.userDetailsResponse.observe(viewLifecycleOwner) {
            it?.let {
                binding.tvName.text = it.firstName +" " + it.lastName

                binding.tvDob.text = it.birthDate
                binding.tvEmail.text  = it.email
                binding.tvGender.text = it.gender
                binding.tvPhoneNo.text = it.phone
            }
        }



    }

    override fun onResume() {
        super.onResume()

        if (!id.isNullOrEmpty())
            userDetailsViewModel.getUserDetails(id)
    }

}