package com.charaminstra.pleon.login.ui

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.charaminstra.pleon.common.CLASS_NAME
import com.charaminstra.pleon.common.KAKAO_LOGIN_BTN_CLICK
import com.charaminstra.pleon.common.PHONE_LOGIN_BTN_CLICK
import com.charaminstra.pleon.common_ui.ErrorToast
import com.charaminstra.pleon.common_ui.InfoToast
import com.charaminstra.pleon.login.AuthViewModel
import com.charaminstra.pleon.login.R
import com.charaminstra.pleon.login.databinding.FragmentLoginBinding
import com.charaminstra.pleon.login.startHomeActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val TAG = javaClass.name
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var binding : FragmentLoginBinding
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        firebaseAnalytics= FirebaseAnalytics.getInstance(requireContext())
        navController = this.findNavController()
        initObservers()

        //spannable
        val foregroundSpan = ForegroundColorSpan(resources.getColor(com.charaminstra.pleon.common_ui.R.color.main_green_color))
        val string = SpannableString(binding.welcomeTv.text)
        string.setSpan(
            foregroundSpan,
            24,29, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val styleSpan = StyleSpan(Typeface.BOLD)
        string.setSpan(
            styleSpan,
            24,29, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.welcomeTv.text = string
//        binding.startBtn.setOnClickListener {
//            startHomeActivity(requireContext())
//        }

        binding.phoneLoginBtn.setOnClickListener {
            navController.navigate(R.id.login_fragment_to_phone_fragment)
            // logging
            val loggingBundle = Bundle()
            loggingBundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(PHONE_LOGIN_BTN_CLICK ,loggingBundle)
        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            Log.e(TAG, "카카오계정 token"+ token.toString())
            Log.e(TAG, "카카오계정 error"+error.toString())
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                viewModel.postKakaoToken(token.accessToken)
            }
        }

        binding.kakaoLoginBtn.setOnClickListener {
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context!!)) {
                UserApiClient.instance.loginWithKakaoTalk(context!!) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(context!!, callback = callback)
                    } else if (token != null) {
                        Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                        viewModel.postKakaoToken(token.accessToken)
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context!!, callback = callback)
            }
            // logging
            val loggingBundle = Bundle()
            loggingBundle.putString(CLASS_NAME, TAG)
            firebaseAnalytics.logEvent(KAKAO_LOGIN_BTN_CLICK ,loggingBundle)
        }
        return binding.root
    }

    private fun initObservers(){
        viewModel.success.observe(viewLifecycleOwner, Observer {
            if(!it){
                ErrorToast(requireContext()).showMsgUp(resources.getString(R.string.login_fail_msg),binding.kakaoLoginBtn.y)
            }else{
            }
        })
        viewModel.userExist.observe(viewLifecycleOwner, Observer {
            if(it){
                InfoToast(requireContext()).showMsgUp(resources.getString(R.string.login_success_msg),binding.kakaoLoginBtn.y)
                /* 기존 회원 */
                viewModel.postLogin()
            }else{
                /* 신규 회원 */
                val direction = LoginFragmentDirections.loginFragmentToNicknameFragment(
                    "kakao"
                )
                navController.navigate(direction)
            }
        })
        viewModel.setTokenSuccess.observe(viewLifecycleOwner, Observer {
            if(it){
                startHomeActivity(requireContext())
                activity?.finish()
            }
        })
    }
}