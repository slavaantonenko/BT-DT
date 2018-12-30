package com.msapplications.btdt.lists;

import android.content.Context;

import com.msapplications.btdt.CommonValues;
import com.msapplications.btdt.Utils;
import com.msapplications.btdt.objects.itemTypes.cinema.CinemaHall;

import java.util.ArrayList;

public class CinemasHallsList
{
    private static ArrayList<CinemaHall> halls = null;

    private CinemasHallsList(Context context)
    {
        halls = (ArrayList<CinemaHall>) Utils.getListFromCache(context.getCacheDir(), CommonValues.CACHE_CINEMAS_HALLS_KEY);

        if (halls == null)
            halls = new ArrayList<>();
    }

    public static ArrayList<CinemaHall> getHalls(Context context)
    {
        if (halls == null)
            new CinemasHallsList(context);

        return halls;
    }

    public static void addHall(Context context, CinemaHall newHall)
    {
        if (halls == null)
            new CinemasHallsList(context);

        halls.add(newHall);
        updateHallsCacheExtras(context, halls);
    }

//    public static void deleteHall(Context context, String cinemaName, String cinemaCity)
//    {
//        if (halls == null)
//            new CinemasHallsList(context);
//
//        for (Cinema cinema : halls)
//        {
//            if (cinema.getName().equals(cinemaName) && cinema.getCity().equals(cinemaCity)) {
//                halls.remove(cinema);
//                break;
//            }
//        }
//
//        updateHallsCacheExtras(context, halls);
//    }

//    public static boolean cinemaExist(String hall)
//    {
//        for (Cinema cinema : halls)
//            if (cinema.getName().equals(cinemaName) && cinema.getCity().equals(cinemaCity))
//                return true;
//
//        return false;
//    }

    private static void updateHallsCacheExtras(Context mContext, ArrayList<CinemaHall> halls)
    {
        Utils.saveListToCache(mContext.getCacheDir(), halls, CommonValues.CACHE_CINEMAS_HALLS_KEY); // save halls to cache for next time
        //new CategoryList(mContext);
    }
}
