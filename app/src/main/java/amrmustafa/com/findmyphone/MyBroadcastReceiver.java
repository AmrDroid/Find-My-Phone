package amrmustafa.com.findmyphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;

public class MyBroadcastReceiver
  extends BroadcastReceiver
{
  static int NotifyID = 1;
  static String UserPhoneNumber = "";
  InterstitialAd mInterstitialAd;
  
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
  
  private void LoadAdmob(Context paramContext)
  {
    try
    {
      this.mInterstitialAd = new InterstitialAd(paramContext);
      this.mInterstitialAd.setAdUnitId(paramContext.getResources().getString(2131165257));
      paramContext = new AdRequest.Builder().build();
      this.mInterstitialAd.loadAd(paramContext);
      this.mInterstitialAd.setAdListener(new AdListener()
      {
        public void onAdClosed() {}
        
        public void onAdLoaded()
        {
          MyBroadcastReceiver.this.DisplayAdmob();
        }
      });
      return;
    }
    catch (Exception paramContext) {}
  }
  
  /* Error */
  public void onReceive(Context paramContext, android.content.Intent paramIntent)
  {
    // Byte code:
    //   0: aload_2
    //   1: invokevirtual 88	android/content/Intent:getAction	()Ljava/lang/String;
    //   4: astore 4
    //   6: aload 4
    //   8: ldc 90
    //   10: invokevirtual 96	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   13: ifeq +229 -> 242
    //   16: aload_1
    //   17: new 84	android/content/Intent
    //   20: dup
    //   21: aload_1
    //   22: ldc 98
    //   24: invokespecial 101	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
    //   27: invokevirtual 105	android/content/Context:startService	(Landroid/content/Intent;)Landroid/content/ComponentName;
    //   30: pop
    //   31: aload 4
    //   33: ldc 107
    //   35: invokevirtual 96	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   38: ifeq +244 -> 282
    //   41: aload_2
    //   42: invokevirtual 111	android/content/Intent:getExtras	()Landroid/os/Bundle;
    //   45: astore 5
    //   47: aload 5
    //   49: ifnull +233 -> 282
    //   52: aload 5
    //   54: ldc 113
    //   56: invokevirtual 119	android/os/Bundle:get	(Ljava/lang/String;)Ljava/lang/Object;
    //   59: checkcast 121	[Ljava/lang/Object;
    //   62: checkcast 121	[Ljava/lang/Object;
    //   65: astore 6
    //   67: aload 6
    //   69: arraylength
    //   70: anewarray 123	android/telephony/SmsMessage
    //   73: astore 7
    //   75: iconst_0
    //   76: istore_3
    //   77: iload_3
    //   78: aload 7
    //   80: arraylength
    //   81: if_icmpge +201 -> 282
    //   84: getstatic 128	android/os/Build$VERSION:SDK_INT	I
    //   87: bipush 23
    //   89: if_icmplt +194 -> 283
    //   92: aload 5
    //   94: ldc -126
    //   96: invokevirtual 133	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   99: astore_2
    //   100: aload 7
    //   102: iload_3
    //   103: aload 6
    //   105: iload_3
    //   106: aaload
    //   107: checkcast 135	[B
    //   110: checkcast 135	[B
    //   113: aload_2
    //   114: invokestatic 139	android/telephony/SmsMessage:createFromPdu	([BLjava/lang/String;)Landroid/telephony/SmsMessage;
    //   117: aastore
    //   118: aload 7
    //   120: iload_3
    //   121: aaload
    //   122: invokevirtual 142	android/telephony/SmsMessage:getOriginatingAddress	()Ljava/lang/String;
    //   125: astore 4
    //   127: aload 7
    //   129: iload_3
    //   130: aaload
    //   131: invokevirtual 145	android/telephony/SmsMessage:getMessageBody	()Ljava/lang/String;
    //   134: astore_2
    //   135: new 147	phonelocation/example/asuss550c/phonelocation/ManagmentOperations
    //   138: dup
    //   139: aload_1
    //   140: invokespecial 148	phonelocation/example/asuss550c/phonelocation/ManagmentOperations:<init>	(Landroid/content/Context;)V
    //   143: astore 8
    //   145: aload 8
    //   147: aload 4
    //   149: getstatic 151	phonelocation/example/asuss550c/phonelocation/SettingSaved:UserPhoneNumber	Ljava/lang/String;
    //   152: invokevirtual 155	phonelocation/example/asuss550c/phonelocation/ManagmentOperations:IsPhoneAut	(Ljava/lang/String;Ljava/lang/String;)Z
    //   155: iconst_1
    //   156: if_icmpne +147 -> 303
    //   159: aload_2
    //   160: ldc -99
    //   162: invokevirtual 161	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   165: ifeq +138 -> 303
    //   168: getstatic 167	phonelocation/example/asuss550c/phonelocation/ServiceNotification:ServiceIsRun	Z
    //   171: ifne +64 -> 235
    //   174: aload 8
    //   176: aload 4
    //   178: invokevirtual 170	phonelocation/example/asuss550c/phonelocation/ManagmentOperations:NumberToName	(Ljava/lang/String;)Ljava/lang/String;
    //   181: astore_2
    //   182: aload_1
    //   183: new 172	java/lang/StringBuilder
    //   186: dup
    //   187: invokespecial 173	java/lang/StringBuilder:<init>	()V
    //   190: aload_2
    //   191: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   194: ldc -77
    //   196: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   199: invokevirtual 182	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   202: iconst_1
    //   203: invokestatic 188	android/widget/Toast:makeText	(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;
    //   206: invokevirtual 189	android/widget/Toast:show	()V
    //   209: new 84	android/content/Intent
    //   212: dup
    //   213: aload_1
    //   214: ldc -93
    //   216: invokespecial 101	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
    //   219: astore_2
    //   220: aload_2
    //   221: ldc -65
    //   223: aload 4
    //   225: invokevirtual 195	android/content/Intent:putExtra	(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   228: pop
    //   229: aload_1
    //   230: aload_2
    //   231: invokevirtual 105	android/content/Context:startService	(Landroid/content/Intent;)Landroid/content/ComponentName;
    //   234: pop
    //   235: iload_3
    //   236: iconst_1
    //   237: iadd
    //   238: istore_3
    //   239: goto -162 -> 77
    //   242: aload_2
    //   243: invokevirtual 88	android/content/Intent:getAction	()Ljava/lang/String;
    //   246: ldc -59
    //   248: invokevirtual 96	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   251: ifne +15 -> 266
    //   254: aload_2
    //   255: invokevirtual 88	android/content/Intent:getAction	()Ljava/lang/String;
    //   258: ldc -57
    //   260: invokevirtual 96	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   263: ifeq -232 -> 31
    //   266: new 147	phonelocation/example/asuss550c/phonelocation/ManagmentOperations
    //   269: dup
    //   270: aload_1
    //   271: invokespecial 148	phonelocation/example/asuss550c/phonelocation/ManagmentOperations:<init>	(Landroid/content/Context;)V
    //   274: dconst_0
    //   275: invokevirtual 203	phonelocation/example/asuss550c/phonelocation/ManagmentOperations:SendGPS	(D)V
    //   278: goto -247 -> 31
    //   281: astore_1
    //   282: return
    //   283: aload 7
    //   285: iload_3
    //   286: aload 6
    //   288: iload_3
    //   289: aaload
    //   290: checkcast 135	[B
    //   293: checkcast 135	[B
    //   296: invokestatic 206	android/telephony/SmsMessage:createFromPdu	([B)Landroid/telephony/SmsMessage;
    //   299: aastore
    //   300: goto -182 -> 118
    //   303: aload_2
    //   304: getstatic 209	phonelocation/example/asuss550c/phonelocation/SettingSaved:HashCode	Ljava/lang/String;
    //   307: invokevirtual 213	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   310: ifeq +45 -> 355
    //   313: getstatic 209	phonelocation/example/asuss550c/phonelocation/SettingSaved:HashCode	Ljava/lang/String;
    //   316: invokevirtual 217	java/lang/String:length	()I
    //   319: ifle +36 -> 355
    //   322: ldc 18
    //   324: putstatic 209	phonelocation/example/asuss550c/phonelocation/SettingSaved:HashCode	Ljava/lang/String;
    //   327: aload 4
    //   329: astore_2
    //   330: aload 4
    //   332: invokevirtual 217	java/lang/String:length	()I
    //   335: getstatic 220	phonelocation/example/asuss550c/phonelocation/SettingSaved:NumberDigit	I
    //   338: if_icmpge +7 -> 345
    //   341: getstatic 20	phonelocation/example/asuss550c/phonelocation/MyBroadcastReceiver:UserPhoneNumber	Ljava/lang/String;
    //   344: astore_2
    //   345: aload_2
    //   346: invokestatic 223	phonelocation/example/asuss550c/phonelocation/ManagmentOperations:FormatPhoneNumber	(Ljava/lang/String;)Ljava/lang/String;
    //   349: putstatic 228	phonelocation/example/asuss550c/phonelocation/SharedData:PhoneNumber	Ljava/lang/String;
    //   352: goto -117 -> 235
    //   355: aload_2
    //   356: invokevirtual 217	java/lang/String:length	()I
    //   359: iconst_2
    //   360: if_icmple -125 -> 235
    //   363: aload_2
    //   364: iconst_0
    //   365: invokevirtual 232	java/lang/String:charAt	(I)C
    //   368: bipush 37
    //   370: if_icmpne -135 -> 235
    //   373: aload_2
    //   374: iconst_1
    //   375: aload_2
    //   376: invokevirtual 217	java/lang/String:length	()I
    //   379: iconst_1
    //   380: isub
    //   381: invokevirtual 236	java/lang/String:substring	(II)Ljava/lang/String;
    //   384: astore 9
    //   386: aload 9
    //   388: ldc -18
    //   390: invokevirtual 242	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   393: arraylength
    //   394: iconst_2
    //   395: if_icmpne -160 -> 235
    //   398: new 172	java/lang/StringBuilder
    //   401: dup
    //   402: invokespecial 173	java/lang/StringBuilder:<init>	()V
    //   405: aload 9
    //   407: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   410: ldc -18
    //   412: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   415: aload 4
    //   417: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   420: invokevirtual 182	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   423: putstatic 245	phonelocation/example/asuss550c/phonelocation/SettingSaved:Userlocationinthemap	Ljava/lang/String;
    //   426: aload_1
    //   427: aload_1
    //   428: invokevirtual 59	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   431: ldc -10
    //   433: invokevirtual 66	android/content/res/Resources:getString	(I)Ljava/lang/String;
    //   436: iconst_1
    //   437: invokestatic 188	android/widget/Toast:makeText	(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;
    //   440: invokevirtual 189	android/widget/Toast:show	()V
    //   443: new 248	phonelocation/example/asuss550c/phonelocation/NewMessageNotification
    //   446: dup
    //   447: invokespecial 249	phonelocation/example/asuss550c/phonelocation/NewMessageNotification:<init>	()V
    //   450: pop
    //   451: aload_1
    //   452: aload 8
    //   454: aload 4
    //   456: invokevirtual 170	phonelocation/example/asuss550c/phonelocation/ManagmentOperations:NumberToName	(Ljava/lang/String;)Ljava/lang/String;
    //   459: aload_2
    //   460: aload 4
    //   462: getstatic 16	phonelocation/example/asuss550c/phonelocation/MyBroadcastReceiver:NotifyID	I
    //   465: invokestatic 253	phonelocation/example/asuss550c/phonelocation/NewMessageNotification:notify	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V
    //   468: getstatic 16	phonelocation/example/asuss550c/phonelocation/MyBroadcastReceiver:NotifyID	I
    //   471: iconst_1
    //   472: iadd
    //   473: putstatic 16	phonelocation/example/asuss550c/phonelocation/MyBroadcastReceiver:NotifyID	I
    //   476: goto -241 -> 235
    //   479: astore_2
    //   480: aload_2
    //   481: invokevirtual 256	java/lang/Exception:printStackTrace	()V
    //   484: goto -249 -> 235
    //   487: astore_1
    //   488: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	489	0	this	MyBroadcastReceiver
    //   0	489	1	paramContext	Context
    //   0	489	2	paramIntent	android.content.Intent
    //   76	213	3	i	int
    //   4	457	4	str1	String
    //   45	48	5	localBundle	android.os.Bundle
    //   65	222	6	arrayOfObject	Object[]
    //   73	211	7	arrayOfSmsMessage	android.telephony.SmsMessage[]
    //   143	310	8	localManagmentOperations	ManagmentOperations
    //   384	22	9	str2	String
    // Exception table:
    //   from	to	target	type
    //   0	31	281	java/lang/Exception
    //   31	47	281	java/lang/Exception
    //   242	266	281	java/lang/Exception
    //   266	278	281	java/lang/Exception
    //   443	476	479	java/lang/Exception
    //   52	75	487	java/lang/Exception
    //   77	118	487	java/lang/Exception
    //   118	235	487	java/lang/Exception
    //   283	300	487	java/lang/Exception
    //   303	327	487	java/lang/Exception
    //   330	345	487	java/lang/Exception
    //   345	352	487	java/lang/Exception
    //   355	443	487	java/lang/Exception
    //   480	484	487	java/lang/Exception
  }
}


/* Location:              C:\apktool\dex2jar-2.0\findmy-dex2jar.jar!\phonelocation\example\asuss550c\phonelocation\MyBroadcastReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */