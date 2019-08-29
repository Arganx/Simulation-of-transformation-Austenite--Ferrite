package model;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;

public class Model {
    private ArrayList<ArrayList<Double>> tables;
    private Double  cAlfa;
    private Double cAlfaGamma;
    private Double temp;
    private Integer boarderIndex;
    private Double cZero;
    private Integer tableEnd;
    private Integer dT;
    private Integer dX;
    private Double d;
    private Double q;
    private Double d0;
    private Double r;
    private int step;
    private double coolingSpeed;

    public Model(int size,Double cAlfa, Double temp, Integer boarderIndex, Double cZero,double coolingSpeed) {
        this.step=0;
        this.tables = new ArrayList<ArrayList<Double>>();
        this.tables.add(new ArrayList<Double>());
        this.cAlfa = cAlfa;
        this.temp = temp;
        this.boarderIndex = boarderIndex;
        this.cZero = cZero;
        this.fillTable(size);
        this.coolingSpeed = coolingSpeed;
        if(this.temp>820.0)
        {
            this.temp=820.0;
        }
    }

    public Model() {
        this.step=0;
        this.cAlfa = 0.02;
        this.temp = 800.0;
        this.cAlfaGamma = -(this.temp-850.0)/210.0;
        this.boarderIndex=2;
        this.cZero = 0.1;
        this.tables = new ArrayList<ArrayList<Double>>();
        this.tables.add(new ArrayList<Double>());
        this.fillTable(60);
        this.dT=1;
        this.dX=1;
        this.q=140000.0;
        this.d0=0.000041;
        this.r=8.3144;
        double temp_kelwin = this.temp+273;
        this.d=d0*Math.exp(-q/(r*temp_kelwin))*1E10;
        System.out.println("Stable: "+ this.isStable());
        this.coolingSpeed=0;

    }

    public int getSize()
    {
        return this.tables.get(0).size();
    }

    public ArrayList<ArrayList<Double>> getTable()
    {
        return this.tables;
    }

    private void fillTable(int size)
    {
        this.tables.set(0,new ArrayList<Double>());
        for(int i=0;i<this.boarderIndex;i++)
        {
            this.tables.get(0).add(this.cAlfa);
        }
        this.tables.get(0).add(this.cAlfaGamma);
        for(int i=this.boarderIndex+1;i<size;i++)
        {
            this.tables.get(0).add(this.cZero);
        }
    }

    public void showTable()
    {
        for(int i = 0; i<this.tables.size(); i++)
        {
            System.out.println(tables.get(i));
        }
    }

    public boolean isStable()
    {
        return (d*dT)/Math.pow(dX,2)<=0.5;
    }

    public void step()
    {
        //System.out.println(integral()-squareArea());
        this.tables.add(new ArrayList<Double>());
        this.calculate_border();
        this.coolDown(this.coolingSpeed);
        this.step++;
        for(int i=0;i<this.boarderIndex;i++)
        {
            this.tables.get(step).add(this.tables.get(this.step-1).get(i));
        }
        this.cAlfaGamma = -(this.temp-850.0)/210.0;
        this.diffusion();
        if(this.tables.get(step).get(boarderIndex-1)!=0.02)
        {
            this.tables.get(step).set(this.boarderIndex-1,0.02);
        }

 /*       this.tables.get(step).add(this.cAlfaGamma);
        for(int i=this.boarderIndex+1;i<this.tables.get(0).size();i++)
        {
            this.tables.get(this.step).add(this.tables.get(this.step-1).get(i));
        }*/
    }

    private void diffusion()
    {

        this.tables.get(step).add((1-2*d*dT/Math.pow(dX,2))*this.cAlfaGamma + (d*dT/Math.pow(dX,2))*(2*this.tables.get(step-1).get(this.boarderIndex+1)));//border max left
        for(int i=this.boarderIndex+1;i<this.tables.get(0).size()-1;i++)
        {
            this.tables.get(this.step).add(  (1.0-2.0*d*dT/Math.pow(dX,2))*this.tables.get(step-1).get(i) +  (d*dT/Math.pow(dX,2))*(this.tables.get(step-1).get(i-1) + this.tables.get(step-1).get(i+1)) );
        }
        this.tables.get(step).add((1-2*d*dT/Math.pow(dX,2))*this.tables.get(step-1).get(this.tables.get(step-1).size()-1) + (d*dT/Math.pow(dX,2))*(2*this.tables.get(step-1).get(this.tables.get(step-1).size()-2)));//border max right
    }

    private void calculate_border()
    {
        //System.out.println("Calka: "+integral());
        //System.out.println("Prostokat: "+squareArea());
        if(integral()-squareArea()>=dX*(this.cZero-this.cAlfa) && this.boarderIndex!=this.tables.get(step).size()-2)
        {
            //System.out.println("Zmiana");
            //this.tables.get(step).set(this.boarderIndex,0.02);
            this.boarderIndex++;
        }

    }

    private double integral()
    {
        double s=0;
        for(int i=this.boarderIndex;i<this.tables.get(step).size()-1;i++)
        {
            //System.out.println((this.tables.get(step).get(i)+this.tables.get(step).get(i+1)));
            s+=(this.tables.get(step).get(i)+this.tables.get(step).get(i+1));
        }
        s=s/2*dX;
        //System.out.println(s);
        return s;
    }

    private void coolDown(double amount)
    {
        this.temp-=amount;
        if(this.temp<727.0)
        {
            this.temp = 727.0;
        }
        //System.out.println("New temp: " + this.temp);
    }

    private double squareArea()
    {
        return (this.tables.get(0).size()-this.boarderIndex-1)*0.1;
    }


    public void setCoolingSpeed(double coolingSpeed) {
        this.coolingSpeed = coolingSpeed;
    }

    public void setSize(int size)
    {
        this.fillTable(size);
    }


}
