package amrmustafa.com.findmyphone;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ManagmentOperations
{
  Context context;
  
  public ManagmentOperations() {}
  
  public ManagmentOperations(Context paramContext)
  {
    this.context = paramContext;
  }
  
  public static String FormatPhoneNumber(String paramString)
  {
    try
    {
      String str2 = paramString.replaceAll("[^0-9]", "");
      String str1 = str2;
      if (paramString.charAt(0) == '+') {
        str1 = "+" + str2;
      }
      paramString = str1;
      if (str1.length() >= 10) {
        paramString = str1.substring(str1.length() - 10, str1.length());
      }
      return paramString;
    }
    catch (Exception paramString) {}
    return " ";
  }
  
  public static void SendCommands(String paramString, int paramInt)
  {
    FirebaseDatabase.getInstance().getReference().child("FindMyPhoneUsers").child(paramString).child("commands").setValue(Integer.valueOf(paramInt));
  }
  
  public static void updateLocation(String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date localDate = new Date();
    FirebaseDatabase.getInstance().getReference().child("FindMyPhoneUsers").child(paramString).child("Updates").setValue(localSimpleDateFormat.format(localDate).toString());
  }
  
  public boolean IsPhoneAut(String paramString1, String paramString2)
  {
    boolean bool2 = false;
    paramString2 = paramString2.split(":");
    int i = 0;
    for (;;)
    {
      boolean bool1 = bool2;
      if (i < paramString2.length)
      {
        if (((paramString1.contains(paramString2[i])) || (paramString2[i].contains(paramString1))) && (paramString2[i].length() > 0)) {
          bool1 = true;
        }
      }
      else {
        return bool1;
      }
      i += 1;
    }
  }
  
  public String NumberToName(String paramString)
  {
    String str1 = paramString;
    try
    {
      Cursor localCursor = this.context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
      String str2;
      boolean bool;
      do
      {
        str2 = str1;
        if (!localCursor.moveToNext()) {
          break;
        }
        str2 = localCursor.getString(localCursor.getColumnIndex("display_name"));
        String str3 = FormatPhoneNumber(localCursor.getString(localCursor.getColumnIndex("data1")));
        if (str3.contains(paramString)) {
          break;
        }
        bool = paramString.contains(str3);
      } while (!bool);
      return str2;
    }
    catch (Exception paramString) {}
    return str1;
  }
  
  public void PrepareUser(String paramString1, String paramString2)
  {
    SendCommands(paramString1, CommandType.NoCommand.getCommand());
    updateLocation(paramString1);
    SendGPS(getBatteryLevel());
  }
  
  public void SendGPS(double paramDouble)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date localDate = new Date();
    Object localObject = new SettingSaved(this.context);
    if (SharedData.PhoneNumber.equalsIgnoreCase("empty")) {
      ((SettingSaved)localObject).GetPhoneNumber();
    }
    localObject = ((SettingSaved)localObject).getLocation();
    if (localObject == null) {
      return;
    }
    DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();
    localDatabaseReference.child("FindMyPhoneUsers").child(SharedData.PhoneNumber).child("Location").child("lat").setValue(Double.valueOf(((Location)localObject).getLatitude()));
    localDatabaseReference.child("FindMyPhoneUsers").child(SharedData.PhoneNumber).child("Location").child("lag").setValue(Double.valueOf(((Location)localObject).getLongitude()));
    localDatabaseReference.child("FindMyPhoneUsers").child(SharedData.PhoneNumber).child("Location").child("LastDateOnline").setValue(localSimpleDateFormat.format(localDate).toString());
    localDatabaseReference.child("FindMyPhoneUsers").child(SharedData.PhoneNumber).child("Location").child("Battery").setValue(Double.valueOf(paramDouble));
  }
  
  public double getBatteryLevel()
  {
    Intent localIntent = this.context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    int i = localIntent.getIntExtra("level", -1);
    int j = localIntent.getIntExtra("scale", -1);
    if ((i == -1) || (j == -1)) {
      return 50.0D;
    }
    return i / j * 100.0D;
  }
  
  public String getCompleteAddressString(double paramDouble1, double paramDouble2)
  {
    Object localObject1 = "";
    Object localObject2 = new Geocoder(this.context, Locale.getDefault());
    try
    {
      localObject2 = ((Geocoder)localObject2).getFromLocation(paramDouble1, paramDouble2, 1);
      if (localObject2 != null)
      {
        localObject1 = (Address)((List)localObject2).get(0);
        localObject2 = new StringBuilder("");
        int i = 0;
        while (i < ((Address)localObject1).getMaxAddressLineIndex())
        {
          ((StringBuilder)localObject2).append(((Address)localObject1).getAddressLine(i)).append(" ");
          i += 1;
        }
        localObject1 = ((StringBuilder)localObject2).toString();
      }
      return (String)localObject1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
}


/* Location:              C:\apktool\dex2jar-2.0\findmy-dex2jar.jar!\phonelocation\example\asuss550c\phonelocation\ManagmentOperations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */