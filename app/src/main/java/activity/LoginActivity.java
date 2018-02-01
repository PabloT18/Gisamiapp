/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 */
package activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chris.gisamiapp.MenuActivity;
import com.example.chris.gisamiapp.R;

import java.util.List;

import helper.SessionManager;

import modelo.Administrador;
import utilidades.ClienteRest;
import utilidades.OnTaskCompleted;
import utilidades.Util;

public class LoginActivity extends Activity implements OnTaskCompleted
{
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    //private SQLiteHandler db;

    private static final int SOLICITUD_CATEGORIA = 2;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        //db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn())
        {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // LoginActivity button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Ingrese sus credenciales porfavor", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";



        try {
            String URL = Util.URL_SRV + "session/login";
            ClienteRest clienteRest = new ClienteRest(this);
            clienteRest.doGet(URL, "?user=" + email + "&password=" + password, SOLICITUD_CATEGORIA, true);
            pDialog.setMessage("Logging in ...");
            showDialog();

        } catch (Exception e) {
          //  pDialog.setMessage("Error al conusltar servicio web, posibles problemas de conexión, intente más adelante");
            e.printStackTrace();
        }
    }
/*
        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>()
        {

            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "LoginActivity Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error)
                    {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        //String created_at = user.getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid);

                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "LoginActivity Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
*/
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    @Override
    public void onTaskCompleted(int idSolicitud, String result) {
        switch (idSolicitud) {
            case SOLICITUD_CATEGORIA:
                if (result != null) {
                    try {
                        List<Administrador> resp = ClienteRest.getResults(result,Administrador.class);
                        //Respuesta res = ClienteRest.getResult(result, Respuesta.class);
                        //Util.showMensaje(this, "LOGIN EXITOSO");

                        if (resp.size()>0)
                        {
                            //Util.showMensaje(this, resp.toString());
                            hideDialog();
                            Util.showMensaje(this, "LOGIN EXITOSO");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                            finish();
                        }
                        else
                        {
                            Util.showMensaje(this, "INTENTE NUEVAMENTE: USUARIO O CONTRASEÑA INVALIDOS");
                            hideDialog();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Util.showMensaje(this, "Error en interpretación de resultado, posibles problemas con el servidor.");
                    }
                } else
                    Util.showMensaje(this, "Error al conusltar servicio web, posibles problemas de conexión, intente más adelante.");
                break;
            default:
                break;
        }
    }
}
