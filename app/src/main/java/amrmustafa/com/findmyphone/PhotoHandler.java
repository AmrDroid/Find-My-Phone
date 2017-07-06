package amrmustafa.com.findmyphone;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoHandler
  implements PictureCallback
{
  private final Context context;
  
  public PhotoHandler(Context paramContext)
  {
    this.context = paramContext;
  }
  
  private File getDir()
  {
    return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraAPIDemo");
  }
  
  public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera)
  {
    Object localObject = getDir();
    if ((!((File)localObject).exists()) && (!((File)localObject).mkdirs()))
    {
      Toast.makeText(this.context, "Can't create directory to save image.", 1).show();
      return;
    }
    paramCamera = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
    paramCamera = "Picture_" + paramCamera + ".jpg";
    localObject = new File(((File)localObject).getPath() + File.separator + paramCamera);
    try
    {
      localObject = new FileOutputStream((File)localObject);
      ((FileOutputStream)localObject).write(paramArrayOfByte);
      ((FileOutputStream)localObject).close();
      Toast.makeText(this.context, "New Image saved:" + paramCamera, 1).show();
      return;
    }
    catch (Exception paramArrayOfByte)
    {
      Toast.makeText(this.context, "Image could not be saved.", 1).show();
    }
  }
}


/* Location:              C:\apktool\dex2jar-2.0\findmy-dex2jar.jar!\phonelocation\example\asuss550c\phonelocation\PhotoHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */