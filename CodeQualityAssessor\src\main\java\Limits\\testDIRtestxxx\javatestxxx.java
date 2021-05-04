public class javatestxxx {
    double price;
    // nested class
    
    public javatestxxx() {
    	price = 19.9;
    }
    

    
    public void fu1() {
    	System.out.println("vava");
    }
    
    public void fu2() {
    	System.out.println("vava");
    }
    
    public void fu3() {
    	System.out.println("vava");
    }
    
    public void fu4() {
    	System.out.println("vava");
    }
    
    public void fu5() {
    	System.out.println("vava");
    }
    
    public class Processor{
    	
    	javatestxxx.RAM dl;
    	String model; 
    	String strin2g; 
    	int number, test;
         // members of nested class
        double cores;
        String manufacturer;
        
   	public Processor(javatestxxx.RAM dl, String strin2g, int number, int test) {
    		this.strin2g = strin2g;
    		this.number = number;
    		this.test = test;
    		this.dl = dl;
    		model = "test example";
    	}
    	
    	void futilefunction(javatestxxx.RAM dl) {
    		this.dl = dl;
    	}


        double getCache(){
            return 4.3;
        }
		
		double getStmst(boolean tr){
		double re = 0.0;
		String c = "ddd";
		if(tr) c = "eee";
		switch (c) {
		case "eee": re = 9.9; break;
		case "ddd": re = 10.0; break;
		default: re = 3.3; break;
		
		}
		return re;
		}
    }

    // nested protected class
    public class RAM{
    	

    	javatestxxx cpu;
        // members of protected nested class
        double memory;
        String manufacturer;
        javatestxxx.Processor dl;
        
        public RAM(javatestxxx.Processor dl, String ob) {
    		this.dl = dl;
    		memory = 9.9;
    		manufacturer = "G.SKILL";
    	}
    	
    	public void setRAM(javatestxxx.Processor dl, String man, double d, int test, javatestxxx cp, String strings ) {
    		cpu = cp;
    		manufacturer = man;
    		memory = d;
    	}
        double getClockSpeed(){
            return 5.5;
        }
    }
    

}