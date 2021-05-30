package com.example.horseriding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    public static List<User> users=new ArrayList<>();

    private SharedPreferences sharedpreferences;


     @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);

        // Inserting Contacts
       /* Log.d("Insert: ", "Inserting ..");
        db.addClient(new Client(10, "ayoub", "nemmassi", "default.jpg", "AE132456", "ayoub.nemmassi@gmail.com", "00000", "0623350687", "nothing"));
        db.addUser(new User(7, "othman.froukh@gmail.com", "0000", "othman", "froukh", "nothing", "admin", "default.jpg", "0645879878"));


        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Client> clients = db.getAllContacts();

        for (Client cn : clients) {
            String log = "Id: " + cn.getClientId() + " ,Name: " + cn.getfName() + " ,Phone: " +
                    cn.getClientPhone();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }*/
        //test
    }

    @Override
    protected void onResume() {
        super.onResume();

    users=getAllUsers();
        //login();
        //Log.d("shared", sharedpreferences.getString("nomUtilisateur", null));
       // getAllUsers();
// ...

       /* // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.1.107:45455/users";
=======
        final TextView textView = (TextView) findViewById(R.id.text);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.1.4:45455/users";
>>>>>>> 1c93de48fed8a91f0821187c001899a13cc58aec

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: "+ response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);*/


    }

    void getUser(String id) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, WS.URL + "users"+"/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(MainActivity.class.getSimpleName(), response.toString());
                TextView txtNom = findViewById(R.id.txtNom);
                TextView txtPrenom = findViewById(R.id.txtprenom);
                TextView txtMail = findViewById(R.id.userEmail);
                TextView txtPasswd = findViewById(R.id.userPasswd);
                TextView textView = (TextView) findViewById(R.id.text);
                try {
                    User user = new User(response.getInt("userId"), response.getString("userEmail"),
                            response.getString("userPasswd"), response.getString("userFname"),
                            response.getString("userLname"), response.getString("description"),
                            response.getString("userType"), response.getString("userphoto"), response.getString("userPhone"),response.getString("lastLoginTime"),response.getString("displayColor"));
                  /*  if(response.has("seances"))
                    {
                        JSONArray ja= response.getJSONArray("seances");
                        for (int i=0;i<ja.length();i++){JSONObject j=ja.getJSONObject(i);
                            user.addSeance(new Seance(j.getString("label"),j.getDouble("score")));
                        }
                    }*/
                   /* MySingleton.getInstance(getApplicationContext()).getImageLoader().get(HTTP_BASE + HTTP_IMAGES + response.getString("photo"), new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            etd.setPhoto(response.getBitmap());
                            ImageView img=findViewById(R.id.imgProfile);
                            img.setImageBitmap(etd.getPhoto());
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });*/
                    Integer i = user.getUserId();
                    txtNom.setText(user.getUserFname());
                    txtPrenom.setText(user.getUserLname());
                    txtMail.setText(user.getUserEmail());
                    txtPasswd.setText(user.getUserPasswd());
                    textView.setText(i.toString());

                  /*  if(etd.getNotes()!=null)
                    {

                        NoteAdapter na=new NoteAdapter(MainActivity.this,etd.getNotes());
                        listNotes.setAdapter(na);

                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(MainActivity.class.getSimpleName(), error.getMessage());
                    }
                }

        );
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
    }
    void getClient(String id) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, WS.URL + "clients"+"/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(MainActivity.class.getSimpleName(), response.toString());
                TextView txtNom = findViewById(R.id.txtNom);
                TextView txtPrenom = findViewById(R.id.txtprenom);
                TextView txtMail = findViewById(R.id.userEmail);
                TextView txtPasswd = findViewById(R.id.userPasswd);
                TextView textView = (TextView) findViewById(R.id.text);
                try {
                    Client client = new Client(response.getInt("clientId"), response.getString("fName"),
                            response.getString("lName"), response.getString("photo"),
                            response.getString("identityDoc"), response.getString("clientEmail"),
                            response.getString("passwd"), response.getString("clientPhone"), response.getString("notes"));
                  /*  if(response.has("seances"))
                    {
                        JSONArray ja= response.getJSONArray("seances");
                        for (int i=0;i<ja.length();i++){JSONObject j=ja.getJSONObject(i);
                            user.addSeance(new Seance(j.getString("label"),j.getDouble("score")));
                        }
                    }*/
                   /* MySingleton.getInstance(getApplicationContext()).getImageLoader().get(HTTP_BASE + HTTP_IMAGES + response.getString("photo"), new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            etd.setPhoto(response.getBitmap());
                            ImageView img=findViewById(R.id.imgProfile);
                            img.setImageBitmap(etd.getPhoto());
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });*/
                    Integer i = client.getClientId();
                    txtNom.setText(client.getfName());
                    txtPrenom.setText(client.getlName());
                    txtMail.setText(client.getClientEmail());
                    txtPasswd.setText(client.getPasswd());
                    textView.setText(i.toString());

                  /*  if(etd.getNotes()!=null)
                    {

                        NoteAdapter na=new NoteAdapter(MainActivity.this,etd.getNotes());
                        listNotes.setAdapter(na);

                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(MainActivity.class.getSimpleName(), error.getMessage());
                    }
                }

        );
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
    }
    void getTask(String id) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, WS.URL + "tasks"+"/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(MainActivity.class.getSimpleName(), response.toString());
                TextView txtNom = findViewById(R.id.txtNom);
                TextView txtPrenom = findViewById(R.id.txtprenom);
                TextView txtMail = findViewById(R.id.userEmail);
                TextView txtPasswd = findViewById(R.id.userPasswd);
                TextView textView = (TextView) findViewById(R.id.text);
                try {
                    Task task =new Task(response.getInt("taskId"),response.getInt("durationMinut"),
                            response.getInt("userFk"),response.getString("title"),response.getString("detail"));
                  /*  if(response.has("seances"))
                    {
                        JSONArray ja= response.getJSONArray("seances");
                        for (int i=0;i<ja.length();i++){JSONObject j=ja.getJSONObject(i);
                            user.addSeance(new Seance(j.getString("label"),j.getDouble("score")));
                        }
                    }*/
                   /* MySingleton.getInstance(getApplicationContext()).getImageLoader().get(HTTP_BASE + HTTP_IMAGES + response.getString("photo"), new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            etd.setPhoto(response.getBitmap());
                            ImageView img=findViewById(R.id.imgProfile);
                            img.setImageBitmap(etd.getPhoto());
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });*/
                    Integer i = task.getTaskId();


                  /*  if(etd.getNotes()!=null)
                    {

                        NoteAdapter na=new NoteAdapter(MainActivity.this,etd.getNotes());
                        listNotes.setAdapter(na);

                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(MainActivity.class.getSimpleName(), error.getMessage());
                    }
                }

        );
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
    }
    void getSeance(String id) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, WS.URL + "seances"+"/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(MainActivity.class.getSimpleName(), response.toString());
                TextView txtNom = findViewById(R.id.txtNom);
                TextView txtPrenom = findViewById(R.id.txtprenom);
                TextView txtMail = findViewById(R.id.userEmail);
                TextView txtPasswd = findViewById(R.id.userPasswd);
                TextView textView = (TextView) findViewById(R.id.text);
                try {
                    Seance seance = new Seance(response.getInt("seanceId"),response.getInt("seanceGrpId"),
                            response.getInt("clientId"),response.getInt("monitorId"),
                            response.getInt("durationMinut"),response.getString("comments"),response.getString("startDate"));
                  /*  if(response.has("seances"))
                    {
                        JSONArray ja= response.getJSONArray("seances");
                        for (int i=0;i<ja.length();i++){JSONObject j=ja.getJSONObject(i);
                            user.addSeance(new Seance(j.getString("label"),j.getDouble("score")));
                        }
                    }*/
                   /* MySingleton.getInstance(getApplicationContext()).getImageLoader().get(HTTP_BASE + HTTP_IMAGES + response.getString("photo"), new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            etd.setPhoto(response.getBitmap());
                            ImageView img=findViewById(R.id.imgProfile);
                            img.setImageBitmap(etd.getPhoto());
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });*/


                  /*  if(etd.getNotes()!=null)
                    {

                        NoteAdapter na=new NoteAdapter(MainActivity.this,etd.getNotes());
                        listNotes.setAdapter(na);

                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(MainActivity.class.getSimpleName(), error.getMessage());
                    }
                }

        );
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
    }

    public List<User> getAllUsers() {
         List<User>users=new ArrayList<>() ;

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, WS.URL+"users", null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("getallusers", "success");

                JSONObject j = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                          j = response.getJSONObject(i);

                        users.add(new User(j.getInt("userId"), j.getString("userEmail"),
                                j.getString("userPasswd"), j.getString("userFname"),
                                j.getString("userLname"), j.getString("description"),
                                j.getString("userType"), j.getString("userphoto"), j.getString("userPhone"),j.getString("lastLoginTime"),j.getString("displayColor")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(MainActivity.class.getSimpleName(), error.getMessage());
            }
        }

        );


        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
        return users;
    }
    void getAllClients() {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, WS.URL+"clients", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                TextView txtNom = findViewById(R.id.txtNom);
                TextView txtPrenom = findViewById(R.id.txtprenom);
                TextView txtMail = findViewById(R.id.userEmail);
                TextView txtPasswd = findViewById(R.id.userPasswd);
                TextView textView = (TextView) findViewById(R.id.text);
                try {
                    JSONObject j = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            j = response.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    Integer i = j.getInt("clientId");
                    txtNom.setText(j.getString("fName"));
                    txtPrenom.setText(j.getString("lName"));
                    txtMail.setText(j.getString("clientEmail"));
                    txtPasswd.setText(j.getString("passwd"));
                    textView.setText(i.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(MainActivity.class.getSimpleName(), error.getMessage());
            }
        }

        );


        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
    }
    void getAllTasks() {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, WS.URL+"tasks", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                TextView txtNom = findViewById(R.id.txtNom);
                TextView txtPrenom = findViewById(R.id.txtprenom);
                TextView txtMail = findViewById(R.id.userEmail);
                TextView txtPasswd = findViewById(R.id.userPasswd);
                TextView textView = (TextView) findViewById(R.id.text);
                try {
                    JSONObject j = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            j = response.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    Integer i = j.getInt("clientId");
                    txtNom.setText(j.getString("fName"));
                    txtPrenom.setText(j.getString("lName"));
                    txtMail.setText(j.getString("clientEmail"));
                    txtPasswd.setText(j.getString("passwd"));
                    textView.setText(i.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(MainActivity.class.getSimpleName(), error.getMessage());
            }
        }

        );


        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
    }
    void getAllSeances() {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, WS.URL+"seances", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                TextView txtNom = findViewById(R.id.txtNom);
                TextView txtPrenom = findViewById(R.id.txtprenom);
                TextView txtMail = findViewById(R.id.userEmail);
                TextView txtPasswd = findViewById(R.id.userPasswd);
                TextView textView = (TextView) findViewById(R.id.text);
                try {
                    JSONObject j = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            j = response.getJSONObject(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    Integer i = j.getInt("clientId");
                    txtNom.setText(j.getString("fName"));
                    txtPrenom.setText(j.getString("lName"));
                    txtMail.setText(j.getString("clientEmail"));
                    txtPasswd.setText(j.getString("passwd"));
                    textView.setText(i.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(MainActivity.class.getSimpleName(), error.getMessage());
            }
        }

        );


        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
    }



}