package com.example.lool.lettercount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class LetterCount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter_count);
    }
    public static void main (String[] args) {
//        Scanner in = new Scanner(Paths.get("hamlet.txt"),"UTF-8");
        Scanner in = new Scanner(System.in);
        while ( in.hasNext() ) {
            String line = in.nextLine();
            Line aLine = new Line(line);
            aLine.Count();
        }
        Line.Print_Out();
    }
}

class Line {

    private ArrayList<String> line = new ArrayList<>();
    private static int[] num = new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0 };
    private static String[] letters = new String[] { "a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

    public Line(String string) {
        for (int i = 0; i < string.length(); i++) {
            String temp;
            if (i + 1 != string.length())
                temp = string.substring(i, i + 1);
            else
                temp = string.substring(i);
            if (!temp.equals(" ")) {
                line.add(temp);
            }
        }
    }

    public void Count () {
        Stream<String> letters = line.stream();
        for (int i=0 ; i < 26 ; i++ ) {
            int count = 0;
            count += line.stream().filter ( line -> letters[i].equals(line) ).count();
            count += line.stream().filter ( line -> letters[i].toUpperCase().equals(line) ).count();
            num[i] += count;
        }
    }

    public void Print_Out () {
        for (int i = 0 ; i < 26 ; i++) {
            System.out.println((char)i+'a' +" "+num[i]);
        }
    }
}
