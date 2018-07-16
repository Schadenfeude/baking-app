package com.itrided.android.bakerstreet.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeIngredientsWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetDataProvider(this, intent);
    }
}
