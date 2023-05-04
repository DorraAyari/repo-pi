/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.gui.front.sms;

/**
 *
 * @author dorraayari
 */


import com.infantry.entities.Cours;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class SendSMS {
    

 public static final String ACCOUNT_SID = "AC6478314a2b965fb4e82e4f6b1a29105d";
  public static final String AUTH_TOKEN = "fc0a769debe2a9d4858c5e9f559b3403";
    public static void sendSMS(Cours cours) {
        Twilio.init("AC6478314a2b965fb4e82e4f6b1a29105d", "fc0a769debe2a9d4858c5e9f559b3403");
        System.setProperty("com.twilio.sdk.debug", "true");

        Message message = Message.creator(new PhoneNumber("+21629392952"),
                new PhoneNumber("+16205091529"), "reservation : Bonjour votre reservation est effectuer avec succés ").create();

        System.out.println(message.getSid());
    }
    
}

