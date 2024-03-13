package com.example.roomdb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.roomdb.Dao.UserEntity
import com.example.roomdb.databinding.FragmentEditDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditDetailFragment : Fragment() {

    private lateinit var editDetailViewModel: EditDetailViewModel
    private lateinit var binding: FragmentEditDetailBinding
    private var id: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        binding = FragmentEditDetailBinding.inflate(layoutInflater, container, false)
        binding = FragmentEditDetailBinding.inflate(inflater, container, false)


        id = arguments?.getString("id", null).toString()

        editDetailViewModel =
            ViewModelProvider(this@EditDetailFragment)[EditDetailViewModel::class.java]
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llHeader.tvPageName.text = "Edit User Detail"
        binding.llHeader.btnBack.setOnClickListener {
//            findNavController().navigate(R.id.action_EditDetailFragment_to_userList_fragment)
            (activity as HomeActivity).addFragment(-1, UserListFragment(), "UserListFragment")
        }

        binding.btnEdit.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    MyApp.database.userDao().insertUser(
                        UserEntity(
                            user_id = id.toString(),
                            username = binding.etName.text.toString(),
                            userphone = binding.etPhone.text.toString()
                        )
                    )

                    (activity as HomeActivity).addFragment(-1, UserListFragment(), "UserListFragment")
                }
            }
        }


        editDetailViewModel.userDetailsResponse.observe(viewLifecycleOwner) {
            it?.let {
                binding.etName.setText(it.firstName + " " + it.lastName)

                binding.etPhone.setText(it.phone)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (!id.isNullOrEmpty())
            editDetailViewModel.updateUserDetails(id!!)
    }

}
//}