package com.bilalov.testapplicationforappricot.data.module.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bilalov.testapplicationforappricot.data.module.network.ApiService
import com.bilalov.testapplicationforappricot.data.module.network.UrlItemProvider
import com.bilalov.testapplicationforappricot.data.request.ResponseSearchRepos
import com.bilalov.testapplicationforappricot.data.request.searchRepo.userProfile.ResponseUserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() :ViewModel() {

    val statusApi = MutableLiveData<String>()


    private  val _sharedFlow = MutableSharedFlow<ResponseSearchRepos>(
            replay = 30,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )

    val sharedFlow: SharedFlow<ResponseSearchRepos> = _sharedFlow.asSharedFlow()


    var responseObjectUserProfile: ResponseUserProfile? = null

    val statusApiSecondFuel = MutableLiveData<String>()

    private val _allSearchItems = MutableLiveData<ResponseSearchRepos?>()
    val allSearchItems: MutableLiveData<ResponseSearchRepos?>
        get() = _allSearchItems


    private val _allUserInfo = MutableLiveData<ResponseUserProfile?>()
    val allUserInfo: MutableLiveData<ResponseUserProfile?>
        get() = _allUserInfo


    fun getRepository(textInputSearch: String, application: Application) {
        viewModelScope.launch {
            send(textInputSearch, application)
        }
    }

    fun getUserInfo(login: String, application: Application) {
        viewModelScope.launch {
            sendProfile(login, application)
        }
    }

    private fun send(textInputSearch: String, application: Application) {
         val url = "dG9rZW4gZ2hwX1pkOEpiNGZ0SXc5YTJibWs4WjRZNlh2R0VBY3l4aTRCYVp4bQ=="
         val decoded = String(java.util.Base64.getUrlDecoder().decode(url))
         val headerFirst = "application/vnd.github+json"

        val service = UrlItemProvider.retrofitInstance?.create(ApiService::class.java)
        val call: Call<ResponseSearchRepos>? = service?.getQuestList(headerFirst, decoded, textInputSearch )
        call?.enqueue(object: Callback<ResponseSearchRepos> {
            override fun onResponse(
                call: Call<ResponseSearchRepos>,
                response: Response<ResponseSearchRepos>
            ) {
                _allSearchItems.postValue(response.body())
                statusApi.value = response.isSuccessful.toString()
                viewModelScope.launch {
                    response.body()?.let {
                        _sharedFlow.emit(it)
                    }
                }
            }
            override fun onFailure(call: Call<ResponseSearchRepos>, t: Throwable) {
                statusApi.value = call.isCanceled.toString()
                Toast.makeText(
                    application,
                    "Ops... connection error",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

 private fun sendProfile(login: String, application: Application) {
     val url = "dG9rZW4gZ2hwX1pkOEpiNGZ0SXc5YTJibWs4WjRZNlh2R0VBY3l4aTRCYVp4bQ=="
     val decoded = String(java.util.Base64.getUrlDecoder().decode(url))
     val headerFirst = "application/vnd.github+json"
     val service = UrlItemProvider.retrofitInstance?.create(ApiService::class.java)
        val call: Call<ResponseUserProfile>? = service?.getQuestListProfile(headerFirst, decoded, login)
        call?.enqueue(object: Callback<ResponseUserProfile> {
            override fun onResponse(
                call: Call<ResponseUserProfile>,
                response: Response<ResponseUserProfile>
            ) {
                responseObjectUserProfile = response.body()
                statusApiSecondFuel.value = response.isSuccessful.toString()
                _allUserInfo.postValue(response.body())

            }
            override fun onFailure(call: Call<ResponseUserProfile>, t: Throwable) {
                statusApiSecondFuel.value = call.isCanceled.toString()
                _allUserInfo.value = null
                Toast.makeText(
                    application,
                    "Ops... connection error",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

}