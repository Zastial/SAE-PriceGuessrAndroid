package fr.etudiant.priceguessr.fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import fr.etudiant.priceguessr.Constants
import fr.etudiant.priceguessr.LoginActivity
import fr.etudiant.priceguessr.R
import fr.etudiant.priceguessr.Token
import org.json.JSONObject

class ProfilFragment() : Fragment() {

    private lateinit var loginText: TextView
    private lateinit var btnModifyPassword: Button
    private lateinit var btnLogout: Button
    private lateinit var btnDeleteAccount: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profil, container, false)

        loginText = view.findViewById(R.id.profil_page_section_personnal_info_user_login)
        btnModifyPassword = view.findViewById(R.id.profil_page_btn_modify_password)
        btnLogout = view.findViewById(R.id.profil_page_btn_logout)

        val userName = requireActivity().getSharedPreferences("tokenPreference", Context.MODE_PRIVATE)
            .getString("userName", "")

        loginText.text = userName


        btnModifyPassword.setOnClickListener {
            /* open custom dialog to modify password */
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.layout_dialog_custom_modify_password)
            val dialogInputPwd = dialog.findViewById<EditText>(R.id.profil_page_dialog_modify_password_input)
            val dialogBtnValidate = dialog.findViewById<Button>(R.id.profil_page_dialog_modify_password_btn_ok)
            val dialogBtnClose = dialog.findViewById<ImageButton>(R.id.profil_page_dialog_modify_password_btn_close)

            dialogBtnClose.setOnClickListener {
                dialog.dismiss()
            }

            dialogBtnValidate.setOnClickListener {
                if (dialogInputPwd.text.isEmpty()) {
                    Toast.makeText(requireContext(), getString(R.string.toast_enter_password), Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                val queue = Volley.newRequestQueue(context)
                val passwordRequest = object : StringRequest(
                    Request.Method.PUT,
                    Constants.API_BASE_URl + Constants.API_USER,
                    {response ->
                        Log.e("RESP", response.toString())
                        try {

                        } catch (e: Exception) {

                        }
                    },
                    {error ->
                        Log.e("ERR", error.networkResponse.allHeaders.toString())
                        if (error is VolleyError || error == null || error.networkResponse != null) {
                            Toast.makeText(context,getString(R.string.toast_unknown_error),Toast.LENGTH_SHORT).show()
                        } else {
                            try {
                                val errorCode = error.networkResponse.statusCode
                                val errorBody =
                                    JSONObject(error.networkResponse.data.decodeToString()).getString("message")

                                Log.e("ERR", error.toString())
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
                        header["Authorization"] = Token().getToken(requireActivity())
                        return header
                    }
                }
                queue.add(passwordRequest)
            }
            dialog.show()
        }




        btnLogout.setOnClickListener {
            Token().deleteToken(requireActivity())
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }


        btnDeleteAccount.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.layout_dialog_custom_delete_account)
            val btnValid = dialog.findViewById<Button>(R.id.profil_page_dialog_delete_account_btn_validate)
            val btnCancel = dialog.findViewById<Button>(R.id.profil_page_dialog_delete_account_btn_cancel)

            btnValid.setOnClickListener {


            }


            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }


        return view
    }

}