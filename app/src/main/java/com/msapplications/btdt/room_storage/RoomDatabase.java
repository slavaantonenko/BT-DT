package com.msapplications.btdt.room_storage;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.msapplications.btdt.objects.Category;
import com.msapplications.btdt.objects.itemTypes.NoteItem;
import com.msapplications.btdt.objects.itemTypes.cinema.Cinema;
import com.msapplications.btdt.objects.itemTypes.cinema.CinemaHall;
import com.msapplications.btdt.objects.itemTypes.recipes.Recipe;
import com.msapplications.btdt.objects.itemTypes.recipes.RecipeIngredient;
import com.msapplications.btdt.objects.itemTypes.travel.CountryModel;
import com.msapplications.btdt.room_storage.category.CategoryDao;
import com.msapplications.btdt.room_storage.cinema.CinemaDao;
import com.msapplications.btdt.room_storage.cinema.CinemaHallsDao;
import com.msapplications.btdt.room_storage.ingredient.IngredientDao;
import com.msapplications.btdt.room_storage.note.NoteItemDao;
import com.msapplications.btdt.room_storage.recipe.RecipeDao;
import com.msapplications.btdt.room_storage.travel.CountryDao;

// version = 1 is CinemaHall hall and row in int and not String.
@Database(entities = {Cinema.class, CinemaHall.class, CountryModel.class, NoteItem.class, Category.class,
        Recipe.class, RecipeIngredient.class}, version = 12)
public abstract class RoomDatabase extends android.arch.persistence.room.RoomDatabase
{
    public abstract CinemaDao cinemaDao();
    public abstract CinemaHallsDao cinemaHallsDao();
    public abstract CountryDao countryDao();
    public abstract NoteItemDao noteItemDao();
    public abstract CategoryDao categoryDao();
    public abstract RecipeDao recipeDao();
    public abstract IngredientDao ingredientDao();

    private static volatile RoomDatabase INSTANCE;

    public static RoomDatabase getDatabase(final Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (RoomDatabase.class)
            {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabase.class, "room_database")
                            .allowMainThreadQueries()
                            .addMigrations()
                            .addMigrations(MIGRATION_10_11)
                            .addMigrations(MIGRATION_11_12)
                            .build();
            }
        }

        return INSTANCE;
    }

    public static void closeConnection() {
        if (INSTANCE != null && INSTANCE.isOpen())
            INSTANCE.close();
    }

//    static final Migration MIGRATION_9_10 = new Migration(9, 10) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            // Since we didn't alter the table, there's nothing else to do here.
//            database.execSQL("ALTER TABLE recipes_table ADD COLUMN id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL");
//            database.execSQL("ALTER TABLE recipes_table ADD COLUMN recipe_name VARCHAR(255) NOT NULL");
//            database.execSQL("ALTER TABLE recipes_table ADD COLUMN recipe_color INT(2) DEFAULT 1");
//
//        }
//    };

    static final Migration MIGRATION_11_12 = new Migration(11, 12) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE note_item_table ADD COLUMN recipeID INTEGER DEFAULT -1 NOT NULL");
            database.execSQL("CREATE TABLE recipes_table (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)");
//            database.execSQL("ALTER TABLE recipes_table ADD COLUMN id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL");
            database.execSQL("ALTER TABLE recipes_table ADD COLUMN recipe_name VARCHAR(255)");
            database.execSQL("ALTER TABLE recipes_table ADD COLUMN recipe_color INTEGER NOT NULL DEFAULT 1");
            database.execSQL("ALTER TABLE recipes_table ADD COLUMN recipe_image VARCHAR(255) DEFAULT ''");


            database.execSQL("CREATE TABLE ingredient_item_table (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)");
            database.execSQL("ALTER TABLE ingredient_item_table ADD COLUMN recipe_id INTEGER NOT NULL DEFAULT -1");
            database.execSQL("ALTER TABLE ingredient_item_table ADD COLUMN text VARCHAR(255)");
            database.execSQL("ALTER TABLE ingredient_item_table ADD COLUMN line_number INTEGER NOT NULL DEFAULT -1");

        }
    };

    static final Migration MIGRATION_10_11 = new Migration(10, 11) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE note_item_table ADD COLUMN recipeID INTEGER DEFAULT -1 NOT NULL");
        }
    };
}
