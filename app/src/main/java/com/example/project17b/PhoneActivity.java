package com.example.project17b;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class PhoneActivity extends AppCompatActivity {
    private DBHelper dbOpenHelper;
    private ArrayList<Person> pcs= new ArrayList<>();
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonelistview);


        Uri uri_user = Uri.parse("content://cn.scu.myprovider/contacts/2");
        ContentResolver resolver =  getContentResolver();
        Cursor cursor = resolver.query(uri_user, new String[]{"name","phone","sex"}, null, null, null);

        //利用游标遍历所有数据对象
        while(cursor.moveToNext()){
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range")  String phone  = cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String sex = cursor.getString(cursor.getColumnIndex("sex"));
            Person person=new Person(name,phone ,sex);
            System.out.println(person);
            pcs.add(person);
        }
        // 关闭游标，释放资源
        cursor.close();

        PersonAdapter personAdapter=new PersonAdapter(PhoneActivity.this,R.layout.phone_item,pcs);
        ListView listView=(ListView) findViewById(R.id.list_view1);
        listView.setAdapter(personAdapter);

       /* ArrayList<HashMap<String, String>> readContact = readContact();

        listView=(ListView) findViewById(R.id.list_view1);
        listView.setAdapter(new SimpleAdapter(PhoneActivity.this, readContact,
                R.layout.phone_item, new String[] { "name", "phone" },
                new int[] { R.id.phone_name, R.id.phone_phone }));*/

    }
    private ArrayList<HashMap<String, String>> readContact() {
        // 首先,从raw_contacts中读取联系人的id("contact_id")
        // 其次, 根据contact_id从data表中查询出相应的电话号码和联系人名称
        // 然后,根据mimetype来区分哪个是联系人,哪个是电话号码
        Uri rawContactsUri = Uri
                .parse("content://com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content://com.android.contacts/data");

        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        // 从raw_contacts中读取联系人的id("contact_id")
        Cursor rawContactsCursor = getContentResolver().query(rawContactsUri,
                new String[] { "contact_id" }, null, null, null);
        if (rawContactsCursor != null) {
            while (rawContactsCursor.moveToNext()) {
                String contactId = rawContactsCursor.getString(0);
                System.out.println(contactId);

                // 根据contact_id从data表中查询出相应的电话号码和联系人名称, 实际上查询的是视图view_data
                Cursor dataCursor = getContentResolver().query(dataUri,
                        new String[] { "data1", "mimetype" }, "contact_id=?",
                        new String[] { contactId }, null);

                if (dataCursor != null) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    while (dataCursor.moveToNext()) {
                        String data1 = dataCursor.getString(0);
                        String mimetype = dataCursor.getString(1);
                        System.out.println(contactId + ";" + data1 + ";"
                                + mimetype);
                        if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                            map.put("phone", data1);
                        } else if ("vnd.android.cursor.item/name"
                                .equals(mimetype)) {
                            map.put("name", data1);
                        }
                    }

                    list.add(map);
                    dataCursor.close();
                }
            }

            rawContactsCursor.close();
        }

        return list;
    }



}