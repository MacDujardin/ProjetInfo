
public class Vector {	
	public int x;
	public int y;

	public Vector  (int x ,int y){
		this.x= x;
		this.y= y;
     
	}
 	public Vector() {
 		x = 0;
 		y=0;
    
  				}
     public Vector mul (int n) {
    	 return new Vector (n*x,n*y);
     }
     
    public Vector add (Vector vect) {
    	return new Vector (x+vect.x,y+vect.y);
    }
    
    public Vector truediv ( int n) {
    	return new Vector (x/n , y/n);
    }
    
    public Vector sub (Vector vect) {
    	return new Vector (x-vect.x,y-vect.y);
    }
    
    public String repr() {
    	return "(" + x + "," + y +")" ;
    }
    
    public Vector neg () {
    	return new Vector (-x,-y);
    }
    
    public int getItem (int i) { 
    	if (i== 0 ) {
			return x;}
    	else if (i == 1){
    		return y;
    	}
    	else {
    		return 0 ;
    	}
	}
    
    public boolean eq (Vector vect) {
    	if (vect != null) 
    		return (this.x == vect.x && this.y == vect.y);
    	else 
    		return false;
    }
    
    public Vector perpendiculaire() {
        //perpendiculaire pour des mvmts avec une des composantes en 0
    	return new Vector(y,x);
    }
}


