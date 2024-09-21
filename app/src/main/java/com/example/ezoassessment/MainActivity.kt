package com.example.ezoassessment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ezoassessment.data.Constants.SelectedItem
import com.example.ezoassessment.data.models.User
import com.example.ezoassessment.databinding.ActivityMainBinding
import com.example.ezoassessment.ui.adapters.UsersAdapter
import com.example.ezoassessment.ui.viewmodels.UserViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val searchSubject = PublishSubject.create<String>()
    private var subscription: Disposable? = null
    private var options: List<User>? = null

    private var usersAdapter: UsersAdapter? = null

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as MyApplication).appComponent.inject(this)

        val recyclerView: RecyclerView = binding.userRecyclerView

        binding.userSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                onChangeSearchTerm(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        val viewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel:: class.java]
        viewModel.getUsers()
        viewModel.users.observe(this) {
            options = it
            usersAdapter = UsersAdapter(it)
            usersAdapter?.setOnClickListener(object: UsersAdapter.OnClickListener{
                override fun onClick(position: Int, model: User) {
                    val intent = Intent(this@MainActivity, ProfileActivity:: class.java)
                    intent.putExtra(SelectedItem, model)
                    startActivity(intent)
                }

            })
            setupSearch()
            recyclerView.apply {
                adapter = usersAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
    private fun setupSearch() {
        subscription = searchSubject.hide()
            .distinctUntilChanged()
            .applySchedulers()
            .map { term -> getSearchResult(term) }
            .subscribe { result -> showSearchResultView(result) }
    }

    private fun getSearchResult(term: String): List<User> {
        return options?.filter {
            val name = it.first_name+" "+it.last_name
            name.contains(term, true) }?.map { result ->
            User(
                first_name = result.first_name,
                last_name = result.last_name,
                avatar = result.avatar,
                id = result.id,
                email = result.email
            )
        } ?: emptyList()
    }

    private fun showSearchResultView(result: List<User>) {

        usersAdapter?.clear()
        usersAdapter?.updateData(result)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (subscription?.isDisposed == false) {
            subscription?.dispose()
        }
    }

    private fun onChangeSearchTerm(term: String) {

        if (term.isBlank()) {
            showOptionListView()
        } else {
            searchSubject.onNext(term)
        }
    }

    private fun showOptionListView() {

        usersAdapter?.clear()
        usersAdapter?.updateData(options ?: emptyList())
    }

    fun <T> Observable<T>.applySchedulers(): Observable<T> = observeOn(AndroidSchedulers.mainThread()).subscribeOn(
        Schedulers.io())
}