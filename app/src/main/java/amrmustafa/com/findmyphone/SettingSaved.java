package amrmustafa.com.findmyphone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.telephony.SmsManager;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SettingSaved
{
  public static String HashCode;
  public static boolean ISLocForLocation = false;
  public static int IsRated = 0;
  public static final String MyPREFERENCES = "MyPrefsFindPhone1";
  public static int NumberDigit;
  public static int SeenNewUpdates = 0;
  public static int ShowAds;
  public static String UserPhoneNumber = "";
  public static String Userlocationinthemap;
  public static Map<String, String> WhoFindMeIN;
  Context context;
  SharedPreferences sharedpreferences;
  public String version = "1.8.13";
  
  static
  {
    HashCode = "";
    ShowAds = 1;
    NumberDigit = 10;
    Userlocationinthemap = "77:43";
    ISLocForLocation = false;
    WhoFindMeIN = new HashMap();
  }
  
  public SettingSaved(Context paramContext)
  {
    this.context = paramContext;
    this.sharedpreferences = paramContext.getSharedPreferences("MyPrefsFindPhone1", 0);
  }
  
  public void GetPhoneNumber()
  {
    SharedData.PhoneNumber = this.sharedpreferences.getString("PhoneNumber1", "empty");
  }
  
  public void LoadData()
  {
    try
    {
      Object localObject2 = this.sharedpreferences.getString("WhoFindMeIN", "empty");
      SharedData.PhoneNumber = this.sharedpreferences.getString("PhoneNumber1", "empty");
      this.version = this.sharedpreferences.getString("version", "no version");
      IsRated = this.sharedpreferences.getInt("IsRated", 0);
      SeenNewUpdates = this.sharedpreferences.getInt("SeenNewUpdates", 0);
      Object localObject1 = "";
      WhoFindMeIN.clear();
      if (!((String)localObject2).equals("empty"))
      {
        String[] arrayOfString = ((String)localObject2).split("%");
        localObject2 = localObject1;
        if (arrayOfString.length > 1)
        {
          int i = 0;
          for (;;)
          {
            localObject2 = localObject1;
            if (i >= arrayOfString.length) {
              break;
            }
            WhoFindMeIN.put(arrayOfString[i], arrayOfString[(i + 1)]);
            localObject1 = (String)localObject1 + ":" + arrayOfString[i];
            i += 2;
          }
        }
        UserPhoneNumber = (String)localObject2;
      }
      if (SharedData.PhoneNumber.equals("empty"))
      {
        localObject1 = new Intent(this.context, PhoneVerify.class);
        ((Intent)localObject1).addFlags(268435456);
        this.context.startActivity((Intent)localObject1);
      }
      return;
    }
    catch (Exception localException) {}
  }
  
  public void SaveData()
  {
    String str = "";
    Object localObject = WhoFindMeIN.entrySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      Entry localEntry = (Entry)((Iterator)localObject).next();
      if (str.length() == 0) {
        str = localEntry.getKey() + "%" + localEntry.getValue();
      } else {
        str = str + "%" + localEntry.getKey() + "%" + localEntry.getValue();
      }
    }
    if (WhoFindMeIN.size() == 0) {
      str = "empty";
    }
    try
    {
      localObject = this.sharedpreferences.edit();
      ((Editor)localObject).putString("WhoFindMeIN", str);
      ((Editor)localObject).putInt("IsRated", IsRated);
      ((Editor)localObject).putString("PhoneNumber1", SharedData.PhoneNumber);
      ((Editor)localObject).putInt("SeenNewUpdates", SeenNewUpdates);
      ((Editor)localObject).commit();
      LoadData();
      return;
    }
    catch (Exception localException) {}
  }
  
  public void SavePhoneNumberOnly()
  {
    try
    {
      Editor localEditor = this.sharedpreferences.edit();
      localEditor.putString("PhoneNumber1", SharedData.PhoneNumber);
      localEditor.putString("version", this.version);
      localEditor.commit();
      return;
    }
    catch (Exception localException) {}
  }
  
  public Location getLocation()
  {
    Location localLocation = LocationService.location;
    if (localLocation == null) {
      try
      {
        Object localObject1 = (LocationManager)this.context.getSystemService("location");
        if (localObject1 != null)
        {
          Object localObject2 = new Criteria();
          ((Criteria)localObject2).setAccuracy(1);
          ((Criteria)localObject2).setCostAllowed(false);
          localObject2 = ((LocationManager)localObject1).getLastKnownLocation(((LocationManager)localObject1).getBestProvider((Criteria)localObject2, false));
          if (localObject2 != null) {
            return (Location)localObject2;
          }
          localObject1 = ((LocationManager)localObject1).getLastKnownLocation("passive");
          return (Location)localObject1;
        }
        return null;
      }
      catch (Exception localException)
      {
        Toast.makeText(this.context, this.context.getResources().getString(2131165282), 1).show();
      }
    }
    return localLocation;
  }
  
  public void sendm(String paramString)
  {
    try
    {
      Location localLocation = new SettingSaved(this.context).getLocation();
      SmsManager.getDefault().sendTextMessage(paramString, null, "%Here is my phone %https://www.google.com/maps/@" + localLocation.getLatitude() + "," + localLocation.getLongitude() + ",15z/data=!4m2!3m1!1s0x0000000000000000:0x08cdd5fc704c0eb2", null, null);
      return;
    }
    catch (Exception paramString)
    {
      Toast.makeText(this.context, paramString.getMessage().toString(), 1).show();
    }
  }
}


/* Location:              C:\apktool\dex2jar-2.0\findmy-dex2jar.jar!\phonelocation\example\asuss550c\phonelocation\SettingSaved.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */