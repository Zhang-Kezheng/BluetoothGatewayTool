package com.example.bluetoothgatewaytool;

import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        System.out.println(new String(new byte[]{0x4d,0x79,0x20,0x44,0x65,0x76,0x69,0x63,0x65}));
    }
}