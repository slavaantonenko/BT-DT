package com.msapplications.btdt;

import android.app.Activity;
import android.content.Context;

import com.msapplications.btdt.objects.Category;
import com.msapplications.btdt.objects.CategoryType;
import com.msapplications.btdt.objects.itemTypes.ItemInCategory;

import java.util.ArrayList;

public class CategoryList
{
    private static ArrayList<Category> categories = null;

    private CategoryList(Context mContext)
    {

        categories = (ArrayList<Category>) Utils.getListFromCache(mContext.getCacheDir(), CommonValues.CACHE_CATEGORIES_KEY);

        if (categories == null)
            categories = new ArrayList<>();

    }

    public static ArrayList<Category> getCategories(Context mContext)
    {
        if (categories == null)
            new CategoryList(mContext);

        return categories;
    }

    public static void addCategory(Context mContext, Category newCategory)
    {
        if (categories == null)
            new CategoryList(mContext);

        categories.add(newCategory);
        updateCategoriesCacheExtras(mContext, categories);
    }

    public static void deleteCategory(Context mContext, String categoryName)
    {
        if (categories == null)
            new CategoryList(mContext);

        for (Category category : categories)
        {
            if (category.getName().equals(categoryName))
            {
                categories.remove(category);
                break;
            }
        }

        updateCategoriesCacheExtras(mContext, categories);
    }

    public static void renameCategory(Context mContext, String categoryName, String newCategoryName)
    {
        if (categories == null)
            new CategoryList(mContext);

        for (Category category : categories)
        {
            if (category.getName().equals(categoryName))
            {
                category.setName(newCategoryName);
                break;
            }
        }

        updateCategoriesCacheExtras(mContext, categories);
    }

    public static boolean categoryNameExists(String newName)
    {
        for (Category category : categories)
            if (category.getName().equals(newName))
                return true;

        return false;
    }

    private static void updateCategoriesCacheExtras(Context mContext, ArrayList<Category> categories)
    {
        Utils.saveListToCache(mContext.getCacheDir(), categories, CommonValues.CACHE_CATEGORIES_KEY); // save categories to cache for next time
        //new CategoryList(mContext);
    }

    //TEMP
    private static void createDemoInfo()
    {
        int[] covers = new int[]{
                R.drawable.album8,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album7,
                R.drawable.album6};

        Category a = new Category("Travel", new ArrayList<ItemInCategory>(), CategoryType.COLLECTION, covers[0]);
        categories.add(a);

        a = new Category("Recepies", new ArrayList<ItemInCategory>(), CategoryType.COLLECTION, covers[1]);
        categories.add(a);

        a = new Category("Movies", new ArrayList<ItemInCategory>(), CategoryType.COLLECTION, covers[2]);
        categories.add(a);

        a = new Category("TV", new ArrayList<ItemInCategory>(), CategoryType.COLLECTION, covers[3]);
        categories.add(a);

        a = new Category("Food", new ArrayList<ItemInCategory>(), CategoryType.COLLECTION, covers[4]);
        categories.add(a);

    }

}
