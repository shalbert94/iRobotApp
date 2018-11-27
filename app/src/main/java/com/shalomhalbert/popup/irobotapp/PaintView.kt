package com.shalomhalbert.popup.irobotapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.MotionEvent


/**
 * Stores several FingerPath objects inside an ArrayList
 */
class PaintView : View {

    //Todo: Make names non-hungarian
    //Todo: Condense into fewer lines and set access modifiers
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs,
            defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    companion object {
        private const val DEFAULT_PAINT_COLOR = Color.RED
        private const val BACKGROUND_COLOR = Color.WHITE
        private const val TOUCH_TOLERANCE = 4f //Minimum distance for painting
    }

    //Todo: Reduce the number of global variables
    private var prevX: Float = 0.toFloat()
    private var prevY: Float = 0.toFloat()
    private var currentColor: Int = DEFAULT_PAINT_COLOR
    private var path: Path? = null
    private val paths = arrayListOf<FingerPath>()

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(BACKGROUND_COLOR)

        val paint = Paint().apply {
            isAntiAlias = true //Smooths out the edges of what is being drawn
            style = Paint.Style.STROKE //Draw with strokes
            strokeJoin = Paint.Join.ROUND //Separate strokes are joined with a circular section
            strokeCap = Paint.Cap.ROUND //Beginning and end of stroke are rounded
            strokeWidth = FingerPath.BRUSH_SIZE
        }

        for (fingerPath in paths) {
            paint.color = fingerPath.color

            canvas.drawPath(fingerPath.path, paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val currentX = event.x
        val currentY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchDown(currentX, currentY)
            MotionEvent.ACTION_MOVE -> touchMove(currentX, currentY)
            MotionEvent.ACTION_UP -> touchUp()
        }

        return true
    }

    private fun touchDown(currentX: Float, currentY: Float) {
        path = Path() //New Path
        val fp = FingerPath(currentColor, path!!)
        paths.add(fp)

        path?.moveTo(currentX, currentY) //Change path's starting point to current position
        prevX = currentX
        prevY = currentY

        invalidate() //Triggers onDraw()
    }

    private fun touchMove(currentX: Float, currentY: Float) {
        val dx = Math.abs(currentX - prevX)
        val dy = Math.abs(currentY - prevY)

        if (dx < TOUCH_TOLERANCE || dy < TOUCH_TOLERANCE) return

        path?.quadTo(prevX, prevY, (currentX + prevX) / 2, (currentY + prevY) / 2)
        prevX = currentX
        prevY = currentY

        invalidate()
    }

    private fun touchUp() {
        path?.lineTo(prevX, prevY)

        invalidate()
    }

    //Todo: Call this
    fun clear() {
        paths.clear()
        invalidate()
    }

    //Todo: Call this
    fun updateColor(color: Int) {
        currentColor = color
    }

}