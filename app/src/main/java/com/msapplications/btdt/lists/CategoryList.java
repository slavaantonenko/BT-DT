package com.msapplications.btdt.lists;

import android.content.Context;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.objects.Category;

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

    public static Category getCategoryByIndex(int index)
    {
        if (categories == null)
            return null;

        return categories.get(index);
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

}
