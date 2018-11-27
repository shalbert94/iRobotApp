package com.shalomhalbert.popup.irobotapp

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import top.defaults.colorpicker.ColorPickerPopup


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        postshowColorPicker()

//        val paintView = findViewById<PaintView>(R.id.paintView)

    }

    //Todo: Setup ViewModel for list of paths and current color
    //Todo: Setup color and clear menu options
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.clear) paintView.clear()

        return true
    }

    private fun showColorPicker() {
        val colorPickerPopup = ColorPickerPopup.Builder(this).apply {
            initialColor(Color.BLACK) //Set initial color
            enableBrightness(true) // Enable brightness slider or not
            enableAlpha(true) // Enable alpha slider or not
            okTitle(getString(R.string.pick_a_color))
            cancelTitle(getString(R.string.cancel_pick_a_color))
            showIndicator(true)
            showValue(true)
        }

        colorPickerPopup.build().show(object : ColorPickerPopup.ColorPickerObserver {
            override fun onColorPicked(color: Int) {
                paintView.updateColor(color)
            }

            override fun onColor(color: Int, fromUser: Boolean) { }
        })
    }

}
