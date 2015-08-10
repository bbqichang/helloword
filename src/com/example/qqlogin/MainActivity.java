package com.example.qqlogin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.Format;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 1.找到需要的控件
 * 2.拿出用户名，密码
 * 3.判断是否为空
 * 4.如果点击记住密码，建立file对象
 * 5。写入文档
 * 
 * v2.0当有文件的时候，读出用户名和密码，显示在输入框中
 * 
 * v3.0把文件存到sd卡上面
 * @author qichang
 *
 */
public class MainActivity extends Activity {
	protected static final String TAG = "MainActivity";
	private EditText et_name;
	private EditText et_password;
	private Button login;
	private CheckBox cb_check;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cb_check = (CheckBox) findViewById(R.id.cb_check);
		et_name = (EditText) findViewById(R.id.et_name);
		et_password = (EditText) findViewById(R.id.et_password);
		login = (Button) findViewById(R.id.login);
//		如果文件夹存在，就读出里面的内容显示
		
		try {
			File file = new File(Environment.getExternalStorageDirectory(), "info.txt");
			if (file.exists() || file.length() > 0) {
//				FileInputStream fis = new FileInputStream(file);
//				byte[] bys = new byte[1024];
//				int len = fis.read(bys);
//				String info = new String(bys,0,len);
				FileInputStream fis = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String info = br.readLine();
				String name = info.split("##")[0];
				String password = info.split("##")[1];
			et_name.setText(name);
				et_password.setText(password);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
//		设置点击事件
		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//			在sd卡中写文件之前，先判断sd卡的状态和剩余容量
				if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					Toast.makeText(MainActivity.this,"你的sd卡有异常", 0).show();
					return;
				}
//				判断sd卡的容量满不满足大小
				 long freeSpace = Environment.getExternalStorageDirectory().getFreeSpace();
				 String size = Formatter.formatFileSize(MainActivity.this, freeSpace);
				 Toast.makeText(MainActivity.this, "sd卡的剩余空间是"+size, 0).show();
				 String name = et_name.getText().toString().trim();
				 String password = et_password.getText().toString().trim();
				if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
					Toast.makeText(MainActivity.this, "用户名或者密码不能为空", 0).show();
					return;
				}
				if (cb_check.isChecked()) {
					File file = new File(Environment.getExternalStorageDirectory(), "info.txt");
					String dirString = Environment.getExternalStorageDirectory().toString();
					Log.i("存储的sd卡的目录是",dirString);
					try {
						FileOutputStream fos = new FileOutputStream(file);
						fos.write((name+"##"+password).getBytes());
						fos.close();
						Toast.makeText(MainActivity.this, "用户名密码保存成功", 0).show();
					} catch (Exception e) {
						e.printStackTrace();
						Toast.makeText(MainActivity.this, "用户名密码保存失败", 0).show();
					}
				}else {
					Log.i(TAG,"不需要记住密码");
				}
			}
		});
		
	}
}
