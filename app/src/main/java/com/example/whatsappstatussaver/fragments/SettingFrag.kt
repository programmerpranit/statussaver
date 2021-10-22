package com.example.whatsappstatussaver.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.fragment.app.findFragment
import com.example.whatsappstatussaver.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.switchmaterial.SwitchMaterial
import androidx.constraintlayout.widget.ConstraintLayout as ConstraintLayout

class SettingFrag : Fragment(R.layout.fragment_setting) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dwa = view.findViewById<SwitchMaterial>(R.id.dwaSwitch)

        val sharedPref = requireContext().getSharedPreferences("dwa", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val bool = sharedPref.getBoolean("dwaBool", true)

        dwa.isChecked = bool

        dwa.setOnClickListener{
            val dwaBool = dwa.isChecked
            editor.apply{
                putBoolean("dwaBool", dwaBool)
                apply()
            }

        }
        view.findViewById<TextView>(R.id.privacyPolicyButton).setOnClickListener {
            val sheet = layoutInflater.inflate(R.layout.fragment_bs_privacy_policy, null)
            val bsDialog = BottomSheetDialog(requireContext())
            bsDialog.setContentView(sheet)
            bsDialog.show()
        }
    }
}
