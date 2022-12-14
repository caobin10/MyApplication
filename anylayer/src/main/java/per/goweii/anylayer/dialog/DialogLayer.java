package per.goweii.anylayer.dialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import per.goweii.anylayer.DecorLayer;
import per.goweii.anylayer.GlobalConfig;
import per.goweii.anylayer.Layer;
import per.goweii.anylayer.R;
import per.goweii.anylayer.utils.AnimatorHelper;
import per.goweii.anylayer.utils.SoftInputHelper;
import per.goweii.anylayer.utils.Utils;
import per.goweii.anylayer.widget.SwipeLayout;
import per.goweii.burred.Blurred;

/**
 * @author CuiZhen
 * @date 2019/3/10
 * QQ: 302833254
 * E-mail: goweii@163.com
 * GitHub: https://github.com/goweii
 */
public class DialogLayer extends DecorLayer {

    private final long mAnimDurDef = GlobalConfig.get().dialogAnimDuration;
    private final float mDimAmountDef = GlobalConfig.get().dialogDimAmount;

    private SoftInputHelper mSoftInputHelper = null;

    public DialogLayer(@NonNull Context context) {
        this(Utils.requireActivity(context));
    }

    public DialogLayer(@NonNull Activity activity) {
        super(activity);
        cancelableOnKeyBack(true);
    }

    @IntRange(from = 0)
    @Override
    protected int getLevel() {
        return Level.DIALOG;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder() {
        return new ViewHolder();
    }

    @NonNull
    @Override
    public ViewHolder getViewHolder() {
        return (ViewHolder) super.getViewHolder();
    }

    @NonNull
    @Override
    protected Config onCreateConfig() {
        return new Config();
    }

    @NonNull
    @Override
    public Config getConfig() {
        return (Config) super.getConfig();
    }

    @NonNull
    @Override
    protected ListenerHolder onCreateListenerHolder() {
        return new ListenerHolder();
    }

    @NonNull
    @Override
    public ListenerHolder getListenerHolder() {
        return (ListenerHolder) super.getListenerHolder();
    }

    @NonNull
    @Override
    protected View onCreateChild(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        if (getViewHolder().getChildNullable() == null) {
            ContainerLayout container = (ContainerLayout) inflater.inflate(R.layout.anylayer_dialog_layer, parent, false);
            getViewHolder().setChild(container);
            getViewHolder().setContent(onCreateContent(inflater, getViewHolder().getContentWrapper()));
            ViewGroup.LayoutParams layoutParams = getViewHolder().getContent().getLayoutParams();
            FrameLayout.LayoutParams contentParams;
            if (layoutParams == null) {
                contentParams = generateContentDefaultLayoutParams();
            } else if (layoutParams instanceof FrameLayout.LayoutParams) {
                contentParams = (FrameLayout.LayoutParams) layoutParams;
            } else {
                contentParams = new FrameLayout.LayoutParams(layoutParams.width, layoutParams.height);
            }
            getViewHolder().getContent().setLayoutParams(contentParams);
            getViewHolder().getContentWrapper().addView(getViewHolder().getContent());
        }
        return getViewHolder().getChild();
    }

    @NonNull
    protected View onCreateContent(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        if (getViewHolder().getContentNullable() == null) {
            getViewHolder().setContent(inflater.inflate(getConfig().mContentViewId, parent, false));
        } else {
            ViewGroup contentParent = (ViewGroup) getViewHolder().getContent().getParent();
            if (contentParent != null) {
                contentParent.removeView(getViewHolder().getContent());
            }
        }
        return getViewHolder().getContent();
    }

    @Nullable
    @Override
    protected Animator onCreateInAnimator(@NonNull View view) {
        Animator backgroundAnimator = onCreateBackgroundInAnimator(getViewHolder().getBackground());
        Animator contentAnimator = onCreateContentInAnimator(getViewHolder().getContent());
        if (backgroundAnimator == null && contentAnimator == null) return null;
        if (backgroundAnimator == null) return contentAnimator;
        if (contentAnimator == null) return backgroundAnimator;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(backgroundAnimator, contentAnimator);
        return animatorSet;
    }

    @Nullable
    protected Animator onCreateBackgroundInAnimator(@NonNull View view) {
        Animator backgroundAnimator;
        if (getConfig().mBackgroundAnimatorCreator != null) {
            backgroundAnimator = getConfig().mBackgroundAnimatorCreator.createInAnimator(view);
        } else {
            backgroundAnimator = onCreateDefBackgroundInAnimator(view);
        }
        return backgroundAnimator;
    }

    @NonNull
    protected Animator onCreateDefBackgroundInAnimator(@NonNull View view) {
        Animator animator = null;
        if (GlobalConfig.get().dialogBackgroundAnimatorCreator != null) {
            animator = GlobalConfig.get().dialogBackgroundAnimatorCreator.createInAnimator(view);
        }
        if (animator == null) {
            animator = AnimatorHelper.createAlphaInAnim(view);
            animator.setDuration(mAnimDurDef);
        }
        return animator;
    }

    @Nullable
    protected Animator onCreateContentInAnimator(@NonNull View view) {
        Animator contentAnimator;
        if (getConfig().mContentAnimatorCreator != null) {
            contentAnimator = getConfig().mContentAnimatorCreator.createInAnimator(view);
        } else {
            if (getConfig().mAnimStyle != null) {
                switch (getConfig().mAnimStyle) {
                    case ALPHA:
                        contentAnimator = AnimatorHelper.createAlphaInAnim(view);
                        break;
                    case ZOOM:
                        contentAnimator = AnimatorHelper.createZoomInAnim(view);
                        break;
                    case LEFT:
                        contentAnimator = AnimatorHelper.createLeftInAnim(view);
                        break;
                    case RIGHT:
                        contentAnimator = AnimatorHelper.createRightInAnim(view);
                        break;
                    case TOP:
                        contentAnimator = AnimatorHelper.createTopInAnim(view);
                        break;
                    case BOTTOM:
                        contentAnimator = AnimatorHelper.createBottomInAnim(view);
                        break;
                    default:
                        contentAnimator = onCreateDefContentInAnimator(view);
                        break;
                }
            } else {
                int swipeDirection = getConfig().mSwipeDirection;
                if ((swipeDirection & SwipeLayout.Direction.LEFT) != 0) {
                    contentAnimator = AnimatorHelper.createLeftInAnim(view);
                } else if ((swipeDirection & SwipeLayout.Direction.TOP) != 0) {
                    contentAnimator = AnimatorHelper.createTopInAnim(view);
                } else if ((swipeDirection & SwipeLayout.Direction.RIGHT) != 0) {
                    contentAnimator = AnimatorHelper.createRightInAnim(view);
                } else if ((swipeDirection & SwipeLayout.Direction.BOTTOM) != 0) {
                    contentAnimator = AnimatorHelper.createBottomInAnim(view);
                } else {
                    contentAnimator = onCreateDefContentInAnimator(view);
                }
            }
            contentAnimator.setDuration(mAnimDurDef);
        }
        return contentAnimator;
    }

    @NonNull
    protected Animator onCreateDefContentInAnimator(@NonNull View view) {
        Animator animator = null;
        if (GlobalConfig.get().dialogContentAnimatorCreator != null) {
            animator = GlobalConfig.get().dialogContentAnimatorCreator.createInAnimator(view);
        }
        if (animator == null) {
            animator = AnimatorHelper.createZoomAlphaInAnim(view);
            animator.setDuration(mAnimDurDef);
        }
        return animator;
    }

    @Nullable
    @Override
    protected Animator onCreateOutAnimator(@NonNull View view) {
        Animator backgroundAnimator = onCreateBackgroundOutAnimator(getViewHolder().getBackground());
        Animator contentAnimator = onCreateContentOutAnimator(getViewHolder().getContent());
        if (backgroundAnimator == null && contentAnimator == null) return null;
        if (backgroundAnimator == null) return contentAnimator;
        if (contentAnimator == null) return backgroundAnimator;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(backgroundAnimator, contentAnimator);
        return animatorSet;
    }

    @Nullable
    protected Animator onCreateBackgroundOutAnimator(@NonNull View view) {
        Animator backgroundAnimator;
        if (getConfig().mBackgroundAnimatorCreator != null) {
            backgroundAnimator = getConfig().mBackgroundAnimatorCreator.createOutAnimator(view);
        } else {
            backgroundAnimator = onCreateDefBackgroundOutAnimator(view);
        }
        return backgroundAnimator;
    }

    @NonNull
    protected Animator onCreateDefBackgroundOutAnimator(@NonNull View view) {
        Animator animator = null;
        if (GlobalConfig.get().dialogBackgroundAnimatorCreator != null) {
            animator = GlobalConfig.get().dialogBackgroundAnimatorCreator.createOutAnimator(view);
        }
        if (animator == null) {
            animator = AnimatorHelper.createAlphaOutAnim(view);
            animator.setDuration(mAnimDurDef);
        }
        return animator;
    }

    @Nullable
    protected Animator onCreateContentOutAnimator(@NonNull View view) {
        Animator contentAnimator;
        if (getConfig().mContentAnimatorCreator != null) {
            contentAnimator = getConfig().mContentAnimatorCreator.createOutAnimator(view);
        } else {
            if (getConfig().mAnimStyle != null) {
                switch (getConfig().mAnimStyle) {
                    case ALPHA:
                        contentAnimator = AnimatorHelper.createAlphaOutAnim(view);
                        break;
                    case ZOOM:
                        contentAnimator = AnimatorHelper.createZoomOutAnim(view);
                        break;
                    case LEFT:
                        contentAnimator = AnimatorHelper.createLeftOutAnim(view);
                        break;
                    case RIGHT:
                        contentAnimator = AnimatorHelper.createRightOutAnim(view);
                        break;
                    case TOP:
                        contentAnimator = AnimatorHelper.createTopOutAnim(view);
                        break;
                    case BOTTOM:
                        contentAnimator = AnimatorHelper.createBottomOutAnim(view);
                        break;
                    default:
                        contentAnimator = onCreateDefContentOutAnimator(view);
                        break;
                }
            } else {
                int swipeDirection = getConfig().mSwipeDirection;
                if ((swipeDirection & SwipeLayout.Direction.LEFT) != 0) {
                    contentAnimator = AnimatorHelper.createLeftOutAnim(view);
                } else if ((swipeDirection & SwipeLayout.Direction.TOP) != 0) {
                    contentAnimator = AnimatorHelper.createTopOutAnim(view);
                } else if ((swipeDirection & SwipeLayout.Direction.RIGHT) != 0) {
                    contentAnimator = AnimatorHelper.createRightOutAnim(view);
                } else if ((swipeDirection & SwipeLayout.Direction.BOTTOM) != 0) {
                    contentAnimator = AnimatorHelper.createBottomOutAnim(view);
                } else {
                    contentAnimator = onCreateDefContentOutAnimator(view);
                }
            }
            contentAnimator.setDuration(mAnimDurDef);
        }
        return contentAnimator;
    }

    @NonNull
    protected Animator onCreateDefContentOutAnimator(@NonNull View view) {
        Animator animator = null;
        if (GlobalConfig.get().dialogContentAnimatorCreator != null) {
            animator = GlobalConfig.get().dialogContentAnimatorCreator.createOutAnimator(view);
        }
        if (animator == null) {
            animator = AnimatorHelper.createZoomAlphaOutAnim(view);
            animator.setDuration(mAnimDurDef);
        }
        return animator;
    }

    @NonNull
    protected FrameLayout.LayoutParams generateContentDefaultLayoutParams() {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onAttach() {
        super.onAttach();
        initContent();
        initBackground();
        initContainer();
    }

    @Override
    public void onPreDraw() {
        super.onPreDraw();
    }

    @Override
    public void onShow() {
        super.onShow();
    }

    @Override
    public void onPreRemove() {
        super.onPreRemove();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getViewHolder().recycle();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Utils.getViewSize(getViewHolder().getBackground(), new Runnable() {
            @Override
            public void run() {
                fitContainerToActivityContent();
            }
        });
    }

    protected void initContainer() {
        if (getConfig().mOutsideInterceptTouchEvent) {
            getViewHolder().getChild().setClickable(true);
            if (getConfig().mCancelableOnTouchOutside) {
                getViewHolder().getChild().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
            }
        } else {
            getViewHolder().getChild().setOnClickListener(null);
            getViewHolder().getChild().setClickable(false);
        }
        if (getConfig().mOutsideTouchedToDismiss || getConfig().mOutsideTouchedListener != null) {
            getViewHolder().getChild().setOnTouchedListener(new ContainerLayout.OnTouchedListener() {
                @Override
                public void onTouched() {
                    if (getConfig().mOutsideTouchedToDismiss) {
                        dismiss();
                    }
                    if (getConfig().mOutsideTouchedListener != null) {
                        getConfig().mOutsideTouchedListener.outsideTouched();
                    }
                }
            });
        }
        fitContainerToActivityContent();
        FrameLayout.LayoutParams contentWrapperParams = (FrameLayout.LayoutParams) getViewHolder().getContentWrapper().getLayoutParams();
        contentWrapperParams.width = FrameLayout.LayoutParams.MATCH_PARENT;
        contentWrapperParams.height = FrameLayout.LayoutParams.MATCH_PARENT;
        getViewHolder().getContentWrapper().setLayoutParams(contentWrapperParams);
        if (getConfig().mAvoidStatusBar) {
            getViewHolder().getContentWrapper().setPadding(0, Utils.getStatusBarHeight(getActivity()), 0, 0);
            getViewHolder().getContentWrapper().setClipToPadding(false);
        } else {
            getViewHolder().getContentWrapper().setPadding(0, 0, 0, 0);
            getViewHolder().getContentWrapper().setClipToPadding(true);
        }
        getViewHolder().getContentWrapper().setSwipeDirection(getConfig().mSwipeDirection);
        getViewHolder().getContentWrapper().setOnSwipeListener(new SwipeLayout.OnSwipeListener() {
            @Override
            public void onStart() {
                if (getConfig().mSwipeTransformer == null) {
                    getConfig().mSwipeTransformer = new SwipeTransformer() {
                        @Override
                        public void onDragging(@NonNull View content, @NonNull View background, float f) {
                            background.setAlpha(1F - f);
                        }
                    };
                }
                getListenerHolder().notifyOnSwipeStart(DialogLayer.this);
            }

            @Override
            public void onSwiping(@SwipeLayout.Direction int direction, @FloatRange(from = 0F, to = 1F) float fraction) {
                if (getConfig().mSwipeTransformer != null) {
                    getConfig().mSwipeTransformer.onDragging(getViewHolder().getContent(), getViewHolder().getBackground(), fraction);
                }
                getListenerHolder().notifyOnSwiping(DialogLayer.this, direction, fraction);
            }

            @Override
            public void onEnd(@SwipeLayout.Direction int direction) {
                getListenerHolder().notifyOnSwipeEnd(DialogLayer.this, direction);
                // ?????????????????????????????????removeView??????????????????dispatchDraw????????????
                // ????????????????????????viewGroup???childCount???????????????-1???????????????view??????
                getViewHolder().getContentWrapper().setVisibility(View.INVISIBLE);
                getViewHolder().getContentWrapper().post(new Runnable() {
                    @Override
                    public void run() {
                        dismiss(false);
                    }
                });
            }
        });
        getViewHolder().getContentWrapper().setVisibility(View.VISIBLE);
    }

    private void fitContainerToActivityContent() {
        final int dh = getViewHolder().getDecor().getHeight();
        final int dw = getViewHolder().getDecor().getWidth();
        final int[] dl = new int[2];
        getViewHolder().getDecor().getLocationOnScreen(dl);
        final int ach = getViewHolder().getActivityContent().getHeight();
        final int acw = getViewHolder().getActivityContent().getWidth();
        final int[] acl = new int[2];
        getViewHolder().getActivityContent().getLocationOnScreen(acl);
        FrameLayout.LayoutParams containerParams = (FrameLayout.LayoutParams) getViewHolder().getChild().getLayoutParams();
        containerParams.leftMargin = 0/*acl[0] - dl[0]*/;
        containerParams.topMargin = 0/*acl[1] - dl[1]*/;
        containerParams.rightMargin = dl[0] + dw - (acl[0] + acw);
        containerParams.bottomMargin = dl[1] + dh - (acl[1] + ach);
        getViewHolder().getChild().setLayoutParams(containerParams);
    }

    protected void initBackground() {
        if (getConfig().mBackgroundBlurPercent > 0 || getConfig().mBackgroundBlurRadius > 0) {
            Utils.getViewSize(getViewHolder().getBackground(), new Runnable() {
                @Override
                public void run() {
                    float radius = getConfig().mBackgroundBlurRadius;
                    if (getConfig().mBackgroundBlurPercent > 0) {
                        int w = getViewHolder().getBackground().getWidth();
                        int h = getViewHolder().getBackground().getHeight();
                        int min = Math.min(w, h);
                        radius = min * getConfig().mBackgroundBlurPercent;
                    }
                    float scale = getConfig().mBackgroundBlurScale;
                    if (radius > 25) {
                        scale = scale * (radius / 25);
                        radius = 25;
                    }
                    Bitmap snapshot = Utils.snapshot(getViewHolder().getDecor(),
                            getViewHolder().getBackground(),
                            scale,
                            getViewHolder().getParent(),
                            getViewHolder().getChild());
                    Blurred.init(getActivity());
                    Bitmap blurBitmap = Blurred.with(snapshot)
                            .recycleOriginal(true)
                            .keepSize(false)
                            .radius(radius)
                            .blur();
                    getViewHolder().getBackground().setScaleType(ImageView.ScaleType.CENTER_CROP);
                    getViewHolder().getBackground().setImageBitmap(blurBitmap);
                    if (getConfig().mBackgroundColor != Color.TRANSPARENT) {
                        getViewHolder().getBackground().setColorFilter(getConfig().mBackgroundColor);
                    }
                }
            });
        } else {
            if (getConfig().mBackgroundBitmap != null) {
                getViewHolder().getBackground().setImageBitmap(getConfig().mBackgroundBitmap);
                if (getConfig().mBackgroundColor != Color.TRANSPARENT) {
                    getViewHolder().getBackground().setColorFilter(getConfig().mBackgroundColor);
                }
            } else if (getConfig().mBackgroundDrawable != null) {
                getViewHolder().getBackground().setImageDrawable(getConfig().mBackgroundDrawable);
                if (getConfig().mBackgroundColor != Color.TRANSPARENT) {
                    getViewHolder().getBackground().setColorFilter(getConfig().mBackgroundColor);
                }
            } else if (getConfig().mBackgroundResource != -1) {
                getViewHolder().getBackground().setImageResource(getConfig().mBackgroundResource);
                if (getConfig().mBackgroundColor != Color.TRANSPARENT) {
                    getViewHolder().getBackground().setColorFilter(getConfig().mBackgroundColor);
                }
            } else if (getConfig().mBackgroundColor != Color.TRANSPARENT) {
                getViewHolder().getBackground().setImageDrawable(new ColorDrawable(getConfig().mBackgroundColor));
            } else if (getConfig().mBackgroundDimAmount != -1) {
                int color = Color.argb((int) (255 * Utils.floatRange01(getConfig().mBackgroundDimAmount)), 0, 0, 0);
                getViewHolder().getBackground().setImageDrawable(new ColorDrawable(color));
            } else {
                getViewHolder().getBackground().setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
    }

    protected void initContent() {
        getViewHolder().getContent().setClickable(true);
        FrameLayout.LayoutParams contentParams = (FrameLayout.LayoutParams) getViewHolder().getContent().getLayoutParams();
        if (getConfig().mGravity != -1) {
            contentParams.gravity = getConfig().mGravity;
        }
        getViewHolder().getContent().setLayoutParams(contentParams);
        if (getConfig().mAsStatusBarViewId > 0) {
            View statusBar = getViewHolder().getContent().findViewById(getConfig().mAsStatusBarViewId);
            if (statusBar != null) {
                ViewGroup.LayoutParams params = statusBar.getLayoutParams();
                params.height = Utils.getStatusBarHeight(getActivity());
                statusBar.setLayoutParams(params);
                statusBar.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * ??????????????????View
     *
     * @param contentView ?????????View
     */
    @NonNull
    public DialogLayer contentView(@NonNull View contentView) {
        getViewHolder().setContent(contentView);
        return this;
    }

    /**
     * ???????????????????????????
     *
     * @param contentViewId ???????????????ID
     */
    @NonNull
    public DialogLayer contentView(@LayoutRes int contentViewId) {
        getConfig().mContentViewId = contentViewId;
        return this;
    }

    /**
     * ????????????????????????????????????????????????View
     * ???????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param statusBarId ??????????????????View
     */
    @NonNull
    public DialogLayer asStatusBar(@IdRes int statusBarId) {
        getConfig().mAsStatusBarViewId = statusBarId;
        return this;
    }

    /**
     * ?????????????????????
     *
     * @param avoid ?????????????????????
     */
    @NonNull
    public DialogLayer avoidStatusBar(boolean avoid) {
        getConfig().mAvoidStatusBar = avoid;
        return this;
    }

    /**
     * ??????????????????gravity
     * ??????????????????????????????layout_gravity?????????????????????
     *
     * @param gravity {@link Gravity}
     */
    @NonNull
    public DialogLayer gravity(int gravity) {
        getConfig().mGravity = gravity;
        return this;
    }

    /**
     * ???????????????????????????????????????
     *
     * @param swipeDirection {@link SwipeLayout.Direction}
     */
    @NonNull
    public DialogLayer swipeDismiss(int swipeDirection) {
        getConfig().mSwipeDirection = swipeDirection;
        return this;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param swipeTransformer SwipeTransformer
     */
    @NonNull
    public DialogLayer swipeTransformer(@Nullable SwipeTransformer swipeTransformer) {
        getConfig().mSwipeTransformer = swipeTransformer;
        return this;
    }

    /**
     * ????????????????????????
     *
     * @param swipeListener OnSwipeListener
     */
    @NonNull
    public DialogLayer onSwipeListener(@NonNull OnSwipeListener swipeListener) {
        getListenerHolder().addOnSwipeListener(swipeListener);
        return this;
    }

    /**
     * ??????????????????????????????????????????
     *
     * @param animStyle AnimStyle
     */
    @NonNull
    public DialogLayer animStyle(@Nullable AnimStyle animStyle) {
        getConfig().mAnimStyle = animStyle;
        return this;
    }

    /**
     * ???????????????????????????????????????
     * ??????????????????{@link AnimatorHelper}
     *
     * @param contentAnimatorCreator AnimatorCreator
     */
    @NonNull
    public DialogLayer contentAnimator(@Nullable AnimatorCreator contentAnimatorCreator) {
        getConfig().mContentAnimatorCreator = contentAnimatorCreator;
        return this;
    }

    /**
     * ???????????????????????????????????????
     * ??????????????????{@link AnimatorHelper}
     *
     * @param backgroundAnimatorCreator AnimatorCreator
     */
    @NonNull
    public DialogLayer backgroundAnimator(@Nullable AnimatorCreator backgroundAnimatorCreator) {
        getConfig().mBackgroundAnimatorCreator = backgroundAnimatorCreator;
        return this;
    }

    /**
     * ?????????????????????activity?????????????????????
     * ????????????????????????????????????????????????{@link #backgroundColorInt(int)}??????
     * ????????????backgroundColor?????????imageView.setColorFilter(backgroundColor)??????
     * ???????????????{@link #backgroundColorInt(int)}???????????????????????????
     *
     * @param radius ????????????
     */
    @NonNull
    public DialogLayer backgroundBlurRadius(@FloatRange(from = 0F) float radius) {
        getConfig().mBackgroundBlurRadius = radius;
        return this;
    }

    @NonNull
    public DialogLayer backgroundBlurPercent(@FloatRange(from = 0F) float percent) {
        getConfig().mBackgroundBlurPercent = percent;
        return this;
    }

    /**
     * ???????????????????????????????????????
     *
     * @param scale ????????????
     */
    @NonNull
    public DialogLayer backgroundBlurScale(@FloatRange(from = 1F) float scale) {
        getConfig().mBackgroundBlurScale = scale;
        return this;
    }

    /**
     * ??????????????????
     *
     * @param bitmap ??????
     */
    @NonNull
    public DialogLayer backgroundBitmap(@Nullable Bitmap bitmap) {
        getConfig().mBackgroundBitmap = bitmap;
        return this;
    }

    /**
     * ????????????????????????
     *
     * @param dimAmount ???????????? 0~1
     */
    @NonNull
    public DialogLayer backgroundDimAmount(@FloatRange(from = 0F, to = 1F) float dimAmount) {
        getConfig().mBackgroundDimAmount = Utils.floatRange01(dimAmount);
        return this;
    }

    /**
     * ??????????????????
     */
    @NonNull
    public DialogLayer backgroundDimDefault() {
        return backgroundDimAmount(mDimAmountDef);
    }

    /**
     * ??????????????????
     *
     * @param resource ??????ID
     */
    @NonNull
    public DialogLayer backgroundResource(@DrawableRes int resource) {
        getConfig().mBackgroundResource = resource;
        return this;
    }

    /**
     * ????????????Drawable
     *
     * @param drawable Drawable
     */
    @NonNull
    public DialogLayer backgroundDrawable(@Nullable Drawable drawable) {
        getConfig().mBackgroundDrawable = drawable;
        return this;
    }

    /**
     * ??????????????????
     * ????????????{@link #backgroundBitmap(Bitmap)}??????{@link #backgroundBlurRadius(float)}?????????
     * ?????????????????????imageView.setColorFilter(backgroundColor)??????
     * ?????????????????????????????????????????????
     *
     * @param colorInt ?????????
     */
    @NonNull
    public DialogLayer backgroundColorInt(@ColorInt int colorInt) {
        getConfig().mBackgroundColor = colorInt;
        return this;
    }

    /**
     * ??????????????????
     * ????????????{@link #backgroundBitmap(Bitmap)}??????{@link #backgroundBlurRadius(float)}?????????
     * ?????????????????????imageView.setColorFilter(backgroundColor)??????
     * ?????????????????????????????????????????????
     *
     * @param colorRes ????????????ID
     */
    @NonNull
    public DialogLayer backgroundColorRes(@ColorRes int colorRes) {
        getConfig().mBackgroundColor = getActivity().getResources().getColor(colorRes);
        return this;
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param cancelable ???????????????
     */
    @NonNull
    public DialogLayer cancelableOnTouchOutside(boolean cancelable) {
        getConfig().mCancelableOnTouchOutside = cancelable;
        return this;
    }

    /**
     * ????????????????????????????????????
     *
     * @param cancelable ???????????????
     */
    @NonNull
    @Override
    public DialogLayer cancelableOnClickKeyBack(boolean cancelable) {
        return (DialogLayer) super.cancelableOnClickKeyBack(cancelable);
    }

    /**
     * ??????????????????????????????
     *
     * @return View
     */
    @Nullable
    public View getContentView() {
        return getViewHolder().getContent();
    }

    /**
     * ???????????????
     *
     * @return ImageView
     */
    @NonNull
    public ImageView getBackground() {
        return getViewHolder().getBackground();
    }

    /**
     * ?????????????????????????????????????????????
     * ????????????EditText???????????????????????????
     * ???{@link OnVisibleChangeListener#onShow(Layer)}?????????
     * ?????????{@link #removeSoftInput()}????????????
     *
     * @param editTexts ??????EditTexts
     */
    @NonNull
    public DialogLayer compatSoftInput(boolean bottomToContentView, EditText... editTexts) {
        if (mSoftInputHelper == null) {
            mSoftInputHelper = SoftInputHelper.attach(getActivity())
                    .moveWithTranslation()
                    .moveBy(getViewHolder().getContentWrapper());
        }
        if (bottomToContentView) {
            mSoftInputHelper.moveWith(getViewHolder().getContent(), editTexts);
        } else {
            for (EditText editText : editTexts) {
                mSoftInputHelper.moveWith(editText, editText);
            }
        }
        return this;
    }

    @NonNull
    public DialogLayer compatSoftInput(EditText... editTexts) {
        return compatSoftInput(true, editTexts);
    }

    /**
     * ?????????????????????
     * ???{@link OnVisibleChangeListener#onDismiss(Layer)}?????????
     * ?????????{@link #compatSoftInput(EditText...)}????????????
     */
    public void removeSoftInput() {
        if (mSoftInputHelper != null) {
            mSoftInputHelper.detach();
        }
    }

    /**
     * ????????????????????????????????????
     * ?????????true???false????????????activityContent????????????
     *
     * @param intercept ????????????????????????
     */
    @NonNull
    public DialogLayer outsideInterceptTouchEvent(boolean intercept) {
        getConfig().mOutsideInterceptTouchEvent = intercept;
        return this;
    }

    @NonNull
    public DialogLayer outsideTouched(OutsideTouchedListener listener) {
        getConfig().mOutsideTouchedListener = listener;
        return this;
    }

    @NonNull
    public DialogLayer outsideTouchedToDismiss(boolean toDismiss) {
        getConfig().mOutsideTouchedToDismiss = toDismiss;
        return this;
    }

    public static class ViewHolder extends DecorLayer.ViewHolder {
        private FrameLayout mActivityContent;
        private BackgroundView mBackground;
        private SwipeLayout mContentWrapper;
        private View mContent;

        public void recycle() {
            if (mBackground.getDrawable() instanceof BitmapDrawable) {
                BitmapDrawable bd = (BitmapDrawable) mBackground.getDrawable();
                bd.getBitmap().recycle();
            }
        }

        @Override
        public void setChild(@NonNull View child) {
            super.setChild(child);
            mContentWrapper = getChild().findViewById(R.id.anylayler_fl_content_wrapper);
            mBackground = getChild().findViewById(R.id.anylayler_iv_background);
        }

        @NonNull
        @Override
        public ContainerLayout getChild() {
            return (ContainerLayout) super.getChild();
        }

        @Nullable
        @Override
        protected ContainerLayout getChildNullable() {
            return (ContainerLayout) super.getChildNullable();
        }

        protected void setContent(@NonNull View content) {
            mContent = content;
        }

        @Nullable
        protected View getContentNullable() {
            return mContent;
        }

        @NonNull
        public View getContent() {
            Utils.requireNonNull(mContent, "?????????show???????????????");
            return mContent;
        }

        @NonNull
        public SwipeLayout getContentWrapper() {
            return mContentWrapper;
        }

        @NonNull
        public BackgroundView getBackground() {
            return mBackground;
        }
    }

    protected static class Config extends DecorLayer.Config {
        protected boolean mOutsideInterceptTouchEvent = true;
        @Nullable
        protected OutsideTouchedListener mOutsideTouchedListener = null;
        protected boolean mOutsideTouchedToDismiss = false;

        @Nullable
        protected AnimatorCreator mBackgroundAnimatorCreator = null;
        @Nullable
        protected AnimatorCreator mContentAnimatorCreator = null;
        @Nullable
        protected AnimStyle mAnimStyle = null;

        protected int mContentViewId = -1;

        protected boolean mCancelableOnTouchOutside = true;

        protected int mAsStatusBarViewId = -1;
        protected boolean mAvoidStatusBar = false;

        protected int mGravity = Gravity.CENTER;
        protected float mBackgroundBlurPercent = 0F;
        protected float mBackgroundBlurRadius = 0F;
        protected float mBackgroundBlurScale = 2F;
        @Nullable
        protected Bitmap mBackgroundBitmap = null;
        protected int mBackgroundResource = -1;
        @Nullable
        protected Drawable mBackgroundDrawable = null;
        protected float mBackgroundDimAmount = -1;
        @ColorInt
        protected int mBackgroundColor = Color.TRANSPARENT;

        @SwipeLayout.Direction
        protected int mSwipeDirection = 0;
        @Nullable
        protected SwipeTransformer mSwipeTransformer = null;
    }

    protected static class ListenerHolder extends DecorLayer.ListenerHolder {
        private List<OnSwipeListener> mOnSwipeListeners = null;

        private void addOnSwipeListener(@NonNull OnSwipeListener onSwipeListener) {
            if (mOnSwipeListeners == null) {
                mOnSwipeListeners = new ArrayList<>(1);
            }
            mOnSwipeListeners.add(onSwipeListener);
        }

        private void notifyOnSwipeStart(@NonNull Layer layer) {
            if (mOnSwipeListeners != null) {
                for (OnSwipeListener onSwipeListener : mOnSwipeListeners) {
                    onSwipeListener.onStart(layer);
                }
            }
        }

        private void notifyOnSwiping(@NonNull Layer layer,
                                     @SwipeLayout.Direction int direction,
                                     @FloatRange(from = 0F, to = 1F) float fraction) {
            if (mOnSwipeListeners != null) {
                for (OnSwipeListener onSwipeListener : mOnSwipeListeners) {
                    onSwipeListener.onSwiping(layer, direction, fraction);
                }
            }
        }

        private void notifyOnSwipeEnd(@NonNull Layer layer,
                                      @SwipeLayout.Direction int direction) {
            if (mOnSwipeListeners != null) {
                for (OnSwipeListener onSwipeListener : mOnSwipeListeners) {
                    onSwipeListener.onEnd(layer, direction);
                }
            }
        }
    }

    public interface OutsideTouchedListener {
        void outsideTouched();
    }

    public interface SwipeTransformer {
        void onDragging(@NonNull View content,
                        @NonNull View background,
                        @FloatRange(from = 0F, to = 1F) float f);
    }

    public interface OnSwipeListener {
        void onStart(@NonNull Layer layer);

        void onSwiping(@NonNull Layer layer,
                       @SwipeLayout.Direction int direction,
                       @FloatRange(from = 0F, to = 1F) float fraction);

        void onEnd(@NonNull Layer layer,
                   @SwipeLayout.Direction int direction);
    }

    public enum AnimStyle {
        ALPHA,
        ZOOM,
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }
}
