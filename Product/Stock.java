/*
This is the class that will be used to construct stock objects, it will have all the relevant methods and calculations
 */
//package stockanalysis;

public class Stock {
    
    private String[] arrayParameters;
    private Double peg;
    private Double roe;
    private Double altmanZscore;
    private Double modifiedCscore;
    private Double piotroskiFscore;
    private Double returnFactor;
    private Double fAdjustedEarning;
    
    //constructors
    public Stock() {
        this.arrayParameters = null;
    }

    public Stock(String[] arrayParameters, Double peg, Double roe, Double altmanZscore, Double modifiedCscore, Double piotroskiFscore, Double returnFactor, Double fAdjustedEarning) {
        this.arrayParameters = new String[arrayParameters.length];
        
        for(int i = 0; i < arrayParameters.length; i++){
            this.arrayParameters[i] = arrayParameters[i];
        }
        this.peg = peg;
        this.roe = roe;
        this.altmanZscore = altmanZscore;
        this.modifiedCscore = modifiedCscore;
        this.piotroskiFscore = piotroskiFscore;
        this.returnFactor = returnFactor;
        this.fAdjustedEarning = fAdjustedEarning;
    }
    
    public Stock(String[] arrayParameters) {
        
        this.arrayParameters = new String[arrayParameters.length];
        
        for(int i = 0; i < arrayParameters.length; i++){
            this.arrayParameters[i] = arrayParameters[i];
        }
        
        setPeg();
        setRoe();
        setAltmanZscore();
        setModifiedCscore();
        setPiotroskiFscore();
        setReturnFactor();
        setfAdjustedEarning();
    }

    //gets and sets
    public String[] getArrayParameters() {
        return arrayParameters;
    }

    public void setArrayParameters(String[] arrayParameters) {
        
        this.arrayParameters = new String[arrayParameters.length];
        
        for(int i = 0; i < arrayParameters.length; i++){
            this.arrayParameters[i] = arrayParameters[i];
        }
    }
//to string
    @Override
    public String toString() {
        return "Stock{" + "arrayParameters=" + arrayParameters + ", peg=" + peg + ", roe=" + roe + ", altmanZscore=" + altmanZscore + ", modifiedCscore=" + modifiedCscore + ", piotroskiFscore=" + piotroskiFscore  + ", returnFactor=" + returnFactor + ", fAdjustedEarning=" + fAdjustedEarning + '}';
    }

    public Double getPeg() {
        return peg;
    }

    public Double getRoe() {
        return roe;
    }

    public Double getAltmanZscore() {
        return altmanZscore;
    }

    public Double getModifiedCscore() {
        return modifiedCscore;
    }

    public Double getPiotroskiFscore() {
        return piotroskiFscore;
    }

    public Double getReturnFactor() {
        return returnFactor;
    }

    public Double getfAdjustedEarning() {
        return fAdjustedEarning;
    }

    public void setPeg() {
        this.peg = Double.parseDouble(arrayParameters[2])/Double.parseDouble(arrayParameters[3]);
        this.peg = 100*this.peg/(Double.parseDouble(arrayParameters[20])/(Double.parseDouble(arrayParameters[3])-Double.parseDouble(arrayParameters[20])));
    }

    public void setRoe() {
        this.roe = Double.parseDouble(arrayParameters[4])/Double.parseDouble(arrayParameters[5]);
    }

    public void setAltmanZscore() {
        this.altmanZscore = Double.parseDouble(arrayParameters[8]);
    }

    public void setModifiedCscore() {
        this.modifiedCscore = Double.parseDouble(arrayParameters[10]);
    }

    public void setPiotroskiFscore() {
        this.piotroskiFscore = Double.parseDouble(arrayParameters[9]);
    }


    public void setReturnFactor() {
        this.returnFactor = Double.parseDouble(arrayParameters[22])/Math.abs(Double.parseDouble(arrayParameters[21]));
        
    }

    public void setfAdjustedEarning() {
        this.fAdjustedEarning = Double.parseDouble(arrayParameters[11]);
        for(int i = 1; i<= this.piotroskiFscore; i++){
            this.fAdjustedEarning=this.fAdjustedEarning*i;
        }
    }
    
    
    
    public static void main(String[] args) {
        
    }
    
}
