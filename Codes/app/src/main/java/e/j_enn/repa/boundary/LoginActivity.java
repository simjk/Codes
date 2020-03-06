package e.j_enn.repa.boundary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;

import e.j_enn.repa.control.MainActivity;
import e.j_enn.repa.R;
import e.j_enn.repa.control.UserDBManager;
import e.j_enn.repa.entity.User;

public class LoginActivity extends AppCompatActivity {

    private final AppCompatActivity activity = LoginActivity.this;

    Toolbar toolbar;

    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;

    //private InputValidation inputValidation;
    private UserDBManager databaseHelper;
    public User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //getSupportActionBar().hide(); //Hide ActionBar
        //toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        TextView tv = ((TextView) findViewById(R.id.toolbar_title));
        tv.setText("Login");

        initView();


        //Forget Password
        findViewById(R.id.btnForgetPwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                // LoginActivity.this.finish();
            }
        });

        //Register Button
        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
                                                              @Override
                                                              public void onClick(View v) {
                                                                  //Open Register Page
                                                                  startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                                                                  overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                                                  //  LoginActivity.this.finish();
                                                              }
                                                          }

        );
        //Login Button
        //final TableLayout tableLayout = (TableLayout) findViewById(R.id.table_layout);

        findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tableLayout.setVisibility(View.GONE);
                /*BrowseFragment fragment = new BrowseFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_browse, fragment);
                transaction.commit();*/

                ///startActivity(new Intent(LoginActivity.this, MainActivity.class));
                //LoginActivity.this.finish();
                initObjects();
                if (AccountEmptyValidation() == true) {
                    if (AccountEmailValidation() == true) {
                        verifyFromSQLite();
                    }
                }
            }
        });
    }

    private void initObjects() {
        databaseHelper = new UserDBManager(activity);
    }

    private void initView() {
        textInputEditTextEmail = (EditText) findViewById(R.id.email_addr);
        textInputEditTextPassword = (EditText) findViewById(R.id.password);
    }

    private void verifyFromSQLite() {
        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim(),
                textInputEditTextPassword.getText().toString().trim())) {
            startActivity(new Intent(activity, MainActivity.class).putExtra("email", textInputEditTextEmail.getText().toString()));
            emptyInputEditText();
//            LoginActivity.this.finish();
        } else {
            textInputEditTextPassword.requestFocus();
            textInputEditTextEmail.setError("Wrong Email and Password!");
            textInputEditTextPassword.setError("Wrong Email and Password!");
        }
    }

    private boolean AccountEmptyValidation() {
        boolean result = true;

        if (textInputEditTextEmail.getText().toString().isEmpty()) {
            textInputEditTextEmail.requestFocus();
            textInputEditTextEmail.setError("Email cannot be left blank.");
            result = false;
        }

        if (textInputEditTextPassword.getText().toString().isEmpty()) {
            textInputEditTextPassword.requestFocus();
            textInputEditTextPassword.setError("Password cannot be left blank.");
            result = false;
        }

        return result;
    }

    private boolean AccountEmailValidation() {
        boolean result = true;

        if(Patterns.EMAIL_ADDRESS.matcher(textInputEditTextEmail.getText().toString()).matches() == false){
            textInputEditTextEmail.requestFocus();
            textInputEditTextEmail.setError("Wrong Email format.");
            result = false;
        }

        return result;
    }

    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
