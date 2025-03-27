package com.example.babyjournal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BabyJournal.db";
    private static final int DATABASE_VERSION = 1;

    // Common column names
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_BABY_NAME = "baby_name";

    // Table definitions using a Map
    private static final Map<String, String[]> TABLE_DEFINITIONS = new HashMap<String, String[]>() {{
        // Common columns pattern for most tables
        String baseColumns = COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, baby_name TEXT";
        
        put("babies", new String[]{
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT",
            "name TEXT NOT NULL",
            "gender TEXT NOT NULL"
        });
        
        put("first_words", new String[]{
            baseColumns,
            "first_smile TEXT",
            "first_words TEXT",
            "makes_laugh TEXT",
            "created_at DATETIME DEFAULT CURRENT_TIMESTAMP"
        });

        put("family_members", new String[]{
            baseColumns,
            "relation TEXT",
            "member_name TEXT"
        });

        put("names", new String[]{
            baseColumns,
            "name_reason TEXT",
            "alt_name_1 TEXT",
            "alt_name_2 TEXT",
            "alt_name_3 TEXT"
        });

        put("birth_details", new String[]{
            baseColumns,
            "birth_date TEXT",
            "birth_weight TEXT",
            "birth_features TEXT",
            "birth_attendees TEXT"
        });

        put("first_week", new String[]{
            baseColumns,
            "homecoming_date TEXT",
            "first_address TEXT",
            "first_visitors TEXT"
        });

        put("bathtime", new String[]{
            baseColumns,
            "first_bath TEXT",
            "bath_experience TEXT",
            "bath_games TEXT",
            "bath_toys TEXT"
        });

        put("food_preferences", new String[]{
            baseColumns,
            "food_name TEXT",
            "tried INTEGER"
        });

        put("on_the_move", new String[]{
            baseColumns,
            "lift_head TEXT",
            "roll_over TEXT",
            "find_feet TEXT",
            "sit_up TEXT",
            "clapped TEXT",
            "stood_up TEXT",
            "first_steps TEXT"
        });

        put("telling_everyone", new String[]{
            baseColumns,
            "mam_found_out TEXT",
            "dad_found_out TEXT",
            "mam_first_told TEXT",
            "dad_first_told TEXT",
            "people_reactions TEXT"
        });

        put("parents", new String[]{
            baseColumns,
            "mother_name TEXT",
            "father_name TEXT"
        });
    }};

    
    // Baby table
    public static final String TABLE_BABIES = "babies";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GENDER = "gender";

    // family member table
    private static final String TABLE_FAMILY_MEMBERS = "family_members";
    private static final String COLUMN_RELATION = "relation";
    private static final String COLUMN_MEMBER_NAME = "member_name";

    // Names table
    private static final String TABLE_NAMES = "names";
    private static final String COLUMN_NAME_REASON = "name_reason";
    private static final String COLUMN_ALT_NAME_1 = "alt_name_1";
    private static final String COLUMN_ALT_NAME_2 = "alt_name_2";
    private static final String COLUMN_ALT_NAME_3 = "alt_name_3";

    // table for birth details
    private static final String TABLE_BIRTH_DETAILS = "birth_details";
    private static final String COLUMN_BIRTH_DATE = "birth_date";
    private static final String COLUMN_BIRTH_WEIGHT = "birth_weight";
    private static final String COLUMN_BIRTH_FEATURES = "birth_features";
    private static final String COLUMN_BIRTH_ATTENDEES = "birth_attendees";

    // first week table
    private static final String TABLE_FIRST_WEEK = "first_week";
    private static final String COLUMN_HOMECOMING_DATE = "homecoming_date";
    private static final String COLUMN_FIRST_ADDRESS = "first_address";
    private static final String COLUMN_FIRST_VISITORS = "first_visitors";

    // bath time table
    private static final String TABLE_BATHTIME = "bathtime";
    private static final String COLUMN_FIRST_BATH = "first_bath";
    private static final String COLUMN_BATH_EXPERIENCE = "bath_experience";
    private static final String COLUMN_BATH_GAMES = "bath_games";
    private static final String COLUMN_BATH_TOYS = "bath_toys";

    // food table
    private static final String TABLE_FOOD_PREFERENCES = "food_preferences";
    private static final String COLUMN_FOOD_NAME = "food_name";
    private static final String COLUMN_TRIED = "tried";

    // on the move table
    private static final String TABLE_ON_THE_MOVE = "on_the_move";
    private static final String COLUMN_LIFT_HEAD = "lift_head";
    private static final String COLUMN_ROLL_OVER = "roll_over";
    private static final String COLUMN_FIND_FEET = "find_feet";
    private static final String COLUMN_SIT_UP = "sit_up";
    private static final String COLUMN_CLAPPED = "clapped";
    private static final String COLUMN_STOOD_UP = "stood_up";
    private static final String COLUMN_FIRST_STEPS = "first_steps";

    // telling everyone table
    private static final String TABLE_TELLING_EVERYONE = "telling_everyone";
    private static final String COLUMN_MAM_FOUND_OUT = "mam_found_out";
    private static final String COLUMN_DAD_FOUND_OUT = "dad_found_out";
    private static final String COLUMN_MAM_FIRST_TOLD = "mam_first_told";
    private static final String COLUMN_DAD_FIRST_TOLD = "dad_first_told";
    private static final String COLUMN_PEOPLE_REACTIONS = "people_reactions";

    // parents table
    private static final String TABLE_PARENTS = "parents";
    private static final String COLUMN_MOTHER_NAME = "mother_name";
    private static final String COLUMN_FATHER_NAME = "father_name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private String createTableStatement(String tableName, String[] columns) {
        return "CREATE TABLE " + tableName + "(" + 
               String.join(", ", Arrays.stream(columns)
                       .flatMap(col -> Arrays.stream(col.split(", ")))
                       .toArray(String[]::new)) + 
               ")";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create all tables
        for (Map.Entry<String, String[]> table : TABLE_DEFINITIONS.entrySet()) {
            db.execSQL(createTableStatement(table.getKey(), table.getValue()));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop all tables
        for (String tableName : TABLE_DEFINITIONS.keySet()) {
            db.execSQL("DROP TABLE IF EXISTS " + tableName);
        }
        onCreate(db);
    }

    // Generic method for saving data
    private long saveData(String tableName, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        try {
            // Try to update existing record first
            int updatedRows = db.update(tableName, values, whereClause, whereArgs);
            if (updatedRows > 0) {
                result = updatedRows;
            } else {
                // If no existing record, insert new one
                result = db.insert(tableName, null, values);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error saving data to " + tableName, e);
        }

        return result;
    }

    // Example of using the generic save method
    public long saveBirthDetails(String babyName, String birthDate, String birthWeight,
                               String birthFeatures, String birthAttendees) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BABY_NAME, babyName);
        values.put("birth_date", birthDate);
        values.put("birth_weight", birthWeight);
        values.put("birth_features", birthFeatures);
        values.put("birth_attendees", birthAttendees);

        return saveData("birth_details", values, 
                       COLUMN_BABY_NAME + " = ?", 
                       new String[]{babyName});
    }

    // Generic method for getting data
    private <T> T getData(String tableName, String[] columns, String selection, 
                         String[] selectionArgs, Function<Cursor, T> mapper) {
        SQLiteDatabase db = this.getReadableDatabase();
        T result = null;

        try (Cursor cursor = db.query(
                tableName, columns, selection, selectionArgs, 
                null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                result = mapper.apply(cursor);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting data from " + tableName, e);
        }

        return result;
    }

    public void deleteBabyProfile(long babyId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String babyName = "";
        
        try (Cursor cursor = db.query("babies", 
                new String[]{"name"}, 
                "_id = ?", 
                new String[]{String.valueOf(babyId)}, 
                null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                babyName = cursor.getString(0);
            }
        }

        if (!babyName.isEmpty()) {
            try {
                db.beginTransaction();
                String[] whereArgs = {babyName};
                
                // Delete from all tables
                for (String tableName : TABLE_DEFINITIONS.keySet()) {
                    if (!tableName.equals("babies")) {
                        db.delete(tableName, "baby_name = ?", whereArgs);
                    }
                }
                
                // Delete the baby profile itself
                db.delete("babies", "_id = ?", new String[]{String.valueOf(babyId)});
                
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }

    public long addBabyProfile(String name, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_GENDER, gender);
        return db.insert(TABLE_BABIES, null, values);
    }

    public List<BabyProfile> getAllBabyProfiles() {
        List<BabyProfile> babyProfiles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_GENDER};
        Cursor cursor = db.query(TABLE_BABIES, columns, null, null, null, null,
                COLUMN_ID + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER));

                BabyProfile profile = new BabyProfile(id, name, gender);
                babyProfiles.add(profile);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return babyProfiles;
    }

    public FirstWordsData getFirstWordsData(String babyName) {
        SQLiteDatabase db = this.getReadableDatabase();
        FirstWordsData data = null;

        try {
            Cursor cursor = db.query(
                    "first_words",
                    new String[]{"first_smile", "first_words", "makes_laugh"},
                    "baby_name = ?",
                    new String[]{babyName},
                    null,
                    null,
                    "created_at DESC",  // Get the most recent entry
                    "1"  // Limit to 1 result
            );

            if (cursor != null && cursor.moveToFirst()) {
                data = new FirstWordsData(
                        cursor.getString(cursor.getColumnIndexOrThrow("first_smile")),
                        cursor.getString(cursor.getColumnIndexOrThrow("first_words")),
                        cursor.getString(cursor.getColumnIndexOrThrow("makes_laugh"))
                );
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting first words data", e);
        }

        return data;
    }

    public long saveFirstWordsData(String babyName, String firstSmile, String firstWords, String makesLaugh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("baby_name", babyName);
        values.put("first_smile", firstSmile);
        values.put("first_words", firstWords);
        values.put("makes_laugh", makesLaugh);

        // Try to update existing record first
        int updatedRows = db.update(
                "first_words",
                values,
                "baby_name = ?",
                new String[]{babyName}
        );

        if (updatedRows > 0) {
            return updatedRows;
        } else {
            // If no existing record, insert new one
            return db.insert("first_words", null, values);
        }
    }

    public void addFamilyMember(String babyName, String relation, String memberName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BABY_NAME, babyName);
        values.put(COLUMN_RELATION, relation);
        values.put(COLUMN_MEMBER_NAME, memberName);

        db.insert(TABLE_FAMILY_MEMBERS, null, values);
        db.close();
    }

    public List<FamilyMember> getFamilyMembers(String babyName) {
        List<FamilyMember> familyMembers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_RELATION, COLUMN_MEMBER_NAME};
        String selection = COLUMN_BABY_NAME + " = ?";
        String[] selectionArgs = {babyName};

        Cursor cursor = db.query(TABLE_FAMILY_MEMBERS, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String relation = cursor.getString(cursor.getColumnIndex(COLUMN_RELATION));
                String memberName = cursor.getString(cursor.getColumnIndex(COLUMN_MEMBER_NAME));
                familyMembers.add(new FamilyMember(relation, memberName));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return familyMembers;
    }

    public void deleteFamilyMembers(String babyName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_BABY_NAME + " = ?";
        String[] whereArgs = {babyName};
        db.delete(TABLE_FAMILY_MEMBERS, whereClause, whereArgs);
        db.close();
    }

    // Add these methods for names data
    public long saveNamesData(String babyName, String nameReason, String altName1,
                              String altName2, String altName3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BABY_NAME, babyName);
        values.put(COLUMN_NAME_REASON, nameReason);
        values.put(COLUMN_ALT_NAME_1, altName1);
        values.put(COLUMN_ALT_NAME_2, altName2);
        values.put(COLUMN_ALT_NAME_3, altName3);

        // Try to update existing record first
        int updatedRows = db.update(
                TABLE_NAMES,
                values,
                COLUMN_BABY_NAME + " = ?",
                new String[]{babyName}
        );

        if (updatedRows > 0) {
            return updatedRows;
        } else {
            // If no existing record, insert new one
            return db.insert(TABLE_NAMES, null, values);
        }
    }

    public NamesData getNamesData(String babyName) {
        SQLiteDatabase db = this.getReadableDatabase();
        NamesData data = null;

        try {
            Cursor cursor = db.query(
                    TABLE_NAMES,
                    new String[]{COLUMN_NAME_REASON, COLUMN_ALT_NAME_1,
                            COLUMN_ALT_NAME_2, COLUMN_ALT_NAME_3},
                    COLUMN_BABY_NAME + " = ?",
                    new String[]{babyName},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                data = new NamesData(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_REASON)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ALT_NAME_1)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ALT_NAME_2)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ALT_NAME_3))
                );
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting names data", e);
        }

        return data;
    }

    // Add these methods at the end of the class
    public long saveFirstWeekData(String babyName, String homecomingDate,
                                  String firstAddress, String firstVisitors) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BABY_NAME, babyName);
        values.put(COLUMN_HOMECOMING_DATE, homecomingDate);
        values.put(COLUMN_FIRST_ADDRESS, firstAddress);
        values.put(COLUMN_FIRST_VISITORS, firstVisitors);

        // Try to update existing record first
        int updatedRows = db.update(
                TABLE_FIRST_WEEK,
                values,
                COLUMN_BABY_NAME + " = ?",
                new String[]{babyName}
        );

        if (updatedRows > 0) {
            return updatedRows;
        } else {
            // If no existing record, insert new one
            return db.insert(TABLE_FIRST_WEEK, null, values);
        }
    }

    public FirstWeekData getFirstWeekData(String babyName) {
        SQLiteDatabase db = this.getReadableDatabase();
        FirstWeekData data = null;

        try {
            Cursor cursor = db.query(
                    TABLE_FIRST_WEEK,
                    new String[]{COLUMN_HOMECOMING_DATE, COLUMN_FIRST_ADDRESS, COLUMN_FIRST_VISITORS},
                    COLUMN_BABY_NAME + " = ?",
                    new String[]{babyName},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                data = new FirstWeekData(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HOMECOMING_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_ADDRESS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_VISITORS))
                );
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting first week data", e);
        }

        return data;
    }

    // Add these methods at the end of the class
    public long saveBathtimeData(String babyName, String firstBath,
                                 String bathExperience, String bathGames, String bathToys) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BABY_NAME, babyName);
        values.put(COLUMN_FIRST_BATH, firstBath);
        values.put(COLUMN_BATH_EXPERIENCE, bathExperience);
        values.put(COLUMN_BATH_GAMES, bathGames);
        values.put(COLUMN_BATH_TOYS, bathToys);

        // Try to update existing record first
        int updatedRows = db.update(
                TABLE_BATHTIME,
                values,
                COLUMN_BABY_NAME + " = ?",
                new String[]{babyName}
        );

        if (updatedRows > 0) {
            return updatedRows;
        } else {
            // If no existing record, insert new one
            return db.insert(TABLE_BATHTIME, null, values);
        }
    }

    public BathtimeData getBathtimeData(String babyName) {
        SQLiteDatabase db = this.getReadableDatabase();
        BathtimeData data = null;

        try {
            Cursor cursor = db.query(
                    TABLE_BATHTIME,
                    new String[]{COLUMN_FIRST_BATH, COLUMN_BATH_EXPERIENCE,
                            COLUMN_BATH_GAMES, COLUMN_BATH_TOYS},
                    COLUMN_BABY_NAME + " = ?",
                    new String[]{babyName},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                data = new BathtimeData(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_BATH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BATH_EXPERIENCE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BATH_GAMES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BATH_TOYS))
                );
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting bathtime data", e);
        }

        return data;
    }

    // Add these methods at the end of the class
    public long saveFoodPreference(String babyName, String foodName, boolean tried) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BABY_NAME, babyName);
        values.put(COLUMN_FOOD_NAME, foodName);
        values.put(COLUMN_TRIED, tried ? 1 : 0);

        // Try to update existing record first
        int updatedRows = db.update(
                TABLE_FOOD_PREFERENCES,
                values,
                COLUMN_BABY_NAME + " = ? AND " + COLUMN_FOOD_NAME + " = ?",
                new String[]{babyName, foodName}
        );

        if (updatedRows > 0) {
            return updatedRows;
        } else {
            // If no existing record, insert new one
            return db.insert(TABLE_FOOD_PREFERENCES, null, values);
        }
    }

    public List<FoodData> getFoodPreferences(String babyName) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<FoodData> foodPreferences = new ArrayList<>();

        try {
            Cursor cursor = db.query(
                    TABLE_FOOD_PREFERENCES,
                    new String[]{COLUMN_FOOD_NAME, COLUMN_TRIED},
                    COLUMN_BABY_NAME + " = ?",
                    new String[]{babyName},
                    null,
                    null,
                    COLUMN_FOOD_NAME + " ASC"
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    FoodData data = new FoodData(
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOOD_NAME)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TRIED)) == 1
                    );
                    foodPreferences.add(data);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting food preferences", e);
        }

        return foodPreferences;
    }

    // Add these methods at the end of the class
    public long saveOnTheMoveData(String babyName, String liftHead, String rollOver,
                                  String findFeet, String sitUp, String clapped,
                                  String stoodUp, String firstSteps) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BABY_NAME, babyName);
        values.put(COLUMN_LIFT_HEAD, liftHead);
        values.put(COLUMN_ROLL_OVER, rollOver);
        values.put(COLUMN_FIND_FEET, findFeet);
        values.put(COLUMN_SIT_UP, sitUp);
        values.put(COLUMN_CLAPPED, clapped);
        values.put(COLUMN_STOOD_UP, stoodUp);
        values.put(COLUMN_FIRST_STEPS, firstSteps);

        // Try to update existing record first
        int updatedRows = db.update(
                TABLE_ON_THE_MOVE,
                values,
                COLUMN_BABY_NAME + " = ?",
                new String[]{babyName}
        );

        if (updatedRows > 0) {
            return updatedRows;
        } else {
            // If no existing record, insert new one
            return db.insert(TABLE_ON_THE_MOVE, null, values);
        }
    }

    public OnTheMoveData getOnTheMoveData(String babyName) {
        SQLiteDatabase db = this.getReadableDatabase();
        OnTheMoveData data = null;

        try {
            Cursor cursor = db.query(
                    TABLE_ON_THE_MOVE,
                    new String[]{COLUMN_LIFT_HEAD, COLUMN_ROLL_OVER, COLUMN_FIND_FEET,
                            COLUMN_SIT_UP, COLUMN_CLAPPED, COLUMN_STOOD_UP, COLUMN_FIRST_STEPS},
                    COLUMN_BABY_NAME + " = ?",
                    new String[]{babyName},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                data = new OnTheMoveData(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LIFT_HEAD)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLL_OVER)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIND_FEET)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SIT_UP)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLAPPED)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STOOD_UP)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_STEPS))
                );
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting on the move data", e);
        }

        return data;
    }

    // Add these methods at the end of the class
    public long saveTellingEveryoneData(String babyName, String mamFoundOut, String dadFoundOut,
                                        String mamFirstTold, String dadFirstTold, String peopleReactions)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BABY_NAME, babyName);
        values.put(COLUMN_MAM_FOUND_OUT, mamFoundOut);
        values.put(COLUMN_DAD_FOUND_OUT, dadFoundOut);
        values.put(COLUMN_MAM_FIRST_TOLD, mamFirstTold);
        values.put(COLUMN_DAD_FIRST_TOLD, dadFirstTold);
        values.put(COLUMN_PEOPLE_REACTIONS, peopleReactions);

        // Try to update existing record first
        int updatedRows = db.update(
                TABLE_TELLING_EVERYONE,
                values,
                COLUMN_BABY_NAME + " = ?",
                new String[]{babyName}
        );

        if (updatedRows > 0) {
            return updatedRows;
        } else {
            // If no existing record, insert new one
            return db.insert(TABLE_TELLING_EVERYONE, null, values);
        }
    }

    public TellingEveryoneData getTellingEveryoneData(String babyName) {
        SQLiteDatabase db = this.getReadableDatabase();
        TellingEveryoneData data = null;

        try {
            Cursor cursor = db.query(
                    TABLE_TELLING_EVERYONE,
                    null,  // Get all columns
                    COLUMN_BABY_NAME + " = ?",
                    new String[]{babyName},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                data = new TellingEveryoneData(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAM_FOUND_OUT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAD_FOUND_OUT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAM_FIRST_TOLD)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAD_FIRST_TOLD)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PEOPLE_REACTIONS))
                );
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting telling everyone data", e);
        }

        return data;
    }

    // Add these methods at the end of the class
    public long saveParentsData(String babyName, String motherName, String fatherName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_BABY_NAME, babyName);
        values.put(COLUMN_MOTHER_NAME, motherName);
        values.put(COLUMN_FATHER_NAME, fatherName);

        // Try to update existing record first
        int updatedRows = db.update(
                TABLE_PARENTS,
                values,
                COLUMN_BABY_NAME + " = ?",
                new String[]{babyName}
        );

        if (updatedRows > 0) {
            return updatedRows;
        } else {
            // If no existing record, insert new one
            return db.insert(TABLE_PARENTS, null, values);
        }
    }

    public ParentsData getParentsData(String babyName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ParentsData data = null;

        try {
            Cursor cursor = db.query(
                    TABLE_PARENTS,
                    new String[]{COLUMN_MOTHER_NAME, COLUMN_FATHER_NAME},
                    COLUMN_BABY_NAME + " = ?",
                    new String[]{babyName},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                data = new ParentsData(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOTHER_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FATHER_NAME))
                );
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting parents data", e);
        }

        return data;
    }

    public BirthDetails getBirthDetails(String babyName) {
        SQLiteDatabase db = this.getReadableDatabase();
        BirthDetails data = null;

        try {
            Cursor cursor = db.query(
                    TABLE_BIRTH_DETAILS,
                    new String[]{COLUMN_BIRTH_DATE, COLUMN_BIRTH_WEIGHT, 
                               COLUMN_BIRTH_FEATURES, COLUMN_BIRTH_ATTENDEES},
                    COLUMN_BABY_NAME + " = ?",
                    new String[]{babyName},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                data = new BirthDetails(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH_WEIGHT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH_FEATURES)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH_ATTENDEES))
                );
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error getting birth details data", e);
        }

        return data;
    }
}
