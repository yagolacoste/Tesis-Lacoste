package com.Tesis.bicycle;

import android.content.Context;

import org.osmdroid.util.GeoPoint;

public class Constants {

   public static final int REQUEST_CODE=1;
   public static final int MAX_CARACTER_ID=10;
   public static final String NAME_DATA_BASE="Bicycle";
   public static final int LOCATION_SERVICE_ID=175;
   public  static final String ACTION_START_LOCATION_SERVICE="startLocationService";
   public static final String ACTION_STOP_LOCATION_SERVICE="stopLocationService";
   public static final int DEFAULT_UPDATE_INTERVAL = 10;
   public  static final int FAST_UPDATE_INTERVAL = 5;
   public static final String TRACKING="tracking";
   public static final String ACTION_UPDATE_SERVICE="location_update";
   public static final String REPLAY_MY_ROUTE="replay_my_route";
   public static final String ACTION_REPLAY_OTHER_ROUTES="replay_other_routes";
   public static final String ACTION_REPLAY_MY_ROUTES="replay_my_routes";
   public static final String VIEW_STATISTICS="view_statistics";

   public static GeoPoint LOCATION_INITIAL=new GeoPoint(-37.32345,-59.12558);





   ////////////////////// Constants defined for route/////////////////////////////
   public static final String ROUTE="Route";
   public static final String ROUTE_ID="route_id";
   public static final String ACTION_VIEW_MY_ROUTES="view_my_routes";

   public static final String ROUTE_ITEM="route_item";
   public static final String ROUTE_SELECT="route_select";

   /////////////////////Constants defined for User/////////////////////////
   public static final String USER_ITEM="User_item";
   public static final String USER_SELECT="User_item";

   public static final String ACTION_VIEW_FRIENDS="view_my_friends";


   //////////////////Constants defined for battle///////////////////
   public final static String BATTLE_ITEM="Battle";

   public final static String REPLAY_BATTLE="Battle_replay";
   public final static String BATTLE_ID="Battle_ID";

}
