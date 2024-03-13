package com.example.roomdb

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.roomdb.Dao.UserEntity
import com.example.roomdb.data.user_list_api.User
import com.example.roomdb.databinding.FragmentUserListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserListFragment : Fragment(), UserListAdapter.OnClickListener {
    private lateinit var adapter: UserListAdapter
    private lateinit var userListViewModel: UserListViewModel
    private lateinit var binding: FragmentUserListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        binding = FragmentUserListBinding.inflate(layoutInflater, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)


        userListViewModel = ViewModelProvider(this@UserListFragment)[UserListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserListAdapter(ArrayList<UserEntity>(), this@UserListFragment)

        binding.rvUserList.adapter = adapter
        activity?.runOnUiThread {
            userListViewModel.userResponse.observe(viewLifecycleOwner) { response ->
                if (!response.users.isNullOrEmpty()) {
//                    adapter.refreshList(response.users)
                    // Launch a coroutine to insert users into the database
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            // Perform database insertion in the IO dispatcher
                            response.users.forEach { user ->
                                MyApp.database.userDao().insertUser(
                                    UserEntity(
                                        user_id = user.id.toString(),
                                        username = user.firstName + " " + user.lastName,
                                        phone = user.phone,
                                        birthDate = user.birthDate,
                                        gender = user.gender,
                                        email = user.email,
                                        image = user.image,

                                    )
                                )
                            }
                        }
                    }

                }
            }
        }


    }


    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                userListViewModel.getUserList()
            }
        }
    }

    @SuppressLint("CommitTransaction")
    override fun onUserClick(user: UserEntity, position: Int) {
        val bundle = Bundle()
        bundle.putString("id", user.user_id.toString())
        val userDetailFragment = UserDetailFragment()
        userDetailFragment.arguments = bundle
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.frame_container, userDetailFragment, "UserDetailFragment")
            ?.disallowAddToBackStack()
            ?.commit()
//        findNavController().navigate(R.id.action_mobile_navigation_to_UserDetails)
    }

    override fun onEditClick(user: UserEntity, position: Int) {
        val bundle = Bundle()
        bundle.putString("id", user.user_id.toString())
        val editDetailFragment = EditDetailFragment()
        editDetailFragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame_container, editDetailFragment, "EditDetailFragment")
            ?.disallowAddToBackStack()
            ?.commit()
//        findNavController().navigate(R.id.action_mobile_navigation_to_EditDetails)
    }

    override fun onDeleteClick(user: UserEntity, position: Int) {
        deleteUserFromRoom(user)
    }

    private fun deleteUserFromRoom(user: UserEntity) {

    }
}