package com.example.palindrome

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.palindrome.UserAdapter
import com.example.palindrome.model.User
import com.example.palindrome.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ThirdActivity : AppCompatActivity(), UserAdapter.OnUserClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var emptyStateTextView: TextView
    private lateinit var userAdapter: UserAdapter
    private lateinit var backButton: ImageButton

    private val userList = mutableListOf<User>()
    private var currentPage = 1
    private var isLoading = false
    private var totalPages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        emptyStateTextView = findViewById(R.id.emptyStateTextView)
        backButton = findViewById(R.id.backButton)

        userAdapter = UserAdapter(userList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        backButton.setOnClickListener {
            finish()
        }

        swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && currentPage < totalPages) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        loadMoreUsers()
                    }
                }
            }
        })

        loadUsers()
    }

    private fun loadUsers() {
        isLoading = true
        swipeRefreshLayout.isRefreshing = true

        val retrofit = Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getUsers(currentPage)

        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                isLoading = false
                swipeRefreshLayout.isRefreshing = false
                Log.d("API_RESPONSE", "Success: ${response.body()?.data}")
                Log.e("API_ERROR", "Failure")
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    userResponse?.let {
                        totalPages = it.total_pages
                        userList.addAll(it.data)
                        userAdapter.notifyDataSetChanged()

                        if (userList.isEmpty()) {
                            emptyStateTextView.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                        } else {
                            emptyStateTextView.visibility = View.GONE
                            recyclerView.visibility = View.VISIBLE
                        }
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                isLoading = false
                swipeRefreshLayout.isRefreshing = false
                emptyStateTextView.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                emptyStateTextView.text = "Failed to load users. Pull to refresh."
            }
        })
    }

    private fun loadMoreUsers() {
        currentPage++
        loadUsers()
    }

    private fun refreshData() {
        userList.clear()
        userAdapter.notifyDataSetChanged()
        currentPage = 1
        loadUsers()
    }

    override fun onUserClick(user: User) {
        val intent = Intent()
        intent.putExtra("SELECTED_USER_NAME", "${user.first_name} ${user.last_name}")
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}