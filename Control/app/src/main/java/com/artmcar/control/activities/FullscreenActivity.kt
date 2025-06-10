package com.artmcar.control.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artmcar.control.R
import com.artmcar.control.databinding.ActivityFullscreenBinding
import com.artmcar.control.db.main.MainFields
import com.artmcar.control.fragments.AddFragment
import com.artmcar.control.fragments.ReadFragment
import com.artmcar.control.fragments.UpdateFragment

class FullscreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment_to_open = intent.getStringExtra("fragment_to_open")


        val set_fragment = when(fragment_to_open){
            "add" -> AddFragment()
            "update" -> UpdateFragment()
            "read" -> ReadFragment()
            else -> AddFragment()
        }

        val data = intent.getSerializableExtra("item_data") as? MainFields
        if (fragment_to_open == "read" ){
            val bundle = Bundle()
            bundle.putSerializable("item_data", data)
            set_fragment.arguments = bundle
        }
        supportFragmentManager.beginTransaction().replace(R.id.fs_frame_layout, set_fragment).commit()
    }
}