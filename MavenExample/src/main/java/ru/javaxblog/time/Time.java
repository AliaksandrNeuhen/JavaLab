package ru.javaxblog.time;

import javax.swing.JOptionPane;

public class Time {

    public static void main(String []args){
        String dt=new java.text.SimpleDateFormat(("hh:mm aaa")).format(java.util.Calendar.getInstance().getTime());
        JOptionPane.showMessageDialog(null, "������� �����: " + dt, "�����", JOptionPane.INFORMATION_MESSAGE);
    }
}