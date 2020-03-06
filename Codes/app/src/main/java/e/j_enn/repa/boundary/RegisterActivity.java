package e.j_enn.repa.boundary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import e.j_enn.repa.R;
import e.j_enn.repa.control.UserDBManager;
import e.j_enn.repa.entity.User;

public class RegisterActivity extends AppCompatActivity {

    private final AppCompatActivity activity = RegisterActivity.this;

    Toolbar toolbar;
    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;
    private EditText textInputEditTextName;
    private Spinner textInputEditSalutation;
    private EditText textInputEditPhone;

    //private InputValidation inputValidation;
    private UserDBManager databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Register");
        //toolbar.setTitleTextColor(Color.WHITE);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView tv = ((TextView) findViewById(R.id.toolbar_title));
        tv.setText("Register");
        initObject();

        findViewById(R.id.signBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
                if (RegisterNotEmpty())
                    if (checkEmail())
                        if(checkPassword())
                             if (checkPhone())
                                postDataToSQLite();


            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initView() {
        textInputEditTextName = (EditText) findViewById(R.id.name);
        textInputEditTextEmail = (EditText) findViewById(R.id.email);
        textInputEditTextPassword = (EditText) findViewById(R.id.password);
        textInputEditPhone = (EditText) findViewById(R.id.phone);
        textInputEditSalutation = (Spinner) findViewById(R.id.spinner1);

    }

    private void initObject() {
        //inputValidation = new InputValidation(activity);
        databaseHelper = new UserDBManager(activity);
        user = new User();
    }

    private void postDataToSQLite() {
        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {
            user.setName(textInputEditTextName.getText().toString());
            user.setPhone(textInputEditPhone.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());
            user.setSalutation(textInputEditSalutation.getSelectedItem().toString().trim());

            databaseHelper.addUser(user);
            emptyInputEditText();

            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            emptyInputEditText();
            activity.finish();
        }
    }

    private boolean RegisterNotEmpty() {
        boolean result = true;

        if (textInputEditTextEmail.getText().toString().isEmpty()) {
            textInputEditTextEmail.requestFocus();
            textInputEditTextEmail.setError("Email cannot be left empty.");
            result = false;
        }

        if (textInputEditTextName.getText().toString().isEmpty()) {
            textInputEditTextName.requestFocus();
            textInputEditTextName.setError("Name cannot be left empty.");
            result = false;
        }

        if (textInputEditPhone.getText().toString().isEmpty()) {
            textInputEditPhone.requestFocus();
            textInputEditPhone.setError("Phone cannot be left empty.");
            result = false;
        }

        if (textInputEditTextPassword.getText().toString().isEmpty()) {
            textInputEditTextPassword.requestFocus();
            textInputEditTextPassword.setError("Password cannot be left empty.");
        }
        return result;
    }

    private boolean checkPassword() {
        boolean result = true;

        //Password
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if (textInputEditTextPassword.getText().toString().length() < 8) {
            textInputEditTextPassword.requestFocus();
            textInputEditTextPassword.setError("Password must be a minimum of eight(8) characters in length.");
            result = false;
        } else {
            String password = textInputEditTextPassword.getText().toString();
            if (password.matches(pattern) == false) {
                textInputEditTextPassword.requestFocus();
                textInputEditTextPassword.setError("Password must contain 1 upper, 1 lower, 1 digit and 1 special char");
                result = false;
            }
        }
        return result;
    }

    private boolean checkPhone() {
        //Phone
        String[] phoneHeader = {"6", "8", "9"};
        if (textInputEditPhone.getText().toString().length() == 8) {
            String phoneNumber = textInputEditPhone.getText().toString();
            if (phoneNumber.substring(0, 1).contains(phoneHeader[0])) {
                return true;
            } else if (phoneNumber.substring(0, 1).contains(phoneHeader[1])) {
                return true;
            } else if (phoneNumber.substring(0, 1).contains(phoneHeader[2])) {
                return true;
            } else {
                textInputEditPhone.requestFocus();
                textInputEditPhone.setError("Phone must start with either 6, 8 or 9.");
                return false;
            }
        } else {
            textInputEditPhone.requestFocus();
            textInputEditPhone.setError("Phone must be minimum of 8 digits.");
            return false;
        }
    }

    private boolean checkEmail() {
        //Email
        String[] emailHeader = {"gmail.com", "hotmail.com", "outlook.com", "yahoo.com"};
        if (Patterns.EMAIL_ADDRESS.matcher(textInputEditTextEmail.getText().toString()).matches()) {
            String[] splitByAt = textInputEditTextEmail.getText().toString().split("@");

            if (splitByAt[1].contains(emailHeader[0])) {
                return true;
            } else if (splitByAt[1].contains(emailHeader[1])) {
                return true;
            } else if (splitByAt[1].contains(emailHeader[2])) {
                return true;
            } else if (splitByAt[1].contains(emailHeader[3])) {
                return true;
            } else {
                textInputEditTextEmail.requestFocus();
                textInputEditTextEmail.setError("Email must in from gmail, outlook or yahoo.");
                return false;
            }
        } else {
            textInputEditTextEmail.requestFocus();
            textInputEditTextEmail.setError("Wrong Email format.");
            return false;
        }
    }

    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditPhone.setText(null);
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
