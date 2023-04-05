package fr.etudiant.priceguessr.fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import fr.etudiant.priceguessr.*
import fr.etudiant.priceguessr.models.Token
import org.json.JSONObject

class ProfilFragment() : Fragment() {

    private lateinit var loginText: TextView
    private lateinit var btnLogout: Button
    private lateinit var btnDeleteAccount: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profil, container, false)

        loginText = view.findViewById(R.id.profil_page_section_personnal_info_user_login)
        btnLogout = view.findViewById(R.id.profil_page_btn_logout)
        btnDeleteAccount = view.findViewById(R.id.profil_page_btn_delete_account)

        val userName = requireActivity().getSharedPreferences("tokenPreference", Context.MODE_PRIVATE)
            .getString("userName", "")

        loginText.text = userName
        val queue = Volley.newRequestQueue(context)

        view.findViewById<CardView>(R.id.profil_page_section_password_cardView).setOnClickListener {
            /* open custom dialog to modify password */
            val dialogPwd = Dialog(requireContext())
            dialogPwd.setContentView(R.layout.dialog_custom_modify_password)
            val dialogInputPwd = dialogPwd.findViewById<EditText>(R.id.profil_page_dialog_modify_password_input)
            val dialogBtnValidate = dialogPwd.findViewById<Button>(R.id.profil_page_dialog_modify_password_btn_ok)
            val dialogBtnClose = dialogPwd.findViewById<ImageButton>(R.id.profil_page_dialog_modify_password_btn_close)
            val btnPasswordVisibility = dialogPwd.findViewById<ImageButton>(R.id.profil_page_dialog_modify_password_btn_visibility)


            btnPasswordVisibility.setOnClickListener {
                // 131073 = password hidden
                // 225 = password visible
                val inputType = dialogInputPwd.inputType
                if (inputType == 131073) {
                    dialogInputPwd.inputType = 225
                } else {
                    dialogInputPwd.inputType = 131073
                }
            }


            /* modify password validation */
            dialogBtnValidate.setOnClickListener {
                val password = dialogInputPwd.text.toString()
                if (password.isEmpty()) {
                    Toast.makeText(requireContext(), getString(R.string.toast_enter_password), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val passwordRequest = object : StringRequest(
                    Request.Method.PUT,
                    Constants.API_BASE_URl + Constants.API_USER,
                    {response ->
                        try {
                            //TODO handle request ?
                            val body = JSONObject(response)
                            val newToken = body.get("jwt")
                            Token().setToken(requireActivity(), newToken.toString())
                            dialogPwd.dismiss()
                            startLoginActivity()
                        } catch (e: Exception) {
                            Toast.makeText(context, getString(R.string.api_connection_error), Toast.LENGTH_SHORT).show()
                        }
                    },
                    {error ->
                        if (error is VolleyError || error == null || error.networkResponse != null) {
                            Toast.makeText(context,getString(R.string.toast_unknown_error),Toast.LENGTH_SHORT).show()
                        } else {
                            try {
                                val errorCode = error.networkResponse.statusCode
                                val errorBody =
                                    JSONObject(error.networkResponse.data.decodeToString()).getString("message")
                                when (errorCode) {
                                    400 -> {Toast.makeText(context, errorBody, Toast.LENGTH_SHORT).show()}
                                    401 -> {Toast.makeText(context, getString(R.string.toast_unknown_error), Toast.LENGTH_SHORT).show()}
                                    else -> Toast.makeText(context, errorBody, Toast.LENGTH_SHORT).show()
                                }

                            } catch (e: Exception) {
                                Toast.makeText(context,getString(R.string.toast_unknown_error),Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                ) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val header = mutableMapOf<String,String>()
                        header[Constants.HEADER_TOKEN_AUTHORIZATION] = Token().getToken(requireActivity())
                        return header
                    }

                    override fun getParams(): MutableMap<String, String> {
                        val params = mutableMapOf<String, String>()
                        params["password"] = password
                        return params
                    }
                }
                queue.add(passwordRequest)
            }

            dialogBtnClose.setOnClickListener {
                dialogPwd.dismiss()
            }

            dialogPwd.show()
        }


        btnLogout.setOnClickListener {
            Token().deleteToken(requireActivity())
            startLoginActivity()
        }


        btnDeleteAccount.setOnClickListener {
            val dialogAccount = Dialog(requireContext())
            dialogAccount.setContentView(R.layout.dialog_custom_delete_account)
            val btnValid = dialogAccount.findViewById<Button>(R.id.profil_page_dialog_delete_account_btn_validate)
            val btnCancel = dialogAccount.findViewById<Button>(R.id.profil_page_dialog_delete_account_btn_cancel)

            /* request to delete account */
            btnValid.setOnClickListener {

                val deleteAccoutRequest = object : StringRequest(
                    Request.Method.DELETE,
                Constants.API_BASE_URl + Constants.API_USER,
                    {response ->
                        //TODO handle response ?
                        //val body = JSONObject(response)
                        Token().deleteToken(requireActivity())
                        dialogAccount.dismiss()
                        startLoginActivity()
                    },
                    {error ->
                        if (error is VolleyError || error == null || error.networkResponse != null) {
                            Toast.makeText(context,getString(R.string.toast_unknown_error),Toast.LENGTH_SHORT).show()
                        } else {
                            try {
                                val errorCode = error.networkResponse.statusCode
                                val errorBody =
                                    JSONObject(error.networkResponse.data.decodeToString()).getString(
                                        "message"
                                    )
                                when (errorCode) {
                                    400 -> {
                                        Toast.makeText(context, errorBody, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                    401 -> {
                                        Toast.makeText(
                                            context,
                                            getString(R.string.toast_unknown_error),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    else -> Toast.makeText(context, errorBody, Toast.LENGTH_SHORT)
                                        .show()
                                }

                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    getString(R.string.toast_unknown_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val header = mutableMapOf<String, String>()
                        header[Constants.HEADER_TOKEN_AUTHORIZATION] = Token().getToken(requireActivity())
                        return header
                    }
                }
                queue.add(deleteAccoutRequest)
            }
            btnCancel.setOnClickListener {
                dialogAccount.dismiss()
            }
            dialogAccount.show()
        }


        return view
    }

    /**
     * startLoginActivity
     * kill all previous activity
     */
    private fun startLoginActivity() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}