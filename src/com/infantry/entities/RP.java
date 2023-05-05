/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.util.Date;

/**
 *
 * @author mrdje
 */
public class RP {
       private Date start;
    private Date finish;
     private String choices;
     private int idR,idP;
    
      public RP(int idR,int idP,Date start , Date finish,String choices ){
        this.idR = idR;
        this.idP = idP;
        this.start = start;
        this.finish = finish;
        this.choices = choices;
       
    }
          public RP( ){
  
       
    }

    public Date getStart() {
        return start;
    }

    public Date getFinish() {
        return finish;
    }

    public String getChoices() {
        return choices;
    }

    public int getIdR() {
        return idR;
    }

    public int getIdP() {
        return idP;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public void setChoices(String choices) {
        this.choices = choices;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    @Override
    public String toString() {
        return "RP{" + "start=" + start + ", finish=" + finish + ", choices=" + choices + ", idR=" + idR + ", idP=" + idP + '}';
    }
      
    
      
}
