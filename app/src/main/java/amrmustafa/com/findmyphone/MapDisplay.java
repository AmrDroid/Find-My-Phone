package amrmustafa.com.findmyphone;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapDisplay
  extends AppCompatActivity
  implements OnMapReadyCallback
{
  double Battery = -1.0D;
  boolean PhoneIsVabrating = false;
  String PhoneTracking = "";
  MenuItem item = null;
  DatabaseReference mDatabase;
  InterstitialAd mInterstitialAd;
  GoogleMap map;
  ProgressBar pbLoading;
  LatLng sydney;
  
  private void DisplayAdmob()
  {
    if (this.mInterstitialAd.isLoaded()) {
      this.mInterstitialAd.show();
    }
    if (!this.mInterstitialAd.isLoaded())
    {
      AdRequest localAdRequest = new AdRequest.Builder().build();
      this.mInterstitialAd.loadAd(localAdRequest);
    }
  }
  
  private void LoadAdmob()
  {
    try
    {
      this.mInterstitialAd = new InterstitialAd(this);
      this.mInterstitialAd.setAdUnitId(getResources().getString(2131165257));
      AdRequest localAdRequest = new AdRequest.Builder().build();
      this.mInterstitialAd.loadAd(localAdRequest);
      this.mInterstitialAd.setAdListener(new AdListener()
      {
        public void onAdClosed() {}
        
        public void onAdLoaded()
        {
          MapDisplay.this.DisplayAdmob();
        }
      });
      return;
    }
    catch (Exception localException) {}
  }
  
  private void PreparMarker(Bundle paramBundle)
  {
    this.PhoneTracking = paramBundle.getString("PhoneNumber");
    if (paramBundle.getString("Mode").equals("Offline"))
    {
      paramBundle = "";
      try
      {
        String str = SettingSaved.Userlocationinthemap;
        paramBundle = str;
        String[] arrayOfString = str.split("%");
        paramBundle = str;
        if (arrayOfString.length < 1)
        {
          paramBundle = str;
          finish();
        }
        paramBundle = str;
        arrayOfString = arrayOfString[1].substring(29, arrayOfString[1].length() - 1).split(",");
        paramBundle = str;
        if (arrayOfString.length < 1)
        {
          paramBundle = str;
          finish();
        }
        paramBundle = str;
        this.sydney = new LatLng(Double.parseDouble(arrayOfString[0]), Double.parseDouble(arrayOfString[1]));
      }
      catch (Exception localException)
      {
        for (;;)
        {
          FirebaseCrash.report(new Exception("Index error:" + paramBundle));
          finish();
        }
      }
      LoadMap();
      return;
    }
    try
    {
      this.mDatabase = FirebaseDatabase.getInstance().getReference();
      this.mDatabase.child("FindMyPhoneUsers").child(this.PhoneTracking).child("Location").addValueEventListener(new ValueEventListener()
      {
        public void onCancelled(DatabaseError paramAnonymousDatabaseError) {}
        
        /* Error */
        public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
        {
          // Byte code:
          //   0: aload_1
          //   1: invokevirtual 33	com/google/firebase/database/DataSnapshot:getValue	()Ljava/lang/Object;
          //   4: checkcast 35	java/util/HashMap
          //   7: astore_1
          //   8: aload_1
          //   9: ifnonnull +4 -> 13
          //   12: return
          //   13: aload_0
          //   14: getfield 17	phonelocation/example/asuss550c/phonelocation/MapDisplay$1:this$0	Lphonelocation/example/asuss550c/phonelocation/MapDisplay;
          //   17: iconst_1
          //   18: putfield 39	phonelocation/example/asuss550c/phonelocation/MapDisplay:PhoneIsVabrating	Z
          //   21: aload_1
          //   22: ldc 41
          //   24: invokeinterface 47 2 0
          //   29: invokevirtual 51	java/lang/Object:toString	()Ljava/lang/String;
          //   32: invokestatic 57	java/lang/Double:parseDouble	(Ljava/lang/String;)D
          //   35: dstore_2
          //   36: aload_1
          //   37: ldc 59
          //   39: invokeinterface 47 2 0
          //   44: invokevirtual 51	java/lang/Object:toString	()Ljava/lang/String;
          //   47: invokestatic 57	java/lang/Double:parseDouble	(Ljava/lang/String;)D
          //   50: dstore 4
          //   52: aload_0
          //   53: getfield 17	phonelocation/example/asuss550c/phonelocation/MapDisplay$1:this$0	Lphonelocation/example/asuss550c/phonelocation/MapDisplay;
          //   56: aload_1
          //   57: ldc 61
          //   59: invokeinterface 47 2 0
          //   64: invokevirtual 51	java/lang/Object:toString	()Ljava/lang/String;
          //   67: invokestatic 57	java/lang/Double:parseDouble	(Ljava/lang/String;)D
          //   70: putfield 64	phonelocation/example/asuss550c/phonelocation/MapDisplay:Battery	D
          //   73: aload_0
          //   74: getfield 17	phonelocation/example/asuss550c/phonelocation/MapDisplay$1:this$0	Lphonelocation/example/asuss550c/phonelocation/MapDisplay;
          //   77: new 66	com/google/android/gms/maps/model/LatLng
          //   80: dup
          //   81: dload_2
          //   82: dload 4
          //   84: invokespecial 69	com/google/android/gms/maps/model/LatLng:<init>	(DD)V
          //   87: putfield 73	phonelocation/example/asuss550c/phonelocation/MapDisplay:sydney	Lcom/google/android/gms/maps/model/LatLng;
          //   90: new 75	java/lang/StringBuilder
          //   93: dup
          //   94: invokespecial 76	java/lang/StringBuilder:<init>	()V
          //   97: aload_0
          //   98: getfield 17	phonelocation/example/asuss550c/phonelocation/MapDisplay$1:this$0	Lphonelocation/example/asuss550c/phonelocation/MapDisplay;
          //   101: invokevirtual 80	phonelocation/example/asuss550c/phonelocation/MapDisplay:getApplicationContext	()Landroid/content/Context;
          //   104: aload_0
          //   105: getfield 17	phonelocation/example/asuss550c/phonelocation/MapDisplay$1:this$0	Lphonelocation/example/asuss550c/phonelocation/MapDisplay;
          //   108: getfield 84	phonelocation/example/asuss550c/phonelocation/MapDisplay:PhoneTracking	Ljava/lang/String;
          //   111: invokestatic 90	phonelocation/example/asuss550c/phonelocation/NewMessageNotification:getContactName	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
          //   114: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   117: ldc 96
          //   119: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   122: aload_0
          //   123: getfield 17	phonelocation/example/asuss550c/phonelocation/MapDisplay$1:this$0	Lphonelocation/example/asuss550c/phonelocation/MapDisplay;
          //   126: invokevirtual 100	phonelocation/example/asuss550c/phonelocation/MapDisplay:getResources	()Landroid/content/res/Resources;
          //   129: ldc 101
          //   131: invokevirtual 107	android/content/res/Resources:getString	(I)Ljava/lang/String;
          //   134: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   137: aload_1
          //   138: ldc 109
          //   140: invokeinterface 47 2 0
          //   145: invokevirtual 51	java/lang/Object:toString	()Ljava/lang/String;
          //   148: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   151: invokevirtual 110	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   154: putstatic 115	phonelocation/example/asuss550c/phonelocation/SettingSaved:Userlocationinthemap	Ljava/lang/String;
          //   157: aload_0
          //   158: getfield 17	phonelocation/example/asuss550c/phonelocation/MapDisplay$1:this$0	Lphonelocation/example/asuss550c/phonelocation/MapDisplay;
          //   161: invokevirtual 118	phonelocation/example/asuss550c/phonelocation/MapDisplay:LoadMap	()V
          //   164: aload_0
          //   165: getfield 17	phonelocation/example/asuss550c/phonelocation/MapDisplay$1:this$0	Lphonelocation/example/asuss550c/phonelocation/MapDisplay;
          //   168: invokevirtual 121	phonelocation/example/asuss550c/phonelocation/MapDisplay:EnableItem	()V
          //   171: return
          //   172: astore_1
          //   173: aload_0
          //   174: getfield 17	phonelocation/example/asuss550c/phonelocation/MapDisplay$1:this$0	Lphonelocation/example/asuss550c/phonelocation/MapDisplay;
          //   177: invokevirtual 80	phonelocation/example/asuss550c/phonelocation/MapDisplay:getApplicationContext	()Landroid/content/Context;
          //   180: aload_0
          //   181: getfield 17	phonelocation/example/asuss550c/phonelocation/MapDisplay$1:this$0	Lphonelocation/example/asuss550c/phonelocation/MapDisplay;
          //   184: invokevirtual 80	phonelocation/example/asuss550c/phonelocation/MapDisplay:getApplicationContext	()Landroid/content/Context;
          //   187: invokevirtual 124	android/content/Context:getResources	()Landroid/content/res/Resources;
          //   190: ldc 125
          //   192: invokevirtual 107	android/content/res/Resources:getString	(I)Ljava/lang/String;
          //   195: iconst_1
          //   196: invokestatic 131	android/widget/Toast:makeText	(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;
          //   199: invokevirtual 134	android/widget/Toast:show	()V
          //   202: aload_0
          //   203: getfield 17	phonelocation/example/asuss550c/phonelocation/MapDisplay$1:this$0	Lphonelocation/example/asuss550c/phonelocation/MapDisplay;
          //   206: invokevirtual 137	phonelocation/example/asuss550c/phonelocation/MapDisplay:finish	()V
          //   209: new 27	java/lang/Exception
          //   212: dup
          //   213: new 75	java/lang/StringBuilder
          //   216: dup
          //   217: invokespecial 76	java/lang/StringBuilder:<init>	()V
          //   220: ldc -117
          //   222: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   225: aload_1
          //   226: invokevirtual 142	java/lang/Exception:getMessage	()Ljava/lang/String;
          //   229: invokevirtual 94	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   232: invokevirtual 110	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   235: invokespecial 145	java/lang/Exception:<init>	(Ljava/lang/String;)V
          //   238: invokestatic 151	com/google/firebase/crash/FirebaseCrash:report	(Ljava/lang/Throwable;)V
          //   241: return
          //   242: astore 6
          //   244: goto -171 -> 73
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	247	0	this	1
          //   0	247	1	paramAnonymousDataSnapshot	DataSnapshot
          //   35	47	2	d1	double
          //   50	33	4	d2	double
          //   242	1	6	localException	Exception
          // Exception table:
          //   from	to	target	type
          //   21	52	172	java/lang/Exception
          //   73	171	172	java/lang/Exception
          //   52	73	242	java/lang/Exception
        }
      });
      this.mDatabase.child("FindMyPhoneUsers").child(this.PhoneTracking).child("commands").addValueEventListener(new ValueEventListener()
      {
        public void onCancelled(DatabaseError paramAnonymousDatabaseError) {}
        
        public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
        {
          paramAnonymousDataSnapshot = (Integer)paramAnonymousDataSnapshot.getValue(Integer.class);
          if (paramAnonymousDataSnapshot == null) {
            return;
          }
          if (paramAnonymousDataSnapshot.intValue() == CommandType.IsVibration.getCommand())
          {
            MapDisplay.this.PhoneIsVabrating = true;
            ManagmentOperations.SendCommands(MapDisplay.this.PhoneTracking, CommandType.NoCommand.getCommand());
            MapDisplay.this.Battery = -10.0D;
            MapDisplay.this.LoadMap();
          }
          MapDisplay.this.EnableItem();
        }
      });
      return;
    }
    catch (Exception paramBundle)
    {
      FirebaseCrash.report(new Exception("ShowMap:" + paramBundle.getMessage()));
    }
  }
  
  void AddMapdetails()
  {
    this.map.clear();
    this.map.moveCamera(CameraUpdateFactory.newLatLngZoom(this.sydney, 18.0F));
    Object localObject = SettingSaved.Userlocationinthemap.split("%");
    if (localObject.length < 3) {
      return;
    }
    String str = localObject[2];
    if (getResources().getString(2131165280).equalsIgnoreCase(localObject[0])) {
      localObject = localObject[0];
    }
    for (;;)
    {
      this.map.addMarker(new MarkerOptions().title((String)localObject).snippet(str).icon(BitmapDescriptorFactory.fromResource(2130837644)).anchor(0.0F, 1.0F).position(this.sydney)).showInfoWindow();
      return;
      if ((int)this.Battery == -10)
      {
        localObject = getResources().getString(2131165283);
      }
      else if ((int)this.Battery == 0)
      {
        localObject = getResources().getString(2131165289);
      }
      else if (this.Battery > 0.0D)
      {
        localObject = getResources().getString(2131165279) + "(" + String.valueOf((int)this.Battery) + "%)";
      }
      else
      {
        str = this.PhoneTracking;
        localObject = new ManagmentOperations(this).NumberToName(this.PhoneTracking);
      }
    }
  }
  
  void CheckRate()
  {
    if ((SettingSaved.IsRated == 0) && (SettingSaved.ShowAds % 2 == 0))
    {
      Builder localBuilder = new Builder(this);
      localBuilder.setMessage(getResources().getString(2131165258)).setCancelable(false).setPositiveButton(getResources().getString(2131165274), new OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface = new Intent("android.intent.action.VIEW");
          paramAnonymousDialogInterface.setData(Uri.parse("market://details?id=phonelocation.example.asuss550c.phonelocationphone"));
          paramAnonymousDialogInterface.setData(Uri.parse("https://play.google.com/store/apps/details?id=phonelocation.example.asuss550c.phonelocationphone"));
          MapDisplay.this.startActivity(paramAnonymousDialogInterface);
          SettingSaved.IsRated = 1;
          new SettingSaved(MapDisplay.this.getApplicationContext()).SaveData();
          MapDisplay.this.finish();
        }
      }).setNegativeButton(getResources().getString(2131165253), new OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          MapDisplay.this.finish();
        }
      });
      localBuilder.create().show();
      return;
    }
    finish();
  }
  
  void DisableItem()
  {
    this.item.setEnabled(false);
    this.item.getIcon().setAlpha(130);
    this.pbLoading.setVisibility(0);
  }
  
  void EnableItem()
  {
    if (this.item != null)
    {
      this.item.setEnabled(true);
      this.item.getIcon().setAlpha(255);
      this.pbLoading.setVisibility(8);
    }
  }
  
  void LoadMap()
  {
    try
    {
      ((MapFragment)getFragmentManager().findFragmentById(2131624067)).getMapAsync(this);
      return;
    }
    catch (Exception localException) {}
  }
  
  void WaitingForLoading()
  {
    this.PhoneIsVabrating = false;
    new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          Thread.sleep(15000L);
          MapDisplay.this.runOnUiThread(new Runnable()
          {
            public void run()
            {
              MapDisplay.this.pbLoading.setVisibility(8);
              if (!MapDisplay.this.PhoneIsVabrating)
              {
                SettingSaved.Userlocationinthemap = MapDisplay.this.getResources().getString(2131165280) + "%b%" + MapDisplay.this.getResources().getString(2131165300);
                MapDisplay.this.LoadMap();
                MapDisplay.this.EnableItem();
              }
            }
          });
          return;
        }
        catch (InterruptedException localInterruptedException)
        {
          for (;;)
          {
            localInterruptedException.printStackTrace();
          }
        }
      }
    }).start();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903069);
    PreparMarker(getIntent().getExtras());
    this.pbLoading = ((ProgressBar)findViewById(2131624066));
    this.pbLoading.setVisibility(8);
    if (SettingSaved.ShowAds % 2 == 0) {
      LoadAdmob();
    }
    SettingSaved.ShowAds += 1;
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131689475, paramMenu);
    return true;
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4) {
      CheckRate();
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public void onMapReady(GoogleMap paramGoogleMap)
  {
    this.map = paramGoogleMap;
    this.map.getUiSettings().setZoomControlsEnabled(true);
    if ((ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) && (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0)) {
      return;
    }
    paramGoogleMap.setMyLocationEnabled(true);
    AddMapdetails();
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    int i = paramMenuItem.getItemId();
    this.item = paramMenuItem;
    if (i == 2131624132) {}
    try
    {
      Object localObject;
      if (this.sydney.latitude > 0.0D)
      {
        localObject = new Intent(this, VisitLocations.class);
        ((Intent)localObject).putExtra("latitude", this.sydney.latitude);
        ((Intent)localObject).putExtra("longitude", this.sydney.longitude);
        startActivity((Intent)localObject);
      }
      for (;;)
      {
        return super.onOptionsItemSelected(paramMenuItem);
        if (i == 2131624133)
        {
          localObject = new SettingSaved(getApplicationContext()).getLocation();
          startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?saddr=" + ((Location)localObject).getLatitude() + "," + ((Location)localObject).getLongitude() + "&daddr=" + this.sydney.latitude + "," + this.sydney.longitude)));
        }
        else if (i == 2131624129)
        {
          CheckRate();
        }
        else if (i == 2131624134)
        {
          if (this.PhoneTracking.length() > 0)
          {
            DisableItem();
            ManagmentOperations.updateLocation(this.PhoneTracking);
            WaitingForLoading();
          }
        }
        else if (i == 2131624131)
        {
          if (this.PhoneTracking.length() > 0)
          {
            SmsManager.getDefault().sendTextMessage(this.PhoneTracking, null, "Yo@urLoca#5tionPlea%se", null, null);
            Toast.makeText(this, getResources().getString(2131165273) + " " + this.PhoneTracking + " " + getResources().getString(2131165271), 1).show();
            finish();
          }
        }
        else if (i == 2131624130)
        {
          DisableItem();
          ManagmentOperations.SendCommands(this.PhoneTracking, CommandType.Vibration.getCommand());
          WaitingForLoading();
        }
      }
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
  }
}


/* Location:              C:\apktool\dex2jar-2.0\findmy-dex2jar.jar!\phonelocation\example\asuss550c\phonelocation\MapDisplay.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */