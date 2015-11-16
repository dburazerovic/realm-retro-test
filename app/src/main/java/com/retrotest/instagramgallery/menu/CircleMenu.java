package com.retrotest.instagramgallery.menu;

import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;

/**
 *
 * Menu that should look like Pinterest menu
 *
 * Created by Damir Burazerovic on 8/8/15.
 */
public class CircleMenu extends FrameLayout {

    private Animation inAnimation;
    private Animation outAnimation;

    private final int ACTION_BAR_SIZE = 25;

    // Distance from touch point
    // NOTICE: Default for test purposes only - we can add this as component properties
    // that can be set from layout xml
    private int radius = 100;

    public CircleMenu(Context context) {
        super(context);
    }

    public CircleMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setInAnimation(Animation inAnimation) {
        this.inAnimation = inAnimation;
    }

    public void setOutAnimation(Animation outAnimation) {
        this.outAnimation = outAnimation;
    }

    @Override
    public void setVisibility(int visibility)
    {
        if (getVisibility() != visibility)
        {
            if (visibility == VISIBLE)
            {
                if (inAnimation != null) startAnimation(inAnimation);
            }
            else if ((visibility == INVISIBLE) || (visibility == GONE))
            {
                if (outAnimation != null) startAnimation(outAnimation);
            }
        }

        super.setVisibility(visibility);
    }


    /**
     * Depending of number of items and position of touched coords we will show menu
     *
     * @param view that is center of our menu
     */
    public void showMenuForView(View view) {

        setVisibility(GONE);

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        int dpRadius =  Math.round(radius * metrics.density);
        int actionBarSize = Math.round(ACTION_BAR_SIZE * metrics.density);

        // get view top left coords
        int[] viewCoords = new int[2];
        view.getLocationOnScreen(viewCoords);

        int halfWidth = view.getMeasuredWidth() / 2;
        int halfHeight = view.getMeasuredHeight() / 2;

        int centerX = viewCoords[0] + halfWidth;
        int centerY = viewCoords[1] - actionBarSize + halfHeight;

        RectF rect = new RectF(centerX-dpRadius, centerY - dpRadius, centerX + dpRadius, centerY + dpRadius);

        Path orbit = getMenuOrbitPath(rect);

        recalculateChildPosition(new PathMeasure(orbit, false));

        setVisibility(VISIBLE);
    }

    /**
     * Calculating position for every child
     *
     * NOTICE: calculation does not include size of menu (child) object and that should
     *         be added in next version
     *
     * @param measure
     */
    private void recalculateChildPosition(PathMeasure measure) {
        if (measure != null && getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View childView = getChildAt(i);

                float[] pathCoords = new float[] {0f, 0f};
                measure.getPosTan((i) * measure.getLength() / getChildCount(), pathCoords, null);

                // this whole calculation should have included dimensions of child object
                // but we will skip that for this test
                // by adding pre-draw listener we can measure object before drawing

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.NO_GRAVITY);
                params.setMargins(Math.round(pathCoords[0]), Math.round(pathCoords[1]), 0, 0);

                childView.setLayoutParams(params);
            }
        }
    }

    /**
     * Method will calculate orbit for menu
     * This is just temporarily solution since it should be more generic and use trigonometry
     * for correct solution
     *
     * @param rect rectangle where we will draw our arc
     * @return path with arc
     */
    private Path getMenuOrbitPath(RectF rect) {

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);

        RectF screenRect = new RectF(0, 0, displaySize.x, displaySize.y);

        Path orbit = new Path();
        if (screenRect.intersect(rect)) {

            if (screenRect.equals(rect)) {
                // screen middle
                orbit.addArc(rect, -145, 120);
            } else {
                if (rect.left < 0) {
                    // solving left use cases
                    if (rect.top < 0) {
                        // top left corner
                        orbit.addArc(rect, -20, 120);
                    } else {

                        if (rect.bottom > displaySize.y) {
                            // bottom left corner
                            orbit.addArc(rect, -105, 120);
                        } else {
                            // left side
                            orbit.addArc(rect, -60, 120);
                        }
                    }
                } else if (rect.right > displaySize.x) {
                    // solving right use cases
                    if (rect.top < 0) {
                        // top right corner
                        orbit.addArc(rect, 105, 120);
                    } else {

                        if (rect.bottom > displaySize.y) {
                            // bottom left corner
                            orbit.addArc(rect, 180, 120);
                        } else {
                            // left side
                            orbit.addArc(rect, 145, 120);
                        }
                    }
                } else if (rect.left > 0 && rect.right < displaySize.x && rect.top < 0) {
                    // solving problem for top row
                    orbit.addArc(rect, 45, 120);
                } else {
                    // solving bottom row
                    orbit.addArc(rect, -145, 120);
                }

            }
        }
        return orbit;

    }

}

