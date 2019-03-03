package com.rummi.luukvanagtmaal.grannykub

import android.content.res.Resources
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

class SettingsActivity : AppCompatActivity() {

//    var numAI: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // create spinners
        val res: Resources = resources
        val aiSpinner: Spinner = findViewById(R.id.aiSpinner)
        // create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(this, R.array.AIchoices,
            android.R.layout.simple_spinner_item)
            .also {adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                aiSpinner.adapter = adapter
            }
        aiSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

        }

        val colourSpinner: Spinner = findViewById(R.id.colourSpinner)
        ArrayAdapter.createFromResource(this, R.array.colour_choices,
            android.R.layout.simple_spinner_item)
            .also {adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                colourSpinner.adapter = adapter
            }
        colourSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p0?.getItemAtPosition(p2)!! == res.getStringArray(R.array.colour_choices)[p2]) {
                    val item = p0.getItemAtPosition(p2)
                    when (item.toString()) {
                        "Blue" -> {
                            setTheme(R.style.BlueTheme)
                        }
                        "GreyBrown" -> setTheme(R.style.AppTheme)
                        "White" -> setTheme(R.style.WhiteTheme)
                    }
                    Toast.makeText(applicationContext, "Background colour set to $item", Toast.LENGTH_SHORT).show()
//                    val colint = Intent(applicationContext, PlayActivity::class.java)
//                    colint.putExtra("com.rummi.luukvanagtmaal.grannykub.BCKCOLOUR", res.getStringArray(R.array.colour_choices)[p2])
//                    startActivity(colint)

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

    }
}


