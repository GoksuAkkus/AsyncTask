package com.example.student.myapplication;


import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

        import org.json.JSONObject;

        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView sonuc;
    Spinner ilk,ikinci;
    EditText giris;
    String str1;
    String str2;

    String[] ParaBirimleri={"TRY","GBP","USD","EUR","SOS","UYU","UAH","PYG","QAR","ZMK"};

    Double ilkpara,ikincipara,cevirsonuc,girilen;


    Integer secili_ilk_sp,secili_ikinci_sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ilk=(Spinner)findViewById(R.id.sp_ilk);
        ikinci=(Spinner)findViewById(R.id.sp_iki);
        sonuc=(TextView)findViewById(R.id.textView2);
        giris=(EditText)findViewById(R.id.editText);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ParaBirimleri);

        ilk.setAdapter(adapter);
        ikinci.setAdapter(adapter);



        ilk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                secili_ilk_sp = i;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ikinci.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                secili_ikinci_sp = i;


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }



    public void Hesapla(View view) {




        try{

            DownloadRate downloadRate =new DownloadRate();

            String url = "http://data.fixer.io/api/latest?access_key=fce7e26c2fe3939b443e57ec1cbacbb2&format=1";
            downloadRate.execute(url);


        }
        catch (Exception ee)
        {
            ee.printStackTrace();
        }



    }

    private class DownloadRate extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String result="";
            URL url;
            HttpURLConnection httpURLConnection;

            try{
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();


                while (data > 0)
                {
                    char character = (char) data;
                    result +=character;
                    data =  inputStreamReader.read();
                }
                return result;

            }
            catch (Exception e)
            {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{

                JSONObject jsonObj = new JSONObject(s);


                String rates = jsonObj.getString("rates");
                JSONObject json2=new JSONObject(rates);

                ilkpara = json2.getDouble(ParaBirimleri[secili_ilk_sp]);
                ikincipara = json2.getDouble(ParaBirimleri[secili_ikinci_sp]);

                ilkpara= 1/ilkpara;
                girilen = Double.valueOf(giris.getText().toString());
                ilkpara = girilen*ilkpara;//settexteki değerle çarpıcaz
                cevirsonuc = ilkpara*ikincipara;
                sonuc.setText(cevirsonuc.toString());

                //ilkpara = json2.getDouble(str1);
                //ikincipara = json2.getDouble(str2);


                //Toast.makeText(MainActivity.this, str1, Toast.LENGTH_SHORT).show();


            }
            catch (Exception e)
            {

            }


        }
    }



}
