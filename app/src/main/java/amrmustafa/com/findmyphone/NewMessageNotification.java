package amrmustafa.com.findmyphone;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;

public class NewMessageNotification
{
  private static final String NOTIFICATION_TAG = "NewMessage";
  
  @TargetApi(5)
  public static void cancel(Context paramContext)
  {
    paramContext = (NotificationManager)paramContext.getSystemService("notification");
    if (Build.VERSION.SDK_INT >= 5)
    {
      paramContext.cancel("NewMessage", 0);
      return;
    }
    paramContext.cancel("NewMessage".hashCode());
  }
  
  public static String getContactName(Context paramContext, String paramString)
  {
    String str = paramString;
    Cursor localCursor = paramContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
    do
    {
      paramContext = str;
      if (!localCursor.moveToNext()) {
        break;
      }
      paramContext = localCursor.getString(localCursor.getColumnIndex("data1"));
    } while ((!paramString.contains(paramContext)) && (!paramContext.contains(paramString)));
    paramContext = localCursor.getString(localCursor.getColumnIndex("display_name"));
    return paramContext;
  }
  
  @TargetApi(5)
  private static void notify(Context paramContext, Notification paramNotification)
  {
    paramContext = (NotificationManager)paramContext.getSystemService("notification");
    if (Build.VERSION.SDK_INT >= 5)
    {
      paramContext.notify("NewMessage", 0, paramNotification);
      return;
    }
    paramContext.notify("NewMessage".hashCode(), paramNotification);
  }
  
  public static void notify(Context paramContext, String paramString1, String paramString2, String paramString3, int paramInt)
  {
    Bitmap localBitmap = BitmapFactory.decodeResource(paramContext.getResources(), 2130837619);
    String str = paramContext.getResources().getString(2131165285) + paramString1;
    Intent localIntent = new Intent(paramContext, MapDisplay.class);
    localIntent.putExtra("PhoneNumber", ManagmentOperations.FormatPhoneNumber(paramString3));
    localIntent.putExtra("Mode", "Offline");
    localIntent.addFlags(268435456);
    notify(paramContext, new NotificationCompat.Builder(paramContext).setDefaults(-1).setSmallIcon(2130837619).setContentTitle(str).setContentText(paramString2).setPriority(0).setLargeIcon(localBitmap).setTicker(paramString1).setNumber(paramInt).setContentIntent(PendingIntent.getActivity(paramContext, 0, localIntent, 134217728)).setStyle(new NotificationCompat.BigTextStyle().bigText(paramString2).setBigContentTitle(str).setSummaryText("--")).addAction(2130837625, " ", PendingIntent.getActivity(paramContext, 0, Intent.createChooser(new Intent("android.intent.action.SEND").setType("text/plain").putExtra("android.intent.extra.TEXT", paramString2), "Select"), 134217728)).setAutoCancel(true).build());
  }
}


/* Location:              C:\apktool\dex2jar-2.0\findmy-dex2jar.jar!\phonelocation\example\asuss550c\phonelocation\NewMessageNotification.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */