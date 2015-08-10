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
 * 1.�ҵ���Ҫ�Ŀؼ�
 * 2.�ó��û���������
 * 3.�ж��Ƿ�Ϊ��
 * 4.��������ס���룬����file����
 * 5��д���ĵ�
 * 
 * v2.0�����ļ���ʱ�򣬶����û��������룬��ʾ���������
 * 
 * v3.0���ļ��浽sd������
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
//		����ļ��д��ڣ��Ͷ��������������ʾ
		
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
//		���õ���¼�
		login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//			��sd����д�ļ�֮ǰ�����ж�sd����״̬��ʣ������
				if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					Toast.makeText(MainActivity.this,"���sd�����쳣", 0).show();
					return;
				}
//				�ж�sd�����������������С
				 long freeSpace = Environment.getExternalStorageDirectory().getFreeSpace();
				 String size = Formatter.formatFileSize(MainActivity.this, freeSpace);
				 Toast.makeText(MainActivity.this, "sd����ʣ��ռ���"+size, 0).show();
				 String name = et_name.getText().toString().trim();
				 String password = et_password.getText().toString().trim();
				if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
					Toast.makeText(MainActivity.this, "�û����������벻��Ϊ��", 0).show();
					return;
				}
				if (cb_check.isChecked()) {
					File file = new File(Environment.getExternalStorageDirectory(), "info.txt");
					String dirString = Environment.getExternalStorageDirectory().toString();
					Log.i("�洢��sd����Ŀ¼��",dirString);
					try {
						FileOutputStream fos = new FileOutputStream(file);
						fos.write((name+"##"+password).getBytes());
						fos.close();
						Toast.makeText(MainActivity.this, "�û������뱣��ɹ�", 0).show();
					} catch (Exception e) {
						e.printStackTrace();
						Toast.makeText(MainActivity.this, "�û������뱣��ʧ��", 0).show();
					}
				}else {
					Log.i(TAG,"����Ҫ��ס����");
				}
			}
		});
		
	}
}
