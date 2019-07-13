package com.msapplications.btdt.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.interfaces.OnCategoryClickListener;
import com.msapplications.btdt.interfaces.OnObjectMenuClickListener;
import com.msapplications.btdt.interfaces.OnMenuItemClickListener;
import com.msapplications.btdt.R;
import com.msapplications.btdt.objects.Category;
import com.msapplications.btdt.objects.CategoryType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.iwgang.countdownview.CountdownView;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>
{
    private Context context;
    private List<Category> categoriesList;
    private OnCategoryClickListener categoryClickListener;
    private OnObjectMenuClickListener categoryMenuClickListener;
    private OnMenuItemClickListener menuItemClickListener;
    private int adapterPosition = -1;
//    private int viewPositionAtCreation = -1;

    public CategoriesAdapter(Context context)
    {
        this.context = context;
        categoryClickListener = (OnCategoryClickListener) context;
        categoryMenuClickListener = (OnObjectMenuClickListener) context;
        menuItemClickListener = (OnMenuItemClickListener) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener,
            View.OnClickListener
    {
        public TextView title, tvNewFeatureTitle;
        public ImageView overflow, lock;
        public CardView cvCategory, cvNewFeatureCountDown;
        public CountdownView countdownView; // For future features
        public KonfettiView kvFeatureAvailable;

        public ViewHolder(View view)
        {
            super(view);
            title = view.findViewById(R.id.tvCategoryTitle);
            overflow = view.findViewById(R.id.overflow);
            cvCategory = view.findViewById(R.id.cvCategory);
            cvCategory.setOnClickListener(this);
            overflow.setOnClickListener(this);

            // For future features
            tvNewFeatureTitle = view.findViewById(R.id.tvNewFeatureTitle);
            countdownView = view.findViewById(R.id.countDownNewFeature);
            cvNewFeatureCountDown = view.findViewById(R.id.cvNewFeatureCountDown);
            lock = view.findViewById(R.id.ivLock);
            kvFeatureAvailable = view.findViewById(R.id.kvFeatureAvailable);
        }

        public void onBindViewHolder(Category category)
        {
            final CategoryType type = getItem(getAdapterPosition()).getType();
            final String prefName = CommonValues.FEATURE_AVAILABLE_PREF_NAME.get(type);

            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/ARLRDBD.ttf");
            title.setTypeface(font);
            title.setText(category.getName());

            cvCategory.setCardBackgroundColor(category.getBackgroundColor());
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) cvCategory.getLayoutParams();
            layoutParams.height = getCardViewHeight(type, prefName);
            cvCategory.setLayoutParams(layoutParams);

            if (CommonValues.COMING_SOON_FEATURES.contains(type))
            {
                // Future feature
                try
                {
                    if (calculateTimeDifference(type) >= 0)
                    {
                        tvNewFeatureTitle.setText(category.getName());
                        lock.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_lock));
                        cvNewFeatureCountDown.setVisibility(View.VISIBLE);

                        countdownView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener()
                        {
                            @Override
                            public void onViewAttachedToWindow(View v)
                            {
                                try {
                                    countdownView.start(calculateTimeDifference(type));
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onViewDetachedFromWindow(View v) {}
                        });

                        countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                            @Override
                            public void onEnd(CountdownView cv)
                            {
                                featureAvailableAnimation(cvNewFeatureCountDown,
                                        kvFeatureAvailable,
                                        lock,
                                        prefName);
                            }
                        });
                    }
                    else if (!Utils.getBooleanFromCache(context, prefName, false)) // Unlock animation not yet shown
                    {
                        cvNewFeatureCountDown.setVisibility(View.VISIBLE);
                        tvNewFeatureTitle.setText(category.getName());
                        featureAvailableAnimation(cvNewFeatureCountDown, kvFeatureAvailable, lock, prefName);
                    }
                    else
                        cvNewFeatureCountDown.setVisibility(View.GONE); // Feature was already opened.
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
                cvNewFeatureCountDown.setVisibility(View.INVISIBLE);

        }

        @Override
        public void onClick(View view)
        {
            adapterPosition = getAdapterPosition();

            switch (view.getId())
            {
                case (R.id.cvCategory):
                    if (categoryClickListener == null)
                        return;

                    categoryClickListener.onCategoryClick(view);
                    break;
                case (R.id.overflow):
                    if (categoryMenuClickListener == null)
                        return;

                    categoryMenuClickListener.onObjectMenuClick(view, getAdapterPosition());
                    break;
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem)
        {
            if (menuItem.getItemId() != R.id.action_delete)
                adapterPosition = getAdapterPosition();

            menuItemClickListener.onMenuItemClick(menuItem, getAdapterPosition());
            return false;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.onBindViewHolder(categoriesList.get(position));
    }

    @Override
    public int getItemCount()
    {
        if (categoriesList == null)
            return 0;

        return categoriesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public Category getItem(int position) {
        return categoriesList.get(position);
    }

    public void setCategories(List<Category> categories) {
        categoriesList = categories;
        notifyDataSetChanged();
    }

    public void setCategory(Category category)
    {
        categoriesList.set(adapterPosition, category);
        notifyItemChanged(adapterPosition);
        adapterPosition = -1;
    }

    public int adapterPosition() {
        return adapterPosition;
    }

    public void resetPosition() {
        adapterPosition = -1;
    }

    private int getCardViewHeight(CategoryType type, String prefName)
    {
        Random random = new Random();

        // Randomize height for a category card, formula: new Random().nextInt((max - min) + 1) + min
        int height = random.nextInt((CommonValues.CATEGORY_CARD_SIZE_MAX - CommonValues.CATEGORY_CARD_SIZE_MIN) + 1)
                + CommonValues.CATEGORY_CARD_SIZE_MIN;

        if (CommonValues.COMING_SOON_FEATURES.contains(type) && !Utils.getBooleanFromCache(context, prefName, false)) {
            height = (int) context.getResources().getDimension(R.dimen.nfcc_height);
            return height;
        }

        return Utils.dpToPx(context.getResources(), height);
    }

    private void featureAvailableAnimation(final CardView cvNewFeatureCountDown, final KonfettiView kvFeatureAvailable,
                                           ImageView lock, final String prefName)
    {
        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) context.getDrawable(R.drawable.animated_ic_lock);
        lock.setImageDrawable(animatedVectorDrawable);

        if (animatedVectorDrawable != null)
        {
            animatedVectorDrawable.registerAnimationCallback(new Animatable2.AnimationCallback()
            {
                @Override
                public void onAnimationEnd(Drawable drawable)
                {
                    super.onAnimationEnd(drawable);
                    kvFeatureAvailable.build()
                            .addColors(context.getResources().getIntArray(R.array.feature_available))
                            .setDirection(0.0, 359.0)
                            .setSpeed(1f, 5f)
                            .setFadeOutEnabled(true)
                            .setTimeToLive(2000L)
                            .addShapes(Shape.RECT, Shape.CIRCLE)
                            .addSizes(new Size(12, 5))
                            .setPosition(kvFeatureAvailable.getWidth() / 2, kvFeatureAvailable.getHeight() / 3)
                            .burst(300);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            // Fade out countdown layout and remove it from screen at the end.
                            Animation fadeOut = new AlphaAnimation(1, 0);
                            fadeOut.setInterpolator(new AccelerateInterpolator());
                            fadeOut.setStartOffset(1000);
                            fadeOut.setDuration(1000);
                            AnimationSet animation = new AnimationSet(false); //change to false
                            animation.addAnimation(fadeOut);
                            cvNewFeatureCountDown.startAnimation(animation);
                            cvNewFeatureCountDown.setVisibility(View.GONE);

                            Utils.saveBooleanToCache(context, prefName, true);
                        }

                    }, 0);
                }
            });
            animatedVectorDrawable.start();
        }
    }

    private long calculateTimeDifference(CategoryType type)
    {
        try
        {
            Date currentDate = Calendar.getInstance().getTime();
            String date = CommonValues.COMING_SOON_FEATURES_DATES.get(type);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return formatter.parse(date).getTime() - formatter.parse(formatter.format(currentDate)).getTime();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}