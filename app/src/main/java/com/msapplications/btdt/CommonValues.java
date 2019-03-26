package com.msapplications.btdt;

import com.msapplications.btdt.objects.CategoryType;

import java.util.ArrayList;
import java.util.HashMap;

/*
static values for app
 */
public class CommonValues
{
    // Categories
    public static final int CATEGORY_CARD_SIZE_MIN = 150;
    public static final int CATEGORY_CARD_SIZE_MAX = 250;

    // Activities
    public static final String CATEGORY_ID_EXTRA = "CategoryID";
    public static final String CATEGORY_NAME_EXTRA = "CategoryName";
    public static final String COUNTRY_EXTRA = "Country";

    // Fragments
    public static final String NOTES_FRAGMENT = "NotesFragment";
    public static final String RECIPES_FRAGMENT = "RecipesFragment";
    public static final String CINEMA_SEATS_FRAGMENT = "CinemaSeatsFragment";
    public static final String FRAGMENT_TITLE = "FragmentTitle";
    public static final String TRAVEL_COUNTRY_FRAGMENT_TAG = "TravelCountryFragment";
    public static final String ADD_CINEMA_DIALOG_FRAGMENT_TAG = "AddCinemaDialogFragment";
    public static final String ADD_HALL_DIALOG_FRAGMENT_TAG = "AddEditHallDialogFragment";
    public static final String NEW_CATEGORY_DIALOG_FRAGMENT_TAG = "NewCategoryDialogFragment";
    public static final String RENAME_CATEGORY_DIALOG_FRAGMENT_TAG = "RenameCategoryDialogFragment";
    public static final String CHOOSE_COLOR_DIALOG_FRAGMENT_TAG = "ChooseColorDialogFragment";
    public static final String CINEMA_HALL_BUNDLE = "CinemaHall";
    public static final String CINEMA_INFO_BUNDLE = "CinemaInfo";
    public static final String CINEMA_HALL_ACTION_BUNDLE = "CinemaHallAction";
    public static final String CATEGORY_BUNDLE = "Category";
    public static final int ADD_CINEMA_REQUEST_CODE = 1;
    public static final int EDIT_HALL_REQUEST_CODE = 2;
    public static final int RENAME_CATEGORY_RESULT_CODE = 1;
    public static final int ADDED_TO_TRAVEL_LIST = 1;

    // Global Strings
    public static final String NOTE = "Note";
    public static final String RECIPES = "Recipes";
    public static final String CINEMA_SEATS = "Cinema Seats";
    public static final String CINEMA_CITY = "Cinema City";
    public static final String HOT_CINEMA = "Hot Cinema";
    public static final String YES_PLANET = "Yes Planet";
    public static final String TRAVEL = "Travel";

    // Preferences
    public static final String FIRST_USE = "firstUse";
    public static final ArrayList<CategoryType> COMING_SOON_FEATURES = new ArrayList<CategoryType>(){{
//        add(CategoryType.TRAVEL);
        add(CategoryType.RECIPES);
    }};
    public static final HashMap<CategoryType, String> COMING_SOON_FEATURES_DATES = new HashMap<CategoryType, String>() {{
//        put(CategoryType.TRAVEL, "15/04/2019 00:00:00");
        put(CategoryType.RECIPES, "10/04/2019 00:00:00");
    }};
    public static final HashMap<CategoryType, String> FEATURE_AVAILABLE_PREF_NAME = new HashMap<CategoryType, String>() {{
//        put(CategoryType.TRAVEL, TRAVEL_FEATURE_AVAILABLE);
        put(CategoryType.RECIPES, RECIPES_FEATURE_AVAILABLE);
    }};
    public static final String TRAVEL_FEATURE_AVAILABLE = "travelFeatureAvailable";
    public static final String RECIPES_FEATURE_AVAILABLE = "recipesFeatureAvailable";
    public static final String TRAVEL_LIST_CHANGED = "travelListChanged";
}
