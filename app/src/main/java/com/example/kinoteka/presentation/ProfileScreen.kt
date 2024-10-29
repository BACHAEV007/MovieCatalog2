package com.example.kinoteka.presentation

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.kinoteka.R
import com.example.kinoteka.data.datasource.TokenDataSource
import com.example.kinoteka.data.mapper.NetworkMapper
import com.example.kinoteka.data.network.api.RetrofitApiClient
import com.example.kinoteka.databinding.FeedScreenBinding
import com.example.kinoteka.databinding.ProfileScreenBinding
import com.example.kinoteka.domain.model.Gender
import com.example.kinoteka.presentation.factory.FeedViewModelFactory
import com.example.kinoteka.presentation.factory.LoginViewModelFactory
import com.example.kinoteka.presentation.factory.MoviesViewModelFactory
import com.example.kinoteka.presentation.factory.ProfileViewModelFactory
import com.example.kinoteka.presentation.mapper.MoviesMapper
import com.example.kinoteka.presentation.mapper.ProfileMapper
import com.example.kinoteka.presentation.viewmodel.FeedViewModel
import com.example.kinoteka.presentation.viewmodel.LoginViewModel
import com.example.kinoteka.presentation.viewmodel.MoviesViewModel
import com.example.kinoteka.presentation.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.util.Calendar

class ProfileScreen : Fragment(R.layout.profile_screen) {
    private var binding: ProfileScreenBinding? = null
    private lateinit var viewModel: ProfileViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ProfileScreenBinding.bind(view)
        viewModel = createViewModel()
        viewModel.fetchProfileInfo()
        lifecycleScope.launch {
            viewModel.profileContent.collect { profile ->
                with(binding) {
                    val isMale = profile.gender.ordinal == Gender.MALE.ordinal
                    binding?.maleButton?.isSelected = isMale
                    binding?.femaleButton?.isSelected = !isMale
                    profile.avatarLink?.takeIf { it.isNotEmpty() }?.let { link ->
                        Picasso.get().load(link).into(this!!.avatar)
                    }
                    profile.name.let { this!!.nameEditText.setText(it) }
                    profile.name.let { this!!.greetingName.text = it }
                    profile.nickName.let { this!!.loginEditText.setText(it) }
                    profile.email.let { this!!.emailEditText.setText(it) }
                    profile.birthDate.let { this!!.birthdayCalendarEditText.setText(it) }
                }
            }
        }
        binding!!.greetingText.text = getGreetingMessage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    fun getGreetingMessage(): String {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when {
            currentHour in 6..11 -> getString(R.string.good_morning)
            currentHour in 12..17 -> getString(R.string.good_day)
            currentHour in 18..23 -> getString(R.string.good_evening)
            else -> getString(R.string.good_night)
        }
    }

    private fun createViewModel(): ProfileViewModel {
        val tokenDataSource = TokenDataSource(requireContext())
        val networkMapper = NetworkMapper()
        val profileApiService = RetrofitApiClient.createProfileApi(tokenDataSource)
        val profileMapper = ProfileMapper()
        val factory = ProfileViewModelFactory(
            profileApiService = profileApiService,
            networkMapper = networkMapper,
            profileMapper = profileMapper
        )
        return ViewModelProvider(this, factory)[ProfileViewModel::class.java]
    }
}