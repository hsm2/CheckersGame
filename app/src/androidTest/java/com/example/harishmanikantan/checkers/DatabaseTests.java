package com.example.harishmanikantan.checkers;

import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by harishmanikantan on 4/18/17.
 */

@RunWith(AndroidJUnit4.class)
public class DatabaseTests {

    private CountDownLatch writeSignal;
    private CountDownLatch readSignal;

    @Test
    public void writeData() throws Exception {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        writeSignal = new CountDownLatch(1);

        databaseReference.child("test").child("test1").child("name").setValue("Test1 name")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("Result", "OnComplete");
                        writeSignal.countDown();
                    }
                });

        databaseReference.child("test").child("test1").child("email").setValue("Test1 email");

        databaseReference.child("test").child("test2").child("name").setValue("Test2 name");
        databaseReference.child("test").child("test2").child("email").setValue("Test2 email");

        writeSignal.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void retrieveData() throws Exception {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        readSignal = new CountDownLatch(1);

        databaseReference.child("test").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assertTrue(dataSnapshot.hasChild("test1"));
                assertTrue(dataSnapshot.hasChild("test2"));
                Log.d("Result", "Outside");

                if (dataSnapshot.hasChild("test1")) {
                    String testName1 = dataSnapshot.child("test1").child("name").getValue(String.class);
                    String testEmail1 = dataSnapshot.child("test1").child("email").getValue(String.class);

                    Log.d("Result", "Inside 1");
                    assertEquals(testName1, "Test1 name");
                    assertEquals(testEmail1, "Test1 email");
                }

                if (dataSnapshot.hasChild("test2")) {
                    String testName2 = dataSnapshot.child("test2").child("name").getValue(String.class);
                    String testEmail2 = dataSnapshot.child("test2").child("email").getValue(String.class);

                    Log.d("Result", "Inside 2");
                    assertEquals(testName2, "Test2 name");
                    assertEquals(testEmail2, "Test2 email");
                }

                readSignal.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        readSignal.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void addDataTest() throws Exception {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        readSignal = new CountDownLatch(1);

        databaseReference.child("test").child("Add test").setValue("true");

        databaseReference.child("test").child("Add test").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assertTrue(dataSnapshot.getValue(String.class).equals("true"));

                readSignal.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        readSignal.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void removeDataTest() throws Exception {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        readSignal = new CountDownLatch(1);

        databaseReference.child("test").child("Remove test").setValue("true");
        databaseReference.child("test").child("Remove test").removeValue();

        databaseReference.child("test").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assertTrue(!dataSnapshot.hasChild("Remove test"));

                readSignal.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        readSignal.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void modifyDataTest() throws Exception {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        readSignal = new CountDownLatch(1);

        databaseReference.child("test").child("Modify test").setValue("1");
        databaseReference.child("test").child("Modify test").setValue("changed");

        databaseReference.child("test").child("Modify test").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assertTrue(dataSnapshot.getValue(String.class).equals("changed"));

                readSignal.countDown();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        readSignal.await(10, TimeUnit.SECONDS);
    }

}
