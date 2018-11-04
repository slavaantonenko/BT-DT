package com.msapplications.btdt;

import com.msapplications.btdt.objects.Category;
import com.msapplications.btdt.objects.CategoryType;
import com.msapplications.btdt.objects.itemTypes.ItemInCategory;

import java.util.ArrayList;

public class CategoryList
{
    private static ArrayList<Category> categories = null;

    private CategoryList()
    {
        categories = new ArrayList<>();
        createDemoInfo();
    }

    public static ArrayList<Category> getCategories()
    {
        if(categories == null)
            new CategoryList();

        return categories;
    }

    public static void addCategory(Category newCategory)
    {
        if(categories == null)
            new CategoryList();

        categories.add(newCategory);
    }

    public static void deleteCategory(Category category)
    {
        if(categories == null) {
            new CategoryList();
            return;
        }

        categories.remove(category);
    }

    public static boolean categoryNameExists(String newName)
    {
        for(Category category : categories) {
            if(category.getName().equals(newName))
                return true;
        }
        return false;
    }

    private void createDemoInfo()
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
