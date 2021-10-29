package com.esafeafrica.eresponder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esafeafrica.eresponder.Model.ChatMessage;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import static com.esafeafrica.eresponder.Config.Validation.isFieldValid;

/**
 * create an instance of this fragment.
 */
public class Chat extends AppCompatActivity {

    private static final int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseListAdapter<ChatMessage> adapter;
    private ImageButton send;
    private EditText text;
    private Context context;
    private ListView list;

    public Chat() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);

        InitView();
    }

    public void InitView() {
        context = getApplicationContext();
        send = findViewById(R.id.sendButton);
        text = findViewById(R.id.messageEditText);
        list = findViewById(R.id.messageRecyclerView);


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        authListener = firebaseAuth -> {
            FirebaseUser user1 = firebaseAuth.getCurrentUser();
            if (user1 == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(getApplicationContext(), Login.class));
                //finish();
            } else {

                displayChatMessages(list);
            }
        };

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder().build(),
                    SIGN_IN_REQUEST_CODE
            );
        } else {
            // User is already signed in. Therefore, display
            // a welcome Toast
            // Toast.makeText(context,
            //       "Welcome " + FirebaseAuth.getInstance()
            //             .getCurrentUser()
            //       .getDisplayName(),
            //   Toast.LENGTH_LONG)
            // .show();

            displayChatMessages(list);
        }

        send.setOnClickListener(v -> {
            // Read the input field and push a new instance
            // of ChatMessage to the Firebase database
            String msg = text.getText().toString();
            if (!isFieldValid(msg)) {
                Toast.makeText(getApplicationContext(), "Please Write something", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new ChatMessage(msg,
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );
                // Clear the input
                text.setText("");
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(),
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();

                displayChatMessages(list);
            } else {
                Toast.makeText(getApplicationContext(),
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();
                //finish();
            }
        }
    }


    private void displayChatMessages(ListView listView) {

        adapter = new FirebaseListAdapter<ChatMessage>(Chat.this, ChatMessage.class,
                R.layout.message_item, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = v.findViewById(R.id.message_text);
                TextView messageUser = v.findViewById(R.id.message_user);
                TextView messageTime = v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listView.setAdapter(adapter);
    }
}
