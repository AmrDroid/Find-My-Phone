package amrmustafa.com.findmyphone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

public class LocationService
  extends Service
{
  public static final String BROADCAST_ACTION = "Hello World";
  public static boolean ServiceIsRun = false;
  public static Location location = null;
  Intent intent;
  DatabaseReference mDatabase;
  MyLocationListener myLocationListener;
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    super.onCreate();
    this.intent = new Intent("Hello World");
    ServiceIsRun = true;
    this.mDatabase = FirebaseDatabase.getInstance().getReference();
  }
  
  public void onDestroy()
  {
    super.onDestroy();
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    new SettingSaved(getApplicationContext()).LoadData();
    this.myLocationListener = new MyLocationListener();
    if ((ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) && (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0))
    {
      paramIntent = (LocationManager)getSystemService("location");
      if (paramIntent.getAllProviders().contains("gps"))
      {
        paramIntent.requestLocationUpdates("gps", 0L, 0.0F, this.myLocationListener);
        this.mDatabase.child("FindMyPhoneUsers").child(SharedData.PhoneNumber).child("Updates").addValueEventListener(new ValueEventListener()
        {
          public void onCancelled(DatabaseError paramAnonymousDatabaseError) {}
          
          public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
          {
            if ((String)paramAnonymousDataSnapshot.getValue(String.class) == null) {
              return;
            }
            paramAnonymousDataSnapshot = new ManagmentOperations(LocationService.this.getApplicationContext());
            paramAnonymousDataSnapshot.SendGPS(paramAnonymousDataSnapshot.getBatteryLevel());
          }
        });
        this.mDatabase.child("FindMyPhoneUsers").child(SharedData.PhoneNumber).child("commands").addValueEventListener(new ValueEventListener()
        {
          public void onCancelled(DatabaseError paramAnonymousDatabaseError) {}
          
          public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
          {
            paramAnonymousDataSnapshot = (Integer)paramAnonymousDataSnapshot.getValue(Integer.class);
            if (paramAnonymousDataSnapshot == null) {}
            while (paramAnonymousDataSnapshot.intValue() != CommandType.Vibration.getCommand()) {
              return;
            }
            ((Vibrator)LocationService.this.getSystemService("vibrator")).vibrate(5000L);
            ((AudioManager)LocationService.this.getSystemService("audio")).setRingerMode(2);
            try
            {
              paramAnonymousDataSnapshot = (AudioManager)LocationService.this.getSystemService("audio");
              paramAnonymousDataSnapshot.setStreamVolume(3, paramAnonymousDataSnapshot.getStreamMaxVolume(3), 0);
              MediaPlayer.create(LocationService.this.getApplicationContext(), 2131099648).start();
              FirebaseDatabase.getInstance().getReference().child("FindMyPhoneUsers").child(SharedData.PhoneNumber).child("commands").setValue(Integer.valueOf(CommandType.IsVibration.getCommand()));
              return;
            }
            catch (Exception paramAnonymousDataSnapshot)
            {
              for (;;) {}
            }
          }
        });
      }
    }
    for (;;)
    {
      return 2;
      if (!paramIntent.getAllProviders().contains("network")) {
        break;
      }
      paramIntent.requestLocationUpdates("network", 0L, 0.0F, this.myLocationListener);
      break;
      try
      {
        ServiceIsRun = false;
        paramIntent = new Intent(getApplicationContext(), MainActivity.class);
        paramIntent.setFlags(268435456);
        getApplicationContext().startActivity(paramIntent);
        stopService(new Intent(getApplicationContext(), LocationService.class));
      }
      catch (Exception paramIntent)
      {
        FirebaseCrash.report(new Exception("LocationServiceStartActivity:" + paramIntent.getMessage()));
      }
    }
  }
  
  public class MyLocationListener
    implements LocationListener
  {
    public MyLocationListener() {}
    
    public void onLocationChanged(Location paramLocation)
    {
      LocationService.location = paramLocation;
    }
    
    public void onProviderDisabled(String paramString)
    {
      Toast.makeText(LocationService.this.getApplicationContext(), LocationService.this.getApplicationContext().getResources().getString(2131165282), 1).show();
    }
    
    public void onProviderEnabled(String paramString)
    {
      paramString = new ManagmentOperations(LocationService.this.getApplicationContext());
      paramString.SendGPS(paramString.getBatteryLevel());
    }
    
    public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle) {}
  }
}


/* Location:              C:\apktool\dex2jar-2.0\findmy-dex2jar.jar!\phonelocation\example\asuss550c\phonelocation\LocationService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */