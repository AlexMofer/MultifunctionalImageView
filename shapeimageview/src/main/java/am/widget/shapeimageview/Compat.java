package am.widget.shapeimageview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * 版本兼容控制器
 *
 */
class Compat {

    private interface CompatPlusImpl {
        int getPaddingStart(View view);

        int getPaddingEnd(View view);

        Drawable getDrawable(Context context, int id);

        void setHotspot(Drawable drawable, float x, float y);

        void invalidateOutline(View view);
    }

    private static class BaseCompatPlusImpl implements CompatPlusImpl {

        @Override
        public int getPaddingStart(View view) {
            return view.getPaddingLeft();
        }

        @Override
        public int getPaddingEnd(View view) {
            return view.getPaddingRight();
        }

        @Override
        @SuppressWarnings("all")
        public Drawable getDrawable(Context context, int id) {
            return context.getResources().getDrawable(id);
        }

        @Override
        public void setHotspot(Drawable drawable, float x, float y) {
            // do nothing
        }

        @Override
        public void invalidateOutline(View view) {
            // do nothing
        }
    }

    @TargetApi(17)
    private static class JbMr1CompatPlusImpl extends BaseCompatPlusImpl {
        @Override
        public int getPaddingStart(View view) {
            return view.getPaddingStart();
        }

        @Override
        public int getPaddingEnd(View view) {
            return view.getPaddingEnd();
        }
    }

    @TargetApi(21)
    private static class LollipopCompatPlusImpl extends JbMr1CompatPlusImpl {

        @Override
        public Drawable getDrawable(Context context, int id) {
            return context.getDrawable(id);
        }

        @Override
        public void setHotspot(Drawable drawable, float x, float y) {
            drawable.setHotspot(x, y);
        }

        @Override
        public void invalidateOutline(View view) {
            view.invalidateOutline();
        }
    }

    private static final CompatPlusImpl IMPL;

    static {
        final int version = android.os.Build.VERSION.SDK_INT;
        if (version >= 21) {
            IMPL = new LollipopCompatPlusImpl();
        } else if (version >= 17) {
            IMPL = new JbMr1CompatPlusImpl();
        } else {
            IMPL = new BaseCompatPlusImpl();
        }
    }

    static int getPaddingStart(View view) {
        return IMPL.getPaddingStart(view);
    }

    static int getPaddingEnd(View view) {
        return IMPL.getPaddingEnd(view);
    }

    static Drawable getDrawable(Context context, int id) {
        return IMPL.getDrawable(context, id);
    }

    static void setHotspot(Drawable drawable, float x, float y) {
        IMPL.setHotspot(drawable, x, y);
    }

    static void invalidateOutline(View view) {
        IMPL.invalidateOutline(view);
    }
}
