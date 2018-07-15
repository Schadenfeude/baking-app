package com.itrided.android.bakerstreet.recipe;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.itrided.android.bakerstreet.R;
import com.itrided.android.bakerstreet.recipe.ingredient.IngredientsFragment;
import com.itrided.android.bakerstreet.recipe.step.StepsListFragment;

public class RecipePagerAdapter extends FragmentStatePagerAdapter {

    private static final int STEPS_FRAGMENT_POSITION = 0;
    private static final int INGREDIENTS_FRAGMENT_POSITION = 1;

    private final Context context;
    private SparseArray<Fragment> fragments = new SparseArray<>();


    public RecipePagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (STEPS_FRAGMENT_POSITION == position) {
            return StepsListFragment.getInstance();
        } else {
            return IngredientsFragment.getInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        final Resources resources = context.getResources();
        switch (position) {
            case STEPS_FRAGMENT_POSITION:
                return resources.getString(R.string.tab_title_steps);
            case INGREDIENTS_FRAGMENT_POSITION:
                return resources.getString(R.string.tab_title_ingredients);
            default:
                return resources.getString(R.string.placeholder_string);
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fragments.remove(position);
        super.destroyItem(container, position, object);
    }
}
