package amrmustafa.com.findmyphone;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ContactList
  extends AppCompatActivity
{
  static final int PICK_CONTACT = 1;
  private String Message = "";
  String PersonNumber = "No number";
  private String Phone = "";
  private final int REQUEST_CODE_ASK_PERMISSIONS = 123;
  private final int REQUEST_CODE_ASK_PERMISSIONSSMS = 111;
  String SearchText = "%";
  private VivzAdapter adpter;
  String cNumber = "No number";
  public ArrayList<ListItem> list;
  ListView listv;
  private DatabaseReference mDatabase;
  SearchView searchView;
  
  private void SaveData()
  {
    new SettingSaved(this).SaveData();
  }
  
  @TargetApi(Build.VERSION_CODES.M)
  void CheckPermision()
  {

    if ((Build.VERSION.SDK_INT >= 23) && (ActivityCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") != 0))
    {
      if (!ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_CONTACTS")) {
        requestPermissions(new String[] { "android.permission.READ_CONTACTS" }, 123);
      }
      return;
    }
    PickContact();
  }
  
  void MsgSent()
  {
    try
    {
      SmsManager localSmsManager = SmsManager.getDefault();
      Iterator localIterator = SettingSaved.WhoFindMeIN.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Entry localEntry = (Entry)localIterator.next();
        localSmsManager.sendTextMessage(this.Phone, null, this.Message, null, null);
      }
      return;
    }
    catch (Exception localException) {}
  }
  
  void PickContact()
  {
    startActivityForResult(new Intent("android.intent.action.PICK", ContactsContract.Contacts.CONTENT_URI), 1);
  }
  void ShowAlert(String paramString)
  {
    Builder localBuilder = new Builder(this);
    localBuilder.setMessage(paramString).setCancelable(false).setNegativeButton(getResources().getString(2131165274), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
    });
    localBuilder.create().show();
  }
  
  void ShowMessageWillSend(String paramString)
  {
    try
    {
      if (this.PersonNumber.equalsIgnoreCase(paramString)) {
        return;
      }
      this.PersonNumber = paramString;
      paramString = new Builder(this);
      paramString.setMessage(getResources().getString(2131165290)).setCancelable(false).setPositiveButton(getResources().getString(2131165274), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          try
          {
            paramAnonymousDialogInterface = ContactList.this.getResources().getString(2131165288) + " https://goo.gl/re4sxX";
            Intent localIntent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + ContactList.this.PersonNumber));
            localIntent.putExtra("sms_body", paramAnonymousDialogInterface);
            ContactList.this.startActivity(localIntent);
            return;
          }
          catch (Exception paramAnonymousDialogInterface) {}
        }
      }).setNegativeButton(getResources().getString(2131165253), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
      });
      paramString.create().show();
      return;
    }
    catch (Exception paramString) {}
  }
  
  void UpdetListContact()
  {
    try
    {
      this.list.clear();
      Iterator localIterator = SettingSaved.WhoFindMeIN.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Entry localEntry = (Entry)localIterator.next();
        this.list.add(new ListItem(localEntry.getValue().toString(), localEntry.getKey().toString(), 2130837621));
        continue;
        this.adpter.notifyDataSetChanged();
      }
    }
    catch (Exception localException) {}
    for (;;)
    {
      return;
      if (this.list.size() > 0) {
        this.list.add(new ListItem("Ads", "Ads", 2130837619));
      }
      if (this.list.size() == 0) {
        this.list.add(new ListItem("NoTicket", "no_desc", 2130837619));
      }
      this.adpter.notifyDataSetChanged();
    }
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    switch (paramInt1)
    {
    }
    do
    {
      do
      {
        return;
      } while (paramInt2 != -1);
      paramIntent = paramIntent.getData();
      paramIntent = getContentResolver().query(paramIntent, null, null, null, null);
    } while (!paramIntent.moveToFirst());
    Object localObject = paramIntent.getString(paramIntent.getColumnIndexOrThrow("_id"));
    String str = paramIntent.getString(paramIntent.getColumnIndex("has_phone_number"));
    this.cNumber = "No number";
    if (str.equalsIgnoreCase("1"))
    {
      localObject = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, "contact_id = " + (String)localObject, null, null);
      ((Cursor)localObject).moveToFirst();
      this.cNumber = ManagmentOperations.FormatPhoneNumber(((Cursor)localObject).getString(((Cursor)localObject).getColumnIndex("data1")));
      System.out.println("number is:" + this.cNumber);
    }
    paramIntent = paramIntent.getString(paramIntent.getColumnIndex("display_name"));
    if (this.cNumber.length() < SettingSaved.NumberDigit)
    {
      ShowAlert(getResources().getString(2131165255));
      return;
    }
    SettingSaved.WhoFindMeIN.put(this.cNumber, paramIntent);
    this.mDatabase.child("FindMyPhoneUsers").child(this.cNumber).child("Finders").child(SharedData.PhoneNumber).setValue(Boolean.valueOf(true));
    try
    {
      this.mDatabase.child("FindMyPhoneUsers").child(this.cNumber).child("Updates").addValueEventListener(new ValueEventListener()
      {
        public void onCancelled(DatabaseError paramAnonymousDatabaseError) {}
        
        public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
        {
          if ((String)paramAnonymousDataSnapshot.getValue(String.class) == null)
          {
            ContactList.this.ShowMessageWillSend(ContactList.this.cNumber);
            ContactList.this.mDatabase.removeEventListener(this);
          }
        }
      });
      SaveData();
      UpdetListContact();
      return;
    }
    catch (Exception paramIntent)
    {
      for (;;)
      {
        ShowMessageWillSend(this.cNumber);
      }
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903067);
    this.mDatabase = FirebaseDatabase.getInstance().getReference();
    this.list = new ArrayList();
    this.listv = ((ListView)findViewById(2131624063));
    this.adpter = new VivzAdapter(this, this.list);
    this.listv.setAdapter(this.adpter);
    ((FloatingActionButton)findViewById(2131624062)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ContactList.this.CheckPermision();
      }
    });
    this.listv.setOnItemClickListener(new OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        paramAnonymousAdapterView = (ListItem)ContactList.this.list.get(paramAnonymousInt);
        if (paramAnonymousAdapterView.UserName.equals("NoTicket")) {
          return;
        }
        SettingSaved.WhoFindMeIN.remove(paramAnonymousAdapterView.PhoneNumber);
        ContactList.this.mDatabase.child("FindMyPhoneUsers").child(paramAnonymousAdapterView.PhoneNumber).child("Finders").child(SharedData.PhoneNumber).removeValue();
        ContactList.this.SaveData();
        ContactList.this.UpdetListContact();
      }
    });
    UpdetListContact();
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131689473, paramMenu);
    return true;
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4) {
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    int i = paramMenuItem.getItemId();
    if (i == 2131624129)
    {
      SaveData();
      finish();
    }
    if (i == 2131623967) {
      CheckPermision();
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    switch (paramInt)
    {
    default: 
      super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfInt);
      return;
    case 123: 
      if (paramArrayOfInt[0] == 0)
      {
        PickContact();
        return;
      }
      Toast.makeText(this, getResources().getString(2131165236), 0).show();
      return;
    }
    if (paramArrayOfInt[0] == 0)
    {
      MsgSent();
      return;
    }
    Toast.makeText(this, getResources().getString(2131165236), 0).show();
  }
  
  class VivzAdapter
    extends BaseAdapter
  {
    Context context;
    ArrayList<ListItem> list;
    
    VivzAdapter(ArrayList<ListItem> paramArrayList)
    {
      this.context = paramArrayList;
      ArrayList localArrayList;
      this.list = localArrayList;
    }
    
    public int getCount()
    {
      return this.list.size();
    }
    
    public Object getItem(int paramInt)
    {
      return this.list.get(paramInt);
    }
    
    public long getItemId(int paramInt)
    {
      return paramInt;
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      ListItem localListItem = (ListItem)this.list.get(paramInt);
      paramView = (LayoutInflater)this.context.getSystemService("layout_inflater");
      if (localListItem.UserName.equals("NoTicket"))
      {
        paramView = paramView.inflate(2130903090, paramViewGroup, false);
        ((TextView)paramView.findViewById(2131624092)).setText(ContactList.this.getResources().getString(2131165248));
        return paramView;
      }
      if (localListItem.UserName.equals("Ads"))
      {
        paramView = paramView.inflate(2130903088, paramViewGroup, false);
        ((NativeExpressAdView)paramView.findViewById(2131624079)).loadAd(new AdRequest.Builder().build());
        return paramView;
      }
      if (localListItem.ImageURL == 2130837621) {}
      for (paramView = paramView.inflate(2130903109, paramViewGroup, false);; paramView = paramView.inflate(2130903110, paramViewGroup, false))
      {
        paramViewGroup = (TextView)paramView.findViewById(2131624072);
        TextView localTextView = (TextView)paramView.findViewById(2131624117);
        ImageView localImageView = (ImageView)paramView.findViewById(2131624116);
        paramViewGroup.setText(localListItem.UserName);
        localTextView.setText(localListItem.PhoneNumber);
        localImageView.setImageResource(localListItem.ImageURL);
        return paramView;
      }
    }
  }
}
