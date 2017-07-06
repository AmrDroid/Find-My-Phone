package amrmustafa.com.findmyphone;

public class ListItem
{
  public double BatteryLevel;
  public int ImageURL;
  public String LastDateOnline;
  public String LastLocation;
  public String PhoneNumber;
  public String UserName;
  
  public ListItem(String paramString1, String paramString2, int paramInt)
  {
    this.UserName = paramString1;
    this.PhoneNumber = paramString2;
    this.ImageURL = paramInt;
  }
  
  public ListItem(String paramString1, String paramString2, int paramInt, double paramDouble, String paramString3, String paramString4)
  {
    this.UserName = paramString1;
    this.PhoneNumber = paramString2;
    this.ImageURL = paramInt;
    this.BatteryLevel = paramDouble;
    this.LastLocation = paramString3;
    this.LastDateOnline = paramString4;
  }
}


/* Location:              C:\apktool\dex2jar-2.0\findmy-dex2jar.jar!\phonelocation\example\asuss550c\phonelocation\ListItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */