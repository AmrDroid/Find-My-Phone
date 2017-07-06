package amrmustafa.com.findmyphone;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.RemoteMessage.Notification;
import java.util.Map;

public class MyFirebaseMessagingService
  extends FirebaseMessagingService
{
  private static final String TAG = "MyFirebaseMsgService";
  
  private void sendNotification(String paramString)
  {
    Object localObject2 = paramString.split("%");
    Object localObject1;
    if (localObject2.length == 2)
    {
      localObject1 = new Intent("android.intent.action.VIEW");
      paramString = localObject2[0];
      ((Intent)localObject1).setData(Uri.parse("market://details?id=" + localObject2[1]));
      ((Intent)localObject1).setData(Uri.parse("https://play.google.com/store/apps/details?id=" + localObject2[1]));
    }
    for (;;)
    {
      ((Intent)localObject1).addFlags(67108864);
      localObject1 = PendingIntent.getActivity(this, 0, (Intent)localObject1, 1073741824);
      localObject2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837619);
      Uri localUri = RingtoneManager.getDefaultUri(2);
      paramString = new NotificationCompat.Builder(this).setSmallIcon(2130837619).setLargeIcon((Bitmap)localObject2).setContentTitle("FamilyIn Message").setContentText(paramString).setAutoCancel(true).setSound(localUri).setContentIntent((PendingIntent)localObject1);
      ((NotificationManager)getSystemService("notification")).notify(0, paramString.build());
      return;
      localObject1 = new Intent(this, MainActivity.class);
    }
  }
  
  public void onMessageReceived(RemoteMessage paramRemoteMessage)
  {
    Log.d("MyFirebaseMsgService", "From: " + paramRemoteMessage.getFrom());
    if (paramRemoteMessage.getData().size() > 0) {
      Log.d("MyFirebaseMsgService", "Message data payload: " + paramRemoteMessage.getData());
    }
    if (paramRemoteMessage.getNotification() != null) {
      Log.d("MyFirebaseMsgService", "Message Notification Body: " + paramRemoteMessage.getNotification().getBody());
    }
    sendNotification(paramRemoteMessage.getNotification().getBody());
  }
}


/* Location:              C:\apktool\dex2jar-2.0\findmy-dex2jar.jar!\phonelocation\example\asuss550c\phonelocation\MyFirebaseMessagingService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */